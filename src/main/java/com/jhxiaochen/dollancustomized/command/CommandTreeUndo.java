package com.jhxiaochen.dollancustomized.command;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandTreeUndo extends CommandBase {

    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "treeundo";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // 普通玩家权限
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.treeundo.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String playerName = sender.getName();

        // 如果有第二个参数，选择撤销类型
        String undoType = "all"; // 默认撤销所有
        if (args.length > 1) {
            undoType = args[1].toLowerCase();
            if (!undoType.equals("all") && !undoType.equals("logs") && !undoType.equals("leaves")) {
                throw new WrongUsageException("commands.treeundo.usage_type");
            }
        }

        List<CommandTreeKiller.OperationRecord> operations;
        int totalCount = 0;

        // 根据类型获取操作记录
        if (undoType.equals("logs")) {
            operations = CommandTreeKiller.UNDO_MANAGER.getAllOperations(playerName);
            totalCount = CommandTreeKiller.UNDO_MANAGER.getLogChangeCount(playerName);
        } else if (undoType.equals("leaves")) {
            operations = CommandTreeKiller.UNDO_MANAGER.getAllOperations(playerName);
            totalCount = CommandTreeKiller.UNDO_MANAGER.getLeafChangeCount(playerName);
        } else {
            operations = CommandTreeKiller.UNDO_MANAGER.getAllOperations(playerName);
            totalCount = CommandTreeKiller.UNDO_MANAGER.getTotalChangeCount(playerName);
        }

        if (operations.isEmpty()) {
            String errorMsg = "commands.treeundo.no_records_" + undoType;
            throw new CommandException(errorMsg);
        }

        // 如果指定了撤销数量（这里是操作数，不是方块数）
        int undoCount = 1;
        if (args.length > 0) {
            try {
                undoCount = parseInt(args[0], 1, operations.size());
            } catch (NumberFormatException e) {
                throw new WrongUsageException("commands.treeundo.invalid_count", new Object[]{operations.size()});
            }
        }

        World world = sender.getEntityWorld();
        int restoredBlocks = 0;
        int skippedBlocks = 0;

        // Debug logging
        System.out.println("[DEBUG] Starting undo operation. Player: " + playerName + ", Operations to process: " + undoCount);

        // 先备份要撤销的操作，以便恢复失败时可以回滚
        List<CommandTreeKiller.OperationRecord> operationsToRemove = new ArrayList<>();
        List<CommandTreeKiller.BlockChangeRecord> changesToRestore = new ArrayList<>();

        // 收集要恢复的方块
        for (int i = operations.size() - 1; i >= operations.size() - undoCount && i >= 0; i--) {
            CommandTreeKiller.OperationRecord operation = operations.get(i);
            operationsToRemove.add(operation);

            // 收集这个操作中的所有方块
            for (CommandTreeKiller.BlockChangeRecord change : operation.getChanges()) {
                changesToRestore.add(change);
            }
        }

        System.out.println("[DEBUG] Collected " + changesToRestore.size() + " blocks to restore");

        // 先移除操作记录，避免重复撤销
        List<CommandTreeKiller.OperationRecord> allOperations = CommandTreeKiller.UNDO_MANAGER.getAllOperations(playerName);
        allOperations.removeAll(operationsToRemove);
        System.out.println("[DEBUG] Removed " + operationsToRemove.size() + " operations from undo history");

        // 执行实际的方块恢复
        for (CommandTreeKiller.BlockChangeRecord change : changesToRestore) {
            BlockPos pos = change.getPos();

            // Debug logging - use registry name for more accurate identification
            String blockName = change.getOriginalState().getBlock().getRegistryName() != null ?
                change.getOriginalState().getBlock().getRegistryName().toString() : "unknown";
            System.out.println("[DEBUG] Attempting to restore block at " + pos +
                ", original state: " + blockName +
                ", isLog: " + change.isLog() +
                ", state: " + change.getOriginalState());

            // 检查位置是否在加载的区块内
            if (!world.isBlockLoaded(pos)) {
                System.out.println("[DEBUG] Skipped block at " + pos + " - chunk not loaded");
                skippedBlocks++;
                continue;
            }

            // 检查当前位置是否已经是空气
            IBlockState currentState = world.getBlockState(pos);
            if (currentState.getBlock() == net.minecraft.init.Blocks.AIR) {
                // 如果已经是空气，需要恢复这个方块
                System.out.println("[DEBUG] Block at " + pos + " is air - restoring original state: " + blockName);
                // 恢复方块
                if (world.setBlockState(pos, change.getOriginalState(), 3)) {
                    restoredBlocks++;
                    System.out.println("[DEBUG] Successfully restored block at " + pos);
                } else {
                    System.out.println("[DEBUG] Failed to restore block at " + pos);
                    skippedBlocks++;
                }
            } else {
                // 如果不是空气，说明这个位置已经被其他方块占用了，跳过
                System.out.println("[DEBUG] Block at " + pos + " is not air (" + blockName + "), skipping");
                skippedBlocks++;
            }

            // 只恢复指定类型的方块
            boolean shouldRestore = true;
            if (undoType.equals("logs") && !change.isLog()) {
                shouldRestore = false; // 撤销原木时跳过树叶
            } else if (undoType.equals("leaves") && change.isLog()) {
                shouldRestore = false; // 撤销树叶时跳过原木
            } else if (undoType.equals("all")) {
                shouldRestore = true; // 全部恢复
            }

            if (shouldRestore) {
                // 恢复方块到原始状态
                if (world.setBlockState(pos, change.getOriginalState(), 3)) { // 3 = 更新通知和客户端同步
                    restoredBlocks++;
                } else {
                    // 如果恢复失败，记录但不中断整个过程
                    skippedBlocks++;
                }
            }
        }

        // 检查是否所有方块都恢复成功
        boolean allSuccess = (restoredBlocks == changesToRestore.size());
        if (!allSuccess) {
            // 如果有恢复失败的方块，重新添加操作记录
            System.out.println("[DEBUG] Some blocks failed to restore, re-adding operations to undo history");
            CommandTreeKiller.UNDO_MANAGER.getOperations(playerName).addAll(operationsToRemove);
            // 计算恢复成功的比例
            double successRate = (double) restoredBlocks / changesToRestore.size();
            if (successRate < 0.5) {
                System.out.println("[DEBUG] Success rate too low: " + successRate + ". Undo may not be reliable.");
            }
        }

        // 通知执行结果
        String successKey;
        if (allSuccess) {
            // 所有方块都成功恢复，只显示成功消息
            if (undoType.equals("all")) {
                successKey = "commands.treeundo.success";
            } else {
                successKey = "commands.treeundo.success_" + undoType;
            }
            notifyCommandListener(sender, this, successKey,
                    new Object[]{restoredBlocks, CommandTreeKiller.UNDO_MANAGER.getOperationCount(playerName)});
        } else {
            // 有方块恢复失败，显示部分成功消息
            if (undoType.equals("all")) {
                successKey = "commands.treeundo.partial_success";
            } else {
                successKey = "commands.treeundo.partial_success_" + undoType;
            }
            notifyCommandListener(sender, this, successKey,
                    new Object[]{restoredBlocks, CommandTreeKiller.UNDO_MANAGER.getOperationCount(playerName)});
        }

        // 如果有跳过的方块，也通知一下（只有当不是全部成功时才显示"跳过"消息）
        if (!allSuccess && skippedBlocks > 0) {
            notifyCommandListener(sender, this, "commands.treeundo.skipped",
                    new Object[]{skippedBlocks});
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            // 第一个参数：撤销操作数量
            String playerName = sender.getName();
            List<CommandTreeKiller.OperationRecord> operations = CommandTreeKiller.UNDO_MANAGER.getAllOperations(playerName);
            return getListOfStringsMatchingLastWord(args,
                "1", "2", "3", "5", Integer.toString(Math.min(operations.size(), 10)));
        } else if (args.length == 2) {
            // 第二个参数：撤销类型
            return getListOfStringsMatchingLastWord(args, "all", "logs", "leaves");
        }
        return Collections.emptyList();
    }
}