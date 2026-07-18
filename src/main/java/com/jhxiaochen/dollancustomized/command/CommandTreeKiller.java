package com.jhxiaochen.dollancustomized.command;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.minecraft.block.BlockLeaves.DECAYABLE;

public class CommandTreeKiller extends CommandBase {
    /**
     * 存储撤销记录的单例管理器
     */
    public static final UndoManager UNDO_MANAGER = new UndoManager();

    /**
     * 存储方块变更记录
     */
    public static class BlockChangeRecord {
        private final BlockPos pos;
        private final IBlockState originalState;
        private final long timestamp;
        private final String playerName;

        public BlockChangeRecord(BlockPos pos, IBlockState originalState, String playerName) {
            this.pos = pos.toImmutable();
            this.originalState = originalState;
            this.timestamp = System.currentTimeMillis();
            this.playerName = playerName;
        }

        public BlockPos getPos() {
            return pos;
        }

        public IBlockState getOriginalState() {
            return originalState;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getPlayerName() {
            return playerName;
        }
    }

    /**
     * 撤销管理器
     */
    public static class UndoManager {
        private final Map<String, List<BlockChangeRecord>> playerUndoRecords = new ConcurrentHashMap<>();
        private static final int MAX_RECORDS_PER_PLAYER = 5;

        public void addRecord(BlockChangeRecord record) {
            playerUndoRecords.computeIfAbsent(record.getPlayerName(), k -> new ArrayList<>())
                            .add(record);

            // 限制每个玩家的记录数量
            List<BlockChangeRecord> records = playerUndoRecords.get(record.getPlayerName());
            if (records.size() > MAX_RECORDS_PER_PLAYER) {
                records.remove(0);
            }
        }

        public List<BlockChangeRecord> getRecords(String playerName) {
            return playerUndoRecords.getOrDefault(playerName, new ArrayList<>());
        }

        public void clearRecords(String playerName) {
            playerUndoRecords.remove(playerName);
        }

        public int getUndoCount(String playerName) {
            return playerUndoRecords.getOrDefault(playerName, new ArrayList<>()).size();
        }
    }

    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "treekiller";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // 普通玩家权限
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.treekiller.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.treekiller.usage", new Object[0]);
        }

        // 解析半径
        int radius;
        try {
            radius = parseInt(args[0], 1, 100); // 最大半径限制为100
        } catch (NumberFormatException e) {
            throw new WrongUsageException("commands.treekiller.invalidRadius", new Object[0]);
        }

        // 解析模式
        String mode = args[1].toLowerCase();
        boolean updateLeaves = mode.equals("both") || mode.equals("leaves");
        boolean removeLogs = mode.equals("logs") || mode.equals("both");

        if (!mode.equals("logs") && !mode.equals("leaves") && !mode.equals("both")) {
            throw new WrongUsageException("commands.treekiller.usage", new Object[0]);
        }

        World world = sender.getEntityWorld();
        BlockPos centerPos = sender.getPosition();
        String playerName = sender.getName();

        // 统计数据
        int logsRemoved = 0;
        int leavesUpdated = 0;
        List<BlockPos> leafPositions = new ArrayList<>();
        List<BlockChangeRecord> changeRecords = new ArrayList<>();

        
        // 初始搜索范围
        int startX = centerPos.getX() - radius;
        int startY = Math.max(0, centerPos.getY() - radius);
        int startZ = centerPos.getZ() - radius;
        int endX = centerPos.getX() + radius;
        int endY = Math.min(255, centerPos.getY() + radius);
        int endZ = centerPos.getZ() + radius;

        // 第一遍：找到所有原木
        List<BlockPos> logPositions = new ArrayList<>();

        // 搜索所有方块以找到原木
        BlockPos.MutableBlockPos searchPos = new BlockPos.MutableBlockPos();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    searchPos.setPos(x, y, z);
                    IBlockState state = world.getBlockState(searchPos);
                    Block block = state.getBlock();

                    if (isLogBlock(block)) {
                        BlockPos pos = searchPos.toImmutable();
                        logPositions.add(pos);
                    }
                }
            }
        }

        if (removeLogs) {
            // 移除原木
            for (BlockPos pos : logPositions) {
                IBlockState originalState = world.getBlockState(pos);
                if (world.setBlockToAir(pos)) {
                    // 记录变更
                    changeRecords.add(new BlockChangeRecord(pos, originalState, playerName));
                    logsRemoved++;
                }
            }
        }

        // 第二遍：更新树叶状态（如果启用）
        if (updateLeaves) {
            // 找到所有树叶
            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    for (int z = startZ; z <= endZ; z++) {
                        searchPos.setPos(x, y, z);
                        IBlockState state = world.getBlockState(searchPos);
                        Block block = state.getBlock();

                        if (isLeafBlock(block) && block.getDefaultState().getValue(DECAYABLE)) {
                            BlockPos pos = searchPos.toImmutable();
                            if (shouldRemoveLeaf(world, pos)) {
                                // 记录变更
                                changeRecords.add(new BlockChangeRecord(pos, state, playerName));
                                world.setBlockToAir(pos);
                                leavesUpdated++;
                            }
                        }
                    }
                }
            }
        }

        
        // 保存撤销记录
        for (BlockChangeRecord record : changeRecords) {
            UNDO_MANAGER.addRecord(record);
        }

        // 通知执行结果
        if (mode.equals("both")) {
            notifyCommandListener(sender, this, "commands.treekiller.success_both",
                    new Object[]{radius, logsRemoved, leavesUpdated});
        } else if (mode.equals("logs")) {
            notifyCommandListener(sender, this, "commands.treekiller.success_logs",
                    new Object[]{radius, logsRemoved});
        } else {
            notifyCommandListener(sender, this, "commands.treekiller.success_leaves",
                    new Object[]{radius, leavesUpdated});
        }

        // 通知撤销记录已保存
        int undoCount = UNDO_MANAGER.getUndoCount(playerName);
        notifyCommandListener(sender, this, "commands.treekiller.undo_saved",
                new Object[]{undoCount});
    }

    /**
     * 检查是否是原木方块
     */
    private boolean isLogBlock(Block block) {
        // 检查是否是已知的原木方块
        return block == Blocks.LOG || block == Blocks.LOG2 || block instanceof BlockLog;
    }

    /**
     * 检查是否是树叶方块
     */
    private boolean isLeafBlock(Block block) {
        // 检查是否是已知的树叶方块
        return block == Blocks.LEAVES || block == Blocks.LEAVES2 || block instanceof BlockLeaves;
    }

    /**
     * 判断树叶是否应该移除（没有连接到原木）
     */
    private boolean shouldRemoveLeaf(World world, BlockPos leafPos) {
        // 如果树叶已经有原木连接，保留它
        if (hasLogConnection(world, leafPos)) {
            return false;
        }

        // 检查周围3格范围内是否有原木
        int searchRange = 3;
        for (int dx = -searchRange; dx <= searchRange; dx++) {
            for (int dy = -searchRange; dy <= searchRange; dy++) {
                for (int dz = -searchRange; dz <= searchRange; dz++) {
                    BlockPos checkPos = leafPos.add(dx, dy, dz);
                    if (world.isBlockLoaded(checkPos) && isLogBlock(world.getBlockState(checkPos).getBlock())) {
                        return false; // 找到附近的原木，保留树叶
                    }
                }
            }
        }

        return isLeafBlock(world.getBlockState(leafPos).getBlock()) ? world.getBlockState(leafPos).getValue(DECAYABLE) : true; // 没有找到附近的原木，移除树叶
    }

    /**
     * 检查树叶是否有原木连接
     */
    private boolean hasLogConnection(World world, BlockPos pos) {
        // 检查6个相邻方向
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos adjacentPos = pos.offset(facing);
            if (world.isBlockLoaded(adjacentPos) && isLogBlock(world.getBlockState(adjacentPos).getBlock())) {
                return true;
            }
        }

        // 检查对角方向（额外检查）
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    BlockPos diagonalPos = pos.add(dx, dy, dz);
                    if (world.isBlockLoaded(diagonalPos) && isLogBlock(world.getBlockState(diagonalPos).getBlock())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            // 半径参数
            return getListOfStringsMatchingLastWord(args, "5", "10", "15", "20", "25", "30", "50");
        } else if (args.length == 2) {
            // 模式参数
            return getListOfStringsMatchingLastWord(args, "logs", "leaves", "both");
        }
        return Collections.emptyList();
    }
}