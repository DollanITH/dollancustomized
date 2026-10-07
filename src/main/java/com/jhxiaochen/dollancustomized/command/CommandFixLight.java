package com.jhxiaochen.dollancustomized.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * /fixlight —— 强制重算一定范围内的光照，用于修复异常光照（黑块、光照残留、发光方块不生效等）。
 *
 * 用法：
 *   /fixlight                       以执行者为中心，半径 64 格
 *   /fixlight <半径>                 以执行者为中心，指定半径
 *   /fixlight <x> <y> <z> [半径]     指定中心坐标
 *
 * 原理（分两趟，缺一不可）：
 *
 *   第一趟 —— 清空与重建：
 *     把范围内每个已加载区块的方块光 / 天光数组清零，然后调用原版的
 *     {@link Chunk#generateSkylightMap()} 重建高度图与直射天光（只处理“能看见天空”的柱）。
 *
 *   第二趟 —— 交给原版光照引擎：
 *     逐区块调用 {@link Chunk#checkLight()}。这是原版真正的“重新点亮”入口：
 *       · 若周围 16 格（含 y 轴 seaLevel+16）的区块已加载，则对整块 16x16 的每一列
 *         调用 checkLight(x, z)：它会自上而下找到第一个不透光方块，再对它下方的方块
 *         调用 {@link World#checkLight(BlockPos)} 重新播种天光；
 *       · 再自上而下扫描整列（0..高度上限），对任何 getLightValue > 0 的方块调用
 *         World.checkLight —— 这一步才是方块光（火把 / 萤石 / 灯笼 / 模组光源）的重新播种；
 *       · 最后通过 checkLightSide 把光照扩散到相邻区块。
 *     真正把光照值算出来并扩散出去的是这一步驱动起来的光照引擎（World.updateLight 的 BFS）。
 *
 * 旧实现只调用了 resetRelightChecks()/enqueueRelightChecks()，这是错的：
 * enqueueRelightChecks 只会对“紧挨着实心方块的空气”调用 World.checkLight，它的用途是补
 * 洞穴 / 悬垂物下面的暗缝，既不会重新播种光源，也不会重新播种天光。数组被清零之后它只能
 * 让光照变得“略微不那么黑”，无法恢复，所以从外面看就是“没有有效修复、也没有扩散”。
 *
 * 所有提示文本均使用本地化键（commands.fixlight.*），见 assets/dollancustomized/lang/。
 */
public class CommandFixLight extends CommandBase
{
    private static final String LANG = "commands.fixlight";

    private static final int DEFAULT_RADIUS = 64;
    private static final int MAX_RADIUS = 256;

    /**
     * 半径小于该值时才需要对区块内的发光方块扫描做圆盘裁剪。
     * 半径够大时整块扫描更省事，也不会漏掉任何光源。
     */
    private static final int COLUMN_CLIP_THRESHOLD = 16;

    @Override
    public String getName()
    {
        return "fixlight";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return LANG + ".usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        BlockPos center;
        int radius = DEFAULT_RADIUS;

        if (args.length == 0)
        {
            center = getCommandSenderAsPlayer(sender).getPosition();
        }
        else if (args.length == 1)
        {
            radius = parseInt(args[0], 16, MAX_RADIUS);
            center = getCommandSenderAsPlayer(sender).getPosition();
        }
        else if (args.length == 3)
        {
            center = parseBlockPos(sender, args, 0, false);
        }
        else if (args.length == 4)
        {
            center = parseBlockPos(sender, args, 0, false);
            radius = parseInt(args[3], 16, MAX_RADIUS);
        }
        else
        {
            throw new WrongUsageException(getUsage(sender));
        }

        World world = sender.getEntityWorld();
        if (!(world instanceof WorldServer))
        {
            throw new CommandException(LANG + ".serveronly");
        }

        int fixed = relightArea(world, center, radius);
        if (fixed == 0)
        {
            notifyCommandListener(sender, this, LANG + ".notloaded");
        }
        else
        {
            // 中心坐标取整到方块，便于本地化文本里显示得干净一些
            notifyCommandListener(sender, this, LANG + ".success",
                    new Object[] { fixed, center.getX(), center.getY(), center.getZ(), radius });
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return Collections.emptyList();
    }

    /**
     * 强制重算以 center 为中心、radius 格为半径内的所有已加载区块的光照。
     *
     * @return 处理过的区块数量（0 表示范围内没有任何已加载区块）
     */
    public static int relightArea(World world, BlockPos center, int radius)
    {
        final int minCX = (center.getX() - radius) >> 4;
        final int maxCX = (center.getX() + radius) >> 4;
        final int minCZ = (center.getZ() - radius) >> 4;
        final int maxCZ = (center.getZ() + radius) >> 4;
        final boolean hasSky = world.provider.hasSkyLight();
        final boolean wholeChunk = radius >= COLUMN_CLIP_THRESHOLD;
        final int minX = center.getX() - radius;
        final int maxX = center.getX() + radius;
        final int minZ = center.getZ() - radius;
        final int maxZ = center.getZ() + radius;

        // 第一趟：收集已加载区块，清空光照数组并重建高度图 / 直射天光。
        // 必须先把所有区块都清空重建，再统一交给光照引擎，否则后面区块的 checkLight
        // 会读到邻居还没重建的光照数据，扩散出来的结果是不对的。
        List<Chunk> chunks = new ArrayList<Chunk>();

        for (int cx = minCX; cx <= maxCX; cx++)
        {
            for (int cz = minCZ; cz <= maxCZ; cz++)
            {
                // 只处理已加载区块，避免副作用加载远处区块
                Chunk chunk = world.getChunkProvider().getLoadedChunk(cx, cz);
                if (chunk == null)
                {
                    continue;
                }

                chunks.add(chunk);

                // 1) 清零方块光与天光，强迫引擎从零重算
                for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
                {
                    if (storage == null || storage == Chunk.NULL_BLOCK_STORAGE)
                    {
                        continue;
                    }

                    Arrays.fill(storage.getBlockLight().getData(), (byte) 0);
                    if (hasSky)
                    {
                        Arrays.fill(storage.getSkyLight().getData(), (byte) 0);
                    }
                }

                // 2) 重建高度图并写入直射天光；同时清掉原版遗留的逐柱天光补洞标记
                if (hasSky)
                {
                    chunk.generateSkylightMap();
                }

                chunk.resetRelightChecks();
            }
        }

        if (chunks.isEmpty())
        {
            return 0;
        }

        // 第二趟：驱动原版光照引擎重新点亮每个区块，并把结果推送给客户端。
        for (Chunk chunk : chunks)
        {
            // 3) 原版“重新点亮”入口：重新播种天光与方块光，并扩散到相邻区块。
            if (hasSky || hasLightEmitters(chunk, wholeChunk ? null : new int[] { minX, maxX, minZ, maxZ }))
            {
                chunk.checkLight();
            }

            // 4) 标记区块已修改（写回磁盘时保存新光照数据）
            chunk.setModified(true);

            // 5) 通知客户端重算渲染，并下发包含最新光照数据的完整区块包
            sendChunkLightUpdate(world, chunk);
        }

        return chunks.size();
    }

    /**
     * 扫描区块内是否存在发光方块（getLightValue > 0）。
     *
     * @param clipBounds 为 null 时扫描整块；否则为 {minX, maxX, minZ, maxZ}，只扫描落在这个
     *                   圆盘包围盒内的柱，用于小半径指令避免全块扫描的开销。
     */
    private static boolean hasLightEmitters(Chunk chunk, int[] clipBounds)
    {
        World world = chunk.getWorld();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int baseX = chunk.x << 4;
        int baseZ = chunk.z << 4;

        for (int ix = 0; ix < 16; ix++)
        {
            int worldX = baseX + ix;

            if (clipBounds != null && (worldX < clipBounds[0] || worldX > clipBounds[1]))
            {
                continue;
            }

            for (int iz = 0; iz < 16; iz++)
            {
                int worldZ = baseZ + iz;

                if (clipBounds != null && (worldZ < clipBounds[2] || worldZ > clipBounds[3]))
                {
                    continue;
                }

                int top = chunk.getTopFilledSegment() + 15;

                for (int y = 0; y <= top; y++)
                {
                    cursor.setPos(worldX, y, worldZ);

                    if (world.getBlockState(cursor).getLightValue(world, cursor) > 0)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 让客户端丢弃旧的渲染结果，并把当前区块（含光照数据）完整下发一次。
     */
    private static void sendChunkLightUpdate(World world, Chunk chunk)
    {
        int cx = chunk.x;
        int cz = chunk.z;
        int minX = cx << 4;
        int minZ = cz << 4;

        world.markBlockRangeForRenderUpdate(minX, 0, minZ, minX + 15, 255, minZ + 15);

        if (!(world instanceof WorldServer))
        {
            return;
        }

        PlayerChunkMap chunkMap = ((WorldServer) world).getPlayerChunkMap();
        PlayerChunkMapEntry entry = chunkMap.getEntry(cx, cz);

        if (entry == null || !entry.isSentToPlayers())
        {
            return;
        }

        // sendToPlayer 内部使用完整区块包（fullChunk = true），会带上最新光照数据
        for (EntityPlayerMP player : entry.getWatchingPlayers())
        {
            entry.sendToPlayer(player);
        }
    }
}
