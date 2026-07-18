package com.jhxiaochen.dollancustomized.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

public class CommandTreeUndoDebug extends CommandBase {

    @Override
    public String getName() {
        return "treeundodebug";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/treeundodebug";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String playerName = sender.getName();

        // 显示操作统计
        int operationCount = CommandTreeKiller.UNDO_MANAGER.getOperationCount(playerName);
        int totalBlocks = CommandTreeKiller.UNDO_MANAGER.getTotalChangeCount(playerName);
        int logBlocks = CommandTreeKiller.UNDO_MANAGER.getLogChangeCount(playerName);
        int leafBlocks = CommandTreeKiller.UNDO_MANAGER.getLeafChangeCount(playerName);

        sender.sendMessage(new TextComponentString("§a=== 撤销操作统计 ==="));
        sender.sendMessage(new TextComponentString("§a操作批次: " + operationCount + " 次"));
        sender.sendMessage(new TextComponentString("§b总方块数: " + totalBlocks + " 个"));
        sender.sendMessage(new TextComponentString("§b原木数量: " + logBlocks + " 个"));
        sender.sendMessage(new TextComponentString("§e树叶数量: " + leafBlocks + " 个"));
        sender.sendMessage(new TextComponentString("§d================"));

        if (operationCount == 0) {
            sender.sendMessage(new TextComponentString("§c没有找到任何撤销操作记录！"));
            return;
        }

        // 显示每个操作的详细信息
        List<CommandTreeKiller.OperationRecord> operations = CommandTreeKiller.UNDO_MANAGER.getAllOperations(playerName);
        int displayCount = Math.min(operations.size(), 5); // 只显示最近的5个操作

        sender.sendMessage(new TextComponentString("§a最近 " + displayCount + " 个操作详情:"));

        for (int i = 0; i < displayCount; i++) {
            CommandTreeKiller.OperationRecord operation = operations.get(i);
            int opLogCount = 0;
            int opLeafCount = 0;

            // 统计这个操作中的方块类型
            for (CommandTreeKiller.BlockChangeRecord change : operation.getChanges()) {
                if (change.isLog()) {
                    opLogCount++;
                } else {
                    opLeafCount++;
                }
            }

            String modeStr = "";
            switch (operation.getMode()) {
                case "logs": modeStr = "§c只处理原木"; break;
                case "leaves": modeStr = "§a只处理树叶"; break;
                case "both": modeStr = "§b处理原木和树叶"; break;
            }

            sender.sendMessage(new TextComponentString("§7[" + (i + 1) + "] " + modeStr +
                             " §e方块数: " + operation.getChanges().size() +
                             " §b原木: " + opLogCount + " §e树叶: " + opLeafCount +
                             " §a时间: " + operation.getTimestamp()));
        }

        if (operations.size() > 5) {
            sender.sendMessage(new TextComponentString("§7... 还有 " + (operations.size() - 5) + " 个操作未显示"));
        }
    }
}