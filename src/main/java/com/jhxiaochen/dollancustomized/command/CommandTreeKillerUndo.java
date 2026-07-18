package com.jhxiaochen.dollancustomized.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandTreeKillerUndo extends CommandBase {

    private final CommandTreeKiller.UndoManager undoManager = CommandTreeKiller.UNDO_MANAGER;

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
        List<CommandTreeKiller.BlockChangeRecord> records = undoManager.getRecords(playerName);

        if (records.isEmpty()) {
            throw new CommandException("commands.treeundo.no_records");
        }

        // 如果指定了撤销数量
        int undoCount = 1;
        if (args.length > 0) {
            try {
                undoCount = parseInt(args[0], 1, records.size());
            } catch (NumberFormatException e) {
                throw new WrongUsageException("commands.treeundo.invalid_count", new Object[0]);
            }
        }

        World world = sender.getEntityWorld();
        int restoredBlocks = 0;

        // 撤销最近的记录
        for (int i = records.size() - undoCount; i < records.size(); i++) {
            CommandTreeKiller.BlockChangeRecord record = records.get(i);
            BlockPos pos = record.getPos();

            // 恢复方块
            if (world.setBlockState(pos, record.getOriginalState())) {
                restoredBlocks++;
            }
        }

        // 移除已撤销的记录
        records.subList(records.size() - undoCount, records.size()).clear();

        // 通知执行结果
        notifyCommandListener(sender, this, "commands.treeundo.success",
                new Object[]{restoredBlocks, undoManager.getUndoCount(playerName)});
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            String playerName = sender.getName();
            List<CommandTreeKiller.BlockChangeRecord> records = undoManager.getRecords(playerName);

            // 提供建议的撤销数量（1, 2, 3, 4）
            return getListOfStringsMatchingLastWord(args,
                "1", "2", "3", "4", Integer.toString(Math.min(records.size(), 5)));
        }
        return Collections.emptyList();
    }
}