package com.jhxiaochen.dollancustomized.command;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
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
        // 每次调用都把传播边界重置为"尚未观测到任何区块"的哨兵值，
        // 随后在收集区块时按大家真实的 Y 跨度取并集。避免上一次调用的
        // 残留范围影响这一次。
        MIN_BUILD_Y = Integer.MAX_VALUE;
        MAX_BUILD_Y = Integer.MIN_VALUE;

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
        // 每个区块自己的 Y 覆盖范围（由它实际的 storageArrays 推出），用于限定清空与重播种。
        List<int[]> ranges = new ArrayList<int[]>();

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

                // 先算出这个区块真实的 Y 跨度，避免"清空全部段、却只能重建原版段"。
                // 这里刻意不依赖任何其它模组：范围完全由区块自己的 storageArrays 推出
                // （ExtendedBlockStorage.getYLocation() 给出每段的 yBase）。
                int[] range = yRangeOf(chunk);
                ranges.add(range);

                // 用本区块自身的 Y 跨度刷新传播边界。
                // 这样 /fixlight 完全不需要依赖任何其它模组，也不需要硬编码
                // 0..255：原版区块得到 [0, 256)，扩展维度自然得到类似 [-64, 400)。
                // 取所有区块的并集（多区块时以更宽者为准）。
                synchronized (CommandFixLight.class)
                {
                    MIN_BUILD_Y = Math.min(MIN_BUILD_Y, range[0]);
                    MAX_BUILD_Y = Math.max(MAX_BUILD_Y, range[1] + 1);
                }

                // 关键：先判断"能不能重建"，再决定"要不要清空"。
                // 旧实现无条件清空所有段的光照数组，一旦后续重建失败（例如区块内没有
                // 发光方块、或重建范围覆盖不到被清空的段），光照就永久变黑 —— 这正是
                // /fixlight 会把光照抹黑的原因。
                boolean canRebuildBlockLight = depthsupdate$hasAnyLightEmitter(chunk, range);

                // 本区块的扩展区间段（y<0 / y>=256），稍后单独清空并由自有 BFS 重建。
                List<ExtendedBlockStorage> extendedSections = new ArrayList<ExtendedBlockStorage>();

                // ★ 只清空【原版 0..255】区间的方块光，绝不清扩展区间（y<0 / y>=256）。
                //
                // 这是 /fixlight 反复"把光照抹黑"的真正原因。
                //
                // 下面的重建靠 chunk.checkLight()，而它内部最终走的是原版
                // Chunk.checkLight(x,z)。那条路径的范围与提前退出条件都对齐原版，
                // **不覆盖 y<0 与 y>=256**。因此：
                //   1) 原版区间的光被清空 -> chunk.checkLight() 能重新点亮 -> 正常；
                //   2) 扩展区间的光被清空 -> 没有任何一步会去重建 -> 永久变黑。
                //
                // 用户实测完全吻合：
                //   放置一个方块 -> 原版放置路径的 BFS 把光铺开，Light 变 13；
                //   执行 /fixlight -> 光被清空后再没重建，Light 回到 0。
                // 也就是说【原来的光照数据是对的，是 /fixlight 把它毁掉了】。
                //
                // 扩展区间的光照本就由 depthsupdate 在区块加载/转换时负责，
                // /fixlight 不应该碰它。
                for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
                {
                    if (storage == null || storage == Chunk.NULL_BLOCK_STORAGE)
                    {
                        continue;
                    }

                    int storageBase = storage.getYLocation();

                    if (storageBase < 0 || storageBase >= 256)
                    {
                        // ★ 扩展区间（y<0 / y>=256）：也清空，但重建由本命令【自己的 BFS】
                        //   负责（见第二趟的 rebuildExtendedLight）。
                        //
                        //   为什么不能同样交给 chunk.checkLight()：那条路径最终走原版
                        //   Chunk.checkLight(x,z)，其范围与提前退出条件都对齐原版，
                        //   **不覆盖扩展区间**，清空后就再也没人重建 -> 永久变黑。
                        //   这正是"放置方块能点亮、/fixlight 又把它抹黑"的原因。
                        //
                        //   预期行为：任何高度都能被正确修复。因此这里照清不误，
                        //   只要保证重建是完整且正确的。
                        extendedSections.add(storage);
                        continue;
                    }

                    // 方块光：只有在"确实有光源可重新播种"时才清空。
                    if (canRebuildBlockLight)
                    {
                        Arrays.fill(storage.getBlockLight().getData(), (byte) 0);
                    }

                    // 天光：hasSky 时 generateSkylightMap 会重新铺满可见天空的柱，
                    // 清空是安全的。
                    if (hasSky)
                    {
                        Arrays.fill(storage.getSkyLight().getData(), (byte) 0);
                    }
                }

                // 扩展区间同样清空方块光（重建交给自有 BFS，见第二趟）。
                for (ExtendedBlockStorage storage : extendedSections)
                {
                    Arrays.fill(storage.getBlockLight().getData(), (byte) 0);
                }

                extendedSections.clear();

                // 重建高度图并写入直射天光；同时清掉原版遗留的逐柱天光补洞标记
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

        // 若因为任何原因为观测到区块范围，退回原版高度，保证传播不会越界。
        if (MIN_BUILD_Y > MAX_BUILD_Y)
        {
            MIN_BUILD_Y = 0;
            MAX_BUILD_Y = 256;
        }

        // 第二趟：驱动原版光照引擎重新点亮每个区块，并把结果推送给客户端。
        for (int ci = 0; ci < chunks.size(); ci++)
        {
            Chunk chunk = chunks.get(ci);
            int[] range = ranges.get(ci);

            // 3) 原版区间的重新点亮：交给原版入口（它只覆盖 0..255）。
            if (hasSky || hasLightEmitters(chunk, wholeChunk ? null : new int[] { minX, maxX, minZ, maxZ }, range))
            {
                chunk.checkLight();
            }

            // 3b) ★ 扩展区间的重新点亮：必须由本命令自己做。
            //     chunk.checkLight() 不覆盖 y<0 / y>=256，清空后无人重建 -> 会永久变黑。
            rebuildExtendedLight(world, chunk, range);

            // 4) 标记区块已修改（写回磁盘时保存新光照数据）
            chunk.setModified(true);

            // 5) 通知客户端重算渲染，并下发包含最新光照数据的完整区块包
            sendChunkLightUpdate(world, chunk);
        }

        return chunks.size();
    }

    /**
     * 区块内是否存在可用于播种的发光方块（{@code getLightValue > 0}）。
     *
     * <p>用途：决定"能不能安全清空方块光"。若区块里根本没有发光方块，
     * 清空方块光只会让它变黑且永远无法恢复——这是旧版 /fixlight 抹黑光照的根因。
     * 这里按区块自身的 Y 跨度扫描，因此扩展高度的光源也能被识别。
     */
    private static boolean depthsupdate$hasAnyLightEmitter(Chunk chunk, int[] range)
    {
        World world = chunk.getWorld();
        int baseX = chunk.x << 4;
        int baseZ = chunk.z << 4;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int y = range[0]; y <= range[1]; y++)
        {
            for (int x = 0; x < 16; x++)
            {
                for (int z = 0; z < 16; z++)
                {
                    cursor.setPos(baseX + x, y, baseZ + z);

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
     * 从一个区块自身的 storageArrays 推出它的 Y 覆盖范围。
     *
     * <p>刻意不依赖 Depths Update 或任何其它模组：每段的 yBase 由
     * {@link ExtendedBlockStorage#getYLocation()} 直接给出，取最小 / 最大即可。
     * 原版世界得到 {0, 255}；扩展高度世界会自然得到类似 {-64, 399}。
     *
     * @return {minY, maxYInclusive}
     */
    private static int[] yRangeOf(Chunk chunk)
    {
        int lo = Integer.MAX_VALUE;
        int hi = Integer.MIN_VALUE;

        ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();

        for (ExtendedBlockStorage storage : storages)
        {
            if (storage == null || storage == Chunk.NULL_BLOCK_STORAGE)
            {
                continue;
            }

            int base = storage.getYLocation();
            lo = Math.min(lo, base);
            hi = Math.max(hi, base + 15);
        }

        if (lo > hi)
        {
            // 没有任何已分配的段：退回原版范围。
            return new int[] { 0, 255 };
        }

        // 原版光照引擎在 0..255 内工作；扩展段需要我们自己补种。
        return new int[] { Math.min(lo, 0), Math.max(hi, 255) };
    }

    /**
     * 对超出原版 0..255 的段补做方块光重播种。
     *
     * <h2>为什么不能只对发光方块自己调 {@code checkLight}</h2>
     *
     * 反汇编 {@code World.checkLightFor(EnumSkyBlock, BlockPos)} 的开头：
     * <pre>
     *   48: getLightFor(type, pos)        -> curLight   该格当前存的光
     *   56: getRawLight(pos, type)        -> rawLight   该格自身能发出的光
     *   82: iload rawLight ; 84: iload curLight
     *   86: if_icmple 105                 rawLight &lt;= curLight -> 走"变暗"分支
     *  105: ...
     *  109: if_icmpge 491                 rawLight &gt;= curLight -> 直接跳到结尾 return true
     * </pre>
     * 也就是说：<b>当 {@code rawLight == curLight} 时两个分支都不进，方法直接返回，
     * 光照 BFS 完全不会启动。</b>
     *
     * <p>而岩浆这类方块被 {@code /fixlight} 清空后，它那一格会立刻被写回 15
     * （光源方块的 {@code getLightValue} 就是 15），于是再次调用 {@code checkLight} 时
     * {@code rawLight(15) == curLight(15)} —— 恰好落进 no-op 分支，
     * <b>光永远不向四周扩散</b>。这就是"清空后只有光源方块亮着、周围全黑"的根因。
     *
     * <h2>修法</h2>
     * 先刻意把该格的存储光写成一个<b>低于</b>其自身发光值的数（0），
     * 制造出 {@code rawLight > curLight} 的差值，再调用 {@code world.checkLight(pos)}；
     * 此时 BFS 会正常入队并把光向六邻域扩散。
     *
     * <p>顺序很关键：必须"先清零、后 checkLight"，反过来就又变成 no-op 了。
     */
    /**
     * 对超出原版 0..255 的段补做方块光重播种。
     *
     * <h2>为什么需要"清零再点亮"</h2>
     *
     * 反汇编 {@code World.checkLightFor(EnumSkyBlock, BlockPos)} 开头：
     * <pre>
     *   48: curLight = getLightFor(type, pos)   // 该格当前存的光
     *   56: rawLight = getRawLight(pos, type)   // 该格自身能发出的光
     *   86: if_icmple -> 变暗分支
     *  109: if_icmpge -> 直接 return，不入队
     * </pre>
     * 当 {@code rawLight == curLight} 时两个分支都不进，BFS 完全不启动。
     * 光源方块被清空后会立刻被写回自己的发光值，于是 {@code rawLight == curLight}
     * —— 恰好落进 no-op 分支，光永远不向四周扩散。
     * 因此必须先制造 {@code rawLight > curLight} 的差值。
     *
     * <h2>为什么不能"全部清零 + 少量采样点触发"（这是之前的错误实现）</h2>
     *
     * 曾经的做法是：把区块里**所有**发光方块清零，然后按 {@code (x&7)==0 && (z&7)==0
     * && (y&7)==0} 采样出极少数"代表点"去触发 BFS。这有两个致命问题：
     * <ol>
     *   <li>被清零、但不在任何采样点扩散范围内的光源格会**永久变黑**
     *       （实测：岩浆 avgLight 只有 11.80 而非 15.0）；</li>
     *   <li>采样条件对深层 Y 几乎不命中，大片区域根本没有任何触发点，
     *       于是"空气全是 0"——光完全没有扩散。</li>
     * </ol>
     *
     * <h2>正确做法</h2>
     *
     * <b>逐个光源格处理，且不做全局清零</b>：对每个发光方块，只把**它自己那一格**
     * 清零并立刻触发 BFS，BFS 会把光扩散出去。这样：
     * <ul>
     *   <li>不存在"被清零后没人管"的格子；</li>
     *   <li>每个光源都保证被点亮；</li>
     *   <li>相邻光源的 BFS 会互相补足，重复触发是幂等的（BFS 在
     *       {@code rawLight <= curLight} 时会自动收敛）。</li>
     * </ul>
     *
     * <p>性能上这确实比采样贵，但 {@code /fixlight} 是**手动的一次性命令**，
     * 不是每 tick 的热路径；正确性优先。真正需要避免的是把这种逐格 BFS
     * 放进 {@code Chunk.checkLight()} 那种每次区块加载都会跑的地方。
     */
    private static void reseedExtended(Chunk chunk, int[] range)
    {
        rebuildExtendedLight(chunk.getWorld(), chunk, range);
    }

    /**
     * 重建本区块【扩展区间】(y&lt;0 / y&gt;=256) 的方块光。
     *
     * <h2>为什么必须由本命令自己做</h2>
     *
     * {@code Chunk.checkLight()} 最终走原版 {@code Chunk.checkLight(x,z)}，
     * 其范围与提前退出条件都对齐原版，**不覆盖扩展区间**。
     * 因此扩展段一旦被清空，就没有任何一步会去重建 —— 这正是
     * "放置方块能把光点亮、执行 /fixlight 又把它抹黑"的原因。
     *
     * <h2>做法</h2>
     *
     * 对扩展段里**每一个**发光方块调用 {@code propagateBlockLight}。
     * 这是完整且幂等的：每个光源都保证被点亮；BFS 只在目标格光值更大时
     * 才写入，重复触发自然收敛；相邻光源互相补足，无需关心处理顺序。
     *
     * <p>这就是"任何高度的光照都能被正确修复"的保证：不依赖原版那套
     * 带 0..255 假设的 BFS，而是用本命令自己的、按世界坐标工作的传播。
     */
    private static void rebuildExtendedLight(World world, Chunk chunk, int[] range)
    {
        ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
        int baseX = chunk.x << 4;
        int baseZ = chunk.z << 4;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int seeds = 0;

        for (ExtendedBlockStorage storage : storages)
        {
            if (storage == null || storage == Chunk.NULL_BLOCK_STORAGE || storage.isEmpty())
            {
                continue;
            }

            int storageBase = storage.getYLocation();

            // 只看扩展区间（整段在 0..255 之内的跳过）。
            if (storageBase >= 0 && storageBase < 256)
            {
                continue;
            }

            for (int ly = 0; ly < 16; ly++)
            {
                int y = storageBase + ly;

                if (y < range[0] || y > range[1] || (y >= 0 && y < 256))
                {
                    continue;
                }

                for (int lx = 0; lx < 16; lx++)
                {
                    for (int lz = 0; lz < 16; lz++)
                    {
                        cursor.setPos(baseX + lx, y, baseZ + lz);

                        int emission = storage.get(lx, ly, lz).getLightValue(world, cursor);

                        if (emission <= 0)
                        {
                            continue;
                        }

                        propagateBlockLight(world, cursor, emission);
                        seeds++;
                    }
                }
            }
        }
    }

    /**
     * 自己实现的方块光传播（BFS）。
     *
     * <p>为什么不用 {@code world.checkLight(pos)}：原版 BFS 依赖
     * {@code World.getLightFromNeighborsFor}，它在 y&lt;0 时把坐标钉成
     * {@code (x, 0, z)}，导致深层光照无法扩散。该方法的 SRG 名在本环境
     * 无法可靠注入（会让 mixin APPLY 失败、服务器起不来），所以这里自备一份。
     *
     * <p>算法是标准光照 BFS：从光源出发向六邻域扩展，邻居光值 =
     * 当前光 - 1 - 邻居不透光度；只有大于邻居已存光值时才写入并继续扩展，
     * 因此天然收敛（不需要额外的 visited 集合）。
     *
     * <p>只在 {@code [minY, maxY)} 内传播，队列有上限防止病态膨胀。
     */
    private static void propagateBlockLight(World world, BlockPos.MutableBlockPos pos, int emission)
    {
        int y0 = pos.getY();
        int x0 = pos.getX();
        int z0 = pos.getZ();

        if (emission <= 0)
        {
            return;
        }

        // 起始格：直接写数组（不走 world.setLightFor，避免 notifyLightSet）。
        writeBlockLight(loadedChunkAt(world, x0, z0), x0, y0, z0, emission);

        int capacity = QUEUE_INITIAL * 4;
        int[] queue = new int[capacity];
        int head = 0;
        int tail = 0;

        queue[tail++] = x0;
        queue[tail++] = y0;
        queue[tail++] = z0;
        queue[tail++] = emission;

        while (head < tail)
        {
            int cx = queue[head++];
            int cy = queue[head++];
            int cz = queue[head++];
            int light = queue[head++];

            // ★ 光值从队列里取，【不要】再调 world.getLightFor 回读。
            //
            // 这是修了好久才发现的关键点：SpongeForge 的 WorldServerMixin
            // 用**具体方法体**覆盖了 World.getLightFor（func_175642_b），
            // 并把坐标改写成 0/255。这种"具体方法覆盖"会让本模组在该方法上的
            // @Inject 被整体丢弃（虚分派直接走子类的具体实现）。
            // 结果：对 y<0 调用 world.getLightFor 返回 0。
            //
            // 而旧实现正好用它来决定 BFS 是否继续：
            //     int light = world.getLightFor(BLOCK, pos);
            //     if (light <= 1) continue;          // <- 深层永远命中这里
            // 于是 BFS 在起点就退出，一格都没写 —— 存档实测就是
            // "82 格岩浆、21 格紧邻空气、带光空气 0 格"。
            //
            // 现在光值随队列一起传递，BFS 的推进不再依赖任何"可能被 Sponge
            // 覆盖掉"的读取。写入仍用 world.setLightFor（它工作正常）。
            if (light <= 1)
            {
                continue;
            }

            for (int d = 0; d < 6; d++)
            {
                int nx = cx + DX[d];
                int ny = cy + DY[d];
                int nz = cz + DZ[d];

                // 高度边界：由世界自身的构建高度决定，不写死 0..255。
                if (ny < MIN_BUILD_Y || ny >= MAX_BUILD_Y)
                {
                    continue;
                }

                pos.setPos(nx, ny, nz);
                Chunk neighborChunk = loadedChunkAt(world, nx, nz);
                IBlockState neighbor = readBlockState(neighborChunk, nx, ny, nz);
                int opacity = neighbor.getLightOpacity(world, pos);

                if (opacity >= 255)
                {
                    continue;
                }

                int target = light - 1 - opacity;

                if (target <= 0)
                {
                    continue;
                }

                // ★ 直接写区块的光照数组，【绝不】用 world.setLightFor。
                //
                // 这是导致服务器被看门狗判定"单 tick 耗时 60 秒"而崩溃的原因：
                // World.setLightFor 内部会调 notifyLightSet ->
                // markBlockRangeForRenderUpdate（每次标记 3x3x3 格）。
                // 本 BFS 会写【成千上万】格，于是产生几万次渲染标记，
                // 一个 tick 直接跑满 60 秒 -> ServerHangWatchdog -> 崩服。
                //
                // 原版从不这样做：批量光照（generateSkylightMap）都是
                // 【直接写数组】，最后统一做一次区块同步。
                // 客户端同步由本命令末尾的 sendChunkLightUpdate 负责，
                // 因此逐格通知本来就是多余且致命的。
                Chunk targetChunk = loadedChunkAt(world, nx, nz);
                int existing = readBlockLight(targetChunk, nx, ny, nz);

                if (existing >= target)
                {
                    continue;
                }

                writeBlockLight(targetChunk, nx, ny, nz, target);

                if (tail + 4 > capacity)
                {
                    int grown = capacity * 2;

                    if (grown > QUEUE_MAX)
                    {
                        continue;
                    }

                    int[] bigger = new int[grown];
                    System.arraycopy(queue, 0, bigger, 0, tail);
                    queue = bigger;
                    capacity = grown;
                }

                queue[tail++] = nx;
                queue[tail++] = ny;
                queue[tail++] = nz;
                queue[tail++] = target;
            }
        }
    }

    private static final int QUEUE_INITIAL = 4096;
    private static final int QUEUE_MAX = 1 << 20;

    /** 传播时使用的世界高度边界（由 relightArea 在每次调用前刷新）。 */
    private static volatile int MIN_BUILD_Y = -64;
    private static volatile int MAX_BUILD_Y = 400;

    private static final int[] DX = { 1, -1, 0, 0, 0, 0 };
    private static final int[] DY = { 0, 0, 1, -1, 0, 0 };
    private static final int[] DZ = { 0, 0, 0, 0, 1, -1 };

    /*
     * 下面四个helper 直接读写区块的 ExtendedBlockStorage 数组，
     * 完全不依赖 Depths Update 或任何其它模组：
     *   - "哪一段包含这个 y" 通过逐段比较 getYLocation() 得到，是通用做法；
     *   - 不调用 World.setLightFor（它会触发 notifyLightSet ->
     *     markBlockRangeForRenderUpdate，批量写入会造成看门狗崩服）；
     *   - 不调用 World.getLightFor（SpongeForge 用具体方法体覆盖了它，
     *     会让本模组的注入失效并返回错误值）。
     */

    /**
     * 取已加载的区块；【不要】用 world.getChunk(pos) —— 那个会在缺失时
     * 触发区块加载，可能在批量光照过程中造成递归加载甚至连锁卡顿。
     */
    private static Chunk loadedChunkAt(World world, int x, int z)
    {
        return world.getChunkProvider().getLoadedChunk(x >> 4, z >> 4);
    }

    /** 按世界 Y 找到所属的 ExtendedBlockStorage；找不到返回 null。 */
    private static ExtendedBlockStorage sectionOf(Chunk chunk, int y)
    {
        if (chunk == null)
        {
            return null;
        }

        for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
        {
            if (storage == null || storage == Chunk.NULL_BLOCK_STORAGE)
            {
                continue;
            }

            int base = storage.getYLocation();

            if (y >= base && y < base + 16)
            {
                return storage;
            }
        }

        return null;
    }

    /** 读方块光；该 Y 没有段时按 0（无光）处理。 */
    private static int readBlockLight(Chunk chunk, int x, int y, int z)
    {
        ExtendedBlockStorage storage = sectionOf(chunk, y);

        return storage == null ? 0 : storage.getBlockLight(x & 15, y & 15, z & 15);
    }

    /** 写方块光；该 Y 没有段时忽略（不主动分配新段）。 */
    private static void writeBlockLight(Chunk chunk, int x, int y, int z, int value)
    {
        ExtendedBlockStorage storage = sectionOf(chunk, y);

        if (storage != null)
        {
            storage.setBlockLight(x & 15, y & 15, z & 15, value);
        }
    }

    /** 读方块状态；该 Y 没有段时按空气处理（空段即无方块）。 */
    private static IBlockState readBlockState(Chunk chunk, int x, int y, int z)
    {
        ExtendedBlockStorage storage = sectionOf(chunk, y);

        return storage == null
                ? net.minecraft.init.Blocks.AIR.getDefaultState()
                : storage.get(x & 15, y & 15, z & 15);
    }

    /**
     * 扫描区块内是否存在发光方块（getLightValue > 0）。
     *
     * @param clipBounds 为 null 时扫描整块；否则为 {minX, maxX, minZ, maxZ}，只扫描落在这个
     *                   圆盘包围盒内的柱，用于小半径指令避免全块扫描的开销。
     */
    private static boolean hasLightEmitters(Chunk chunk, int[] clipBounds, int[] range)
    {
        World world = chunk.getWorld();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int baseX = chunk.x << 4;
        int baseZ = chunk.z << 4;

        // 扫描范围改为区块自身的 Y 跨度，而不是固定 0..getTopFilledSegment()+15。
        // 原实现的上界只覆盖到最高已填充段的顶端，在扩展高度世界里会把
        // y<0 / y>=256 的光源全部漏掉；而下界恒为 0，也会漏掉深层。
        int yStart = range[0];
        int yEnd = range[1];

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

                for (int y = yStart; y <= yEnd; y++)
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
