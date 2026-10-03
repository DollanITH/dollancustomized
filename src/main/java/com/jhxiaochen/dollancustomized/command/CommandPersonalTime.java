package com.jhxiaochen.dollancustomized.command;

import com.jhxiaochen.dollancustomized.personaltime.PersonalTimeManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * /ptime 命令：每个玩家可以设置自己独立的时间，不影响服务器世界时间。
 *
 * 用法（文案均已本地化，见 lang/zh_cn.lang 与 lang/en_us.lang）：
 *   /ptime set day|night|<0-24000>   设置个人时间
 *   /ptime reset                     恢复为与服务器时间同步
 *   /ptime status                    查看当前个人时间状态
 */
public class CommandPersonalTime extends CommandBase {

    /** 语言文件中的词条前缀。 */
    private static final String LANG = "commands.dollancustomized.ptime";
    public static final String NAME = "ptime";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return LANG + ".usage";
    }

    /**
     * 仅影响玩家自己客户端的显示时间，不干扰服务器世界时间，因此任何玩家都可用。
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException(LANG + ".playeronly");
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;

        if (args.length == 0) {
            sendMessage(player, LANG + ".usage");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "set":
                handleSet(player, args);
                break;
            case "reset":
                handleReset(player);
                break;
            case "status":
                handleStatus(player);
                break;
            default:
                sendMessage(player, LANG + ".unknown", args[0], getUsage(sender));
        }
    }

    private void handleSet(EntityPlayerMP player, String[] args) {
        if (args.length < 2) {
            sendMessage(player, LANG + ".usage.set");
            return;
        }
        String timeArg = args[1];
        long ticks;
        if (timeArg.equalsIgnoreCase("day")) {
            ticks = 1000L; // 清晨 6:00
        } else if (timeArg.equalsIgnoreCase("night")) {
            ticks = 14000L; // 夜晚 22:00
        } else {
            try {
                ticks = Long.parseLong(timeArg);
            } catch (NumberFormatException e) {
                sendMessage(player, LANG + ".invalid", timeArg);
                return;
            }
            if (ticks < 0) {
                sendMessage(player, LANG + ".toosmall", timeArg);
                return;
            }
        }

        World world = player.getServerWorld();
        long worldTime = world.getWorldTime();
        // 快进到下一个"0"时刻再加目标 ticks，得到玩家想要的绝对世界时间，再换算成偏移。
        long desiredPersonalTime = (long) Math.ceil(worldTime / 24000.0f) * 24000 + ticks;
        long offset = desiredPersonalTime - worldTime;

        PersonalTimeManager.instance().setOffset(player.getUniqueID(), offset);
        sendMessage(player, LANG + ".set.success", ticks, ticksToRealTime(ticks % 24000));

        // 立即补发一个时间同步包，让玩家的天空马上变化（拦截器会自动套用上面的偏移）。
        resendTimePacket(player);
    }

    private void handleReset(EntityPlayerMP player) {
        PersonalTimeManager.instance().resetOffset(player.getUniqueID());
        sendMessage(player, LANG + ".reset.success");
        resendTimePacket(player);
    }

    private void handleStatus(EntityPlayerMP player) {
        long offset = PersonalTimeManager.instance().getOffset(player.getUniqueID());
        if (offset == 0L) {
            sendMessage(player, LANG + ".status.sync");
        } else {
            World world = player.getServerWorld();
            long personalTime = (world.getWorldTime() + offset) % 24000;
            if (personalTime < 0) {
                personalTime += 24000;
            }
            // 用两个自包含模板，分别对应"快/慢"，避免把 key 当参数传入导致不翻译。
            String key = offset > 0 ? LANG + ".status.ahead" : LANG + ".status.behind";
            sendMessage(player, key, ticksToRealTime(personalTime), Math.abs(offset));
        }
    }

    /**
     * 立即向玩家补发一个基于当前服务器世界时间的时间同步包。
     * 该包会经过拦截器，自动应用玩家自己的偏移，从而即时生效。
     */
    private void resendTimePacket(EntityPlayerMP player) {
        World world = player.getServerWorld();
        boolean doDaylightCycle = world.getGameRules().getBoolean("doDaylightCycle");
        player.connection.sendPacket(
                new SPacketTimeUpdate(world.getTotalWorldTime(), world.getWorldTime(), doDaylightCycle));
    }

    /** 绿色前缀 + 黄色可翻译正文，随客户端语言渲染。 */
    private void sendMessage(EntityPlayerMP player, String key, Object... args) {
        TextComponentString prefix = new TextComponentString(TextFormatting.GREEN + "[DollanPersonalTime] ");
        TextComponentTranslation body = new TextComponentTranslation(key, args);
        body.getStyle().setColor(TextFormatting.YELLOW);
        prefix.appendSibling(body);
        player.sendMessage(prefix);
    }

    /** 把一天内的 ticks 换算成 AM/PM 时间显示。 */
    private static String ticksToRealTime(long ticks) {
        int hours = (int) (ticks / 1000.0) + 6;
        int minutes = (int) (((ticks % 1000) / 1000.0) * 60.0);

        String suffix = "AM";
        if (hours >= 12) {
            hours -= 12;
            suffix = "PM";
            if (hours >= 12) {
                hours -= 12;
                suffix = "AM";
            }
        }
        if (hours == 0) {
            hours += 12;
        }
        return hours + ":" + String.format("%02d", minutes) + " " + suffix;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
                                          @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "set", "reset", "status");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return getListOfStringsMatchingLastWord(args, "day", "night");
        }
        return Collections.emptyList();
    }
}
