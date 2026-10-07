package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandTP;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 指令层拦截（单文件方案）：在指令输入时就阻止越界传送，绝不传送后再拉回。
 *
 * 目标：net.minecraft.command.CommandTP —— 1.12.2 运行时 /tp 命令的真实类
 * （命令名 "tp"，继承 CommandBase；注意它和 net.minecraft.command.server.CommandTeleport
 * （命令名 "teleport"，/teleport）是两个独立命令类）。
 *
 * 两个注入点（MCP 名，由 remapJar 生成 refmap 映射到运行时 notch 短名）：
 * 1) teleportEntityToCoordinates —— 传送核心静态方法 (Entity, CoordinateArg×5)。
 *    /tp 的所有坐标形态最终都汇聚到这里，HEAD 越界即 cancel。
 *    边界检查必须用 CoordinateArg.getResult()（原版解析出的"最终绝对坐标"）：
 *    相对坐标 ~N 时 getAmount() 只是增量 N，用它检查会漏判（曾导致 ~ 形态漏拦）。
 * 2) execute —— 命令入口，更早拦截。形态判定与原版逐字节一致：
 *    - 2/4/6 参：args[0] 是目标实体（@s / 玩家名 / 选择器），坐标从 [1] 开始；
 *    - 其余（1/3/5 参）：目标是命令发送者本人，坐标从 [0] 开始；
 *    - 1/2 参：传送到实体位置，无坐标输入，不拦截；
 *    - 坐标不足（< coordStart + 3）的形态：不拦截，交 vanilla 报错。
 *    相对坐标 ~ 的解析基准是"目标实体当前位置"（原版 parseCoordinate(entity.posX, ...)）。
 */
@Mixin(CommandTP.class)
public abstract class MixinCommandTP {

    private static final Logger LOGGER = LogManager.getLogger("DollanTP");

    // ---- 传送核心（所有 /tp 坐标形态的最终汇聚点）----
    @Inject(method = "teleportEntityToCoordinates", at = @At("HEAD"), cancellable = true)
    private static void dollan$limitTeleportCore(Entity entity,
            CommandBase.CoordinateArg x, CommandBase.CoordinateArg y,
            CommandBase.CoordinateArg z, CommandBase.CoordinateArg yaw,
            CommandBase.CoordinateArg pitch, CallbackInfo ci) {
        World world = entity.world;
        if (world == null) {
            return;
        }
        WorldBorder border = world.getWorldBorder();
        if (border == null) {
            return;
        }
        double cx = border.getCenterX();
        double cz = border.getCenterZ();
        double d = border.getDiameter();
        double minX = cx - d / 2.0D;
        double maxX = cx + d / 2.0D;
        double minZ = cz - d / 2.0D;
        double maxZ = cz + d / 2.0D;

        // getResult() = 最终绝对坐标（相对坐标已加基准），getAmount() 只是原始增量/数值
        double tx = x.getResult();
        double tz = z.getResult();
        boolean blocked = (tx < minX || tx > maxX || tz < minZ || tz > maxZ);
        LOGGER.debug("[DollanTP] CommandTP.teleportEntityToCoordinates x={} z={} dim={} border=(c={},{} d={}) blocked={}",
                tx, tz, world.provider.getDimension(), cx, cz, d, blocked);
        if (blocked) {
            ci.cancel();
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP) entity).sendMessage(new TextComponentTranslation("commands.dollancustomized.tp_out_of_border"));
            }
        }
    }

    // ---- 命令执行入口（更早拦截）----
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private void dollan$limitExecute(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        if (args == null || args.length < 3) {
            return;
        }
        // 原版形态判定：2/4/6 参 → args[0] 是目标实体；其余 → 目标是命令发送者本人
        int coordStart;
        Entity target;
        if (args.length == 2 || args.length == 4 || args.length == 6) {
            target = dollan$resolveTarget(server, sender, args[0]);
            if (target == null) {
                return; // 目标解析失败（含 1/2 参的实体间传送形态）：交给 vanilla 处理
            }
            coordStart = 1;
        } else {
            if (!(sender instanceof EntityPlayerMP)) {
                return; // 非玩家发送者（控制台等）的"自身"形态 vanilla 会报错，无需拦截
            }
            target = (EntityPlayerMP) sender;
            coordStart = 0;
        }
        // 1/2 参（传送到实体位置，无坐标输入）或坐标不足的形态：交给 vanilla
        if (args.length < coordStart + 3) {
            return;
        }
        World world = target.world;
        if (world == null) {
            return;
        }
        WorldBorder border = world.getWorldBorder();
        if (border == null) {
            return;
        }
        double cx = border.getCenterX();
        double cz = border.getCenterZ();
        double d = border.getDiameter();
        double minX = cx - d / 2.0D;
        double maxX = cx + d / 2.0D;
        double minZ = cz - d / 2.0D;
        double maxZ = cz + d / 2.0D;

        // 相对坐标 ~ 以"目标实体当前位置"为基准（与原版 parseCoordinate 一致）
        double tx = dollan$resolveXZ(target.posX, args[coordStart]);
        double tz = dollan$resolveXZ(target.posZ, args[coordStart + 2]);
        if (Double.isNaN(tx) || Double.isNaN(tz)) {
            return; // 解析失败（^ 或非法输入）：交给 vanilla 报错
        }
        boolean blocked = (tx < minX || tx > maxX || tz < minZ || tz > maxZ);
        LOGGER.debug("[DollanTP] CommandTP.execute x={} z={} dim={} border=(c={},{} d={}) blocked={}",
                tx, tz, world.provider.getDimension(), cx, cz, d, blocked);
        if (blocked) {
            ci.cancel();
            if (sender instanceof EntityPlayerMP) {
                ((EntityPlayerMP) sender).sendMessage(new TextComponentTranslation("commands.dollancustomized.tp_out_of_border"));
            } else if (target instanceof EntityPlayerMP) {
                ((EntityPlayerMP) target).sendMessage(new TextComponentTranslation("commands.dollancustomized.tp_out_of_border"));
            }
        }
    }

    /**
     * 解析目标实体：支持玩家名 / 选择器（@s、@p、@a、@e 等）/ UUID，
     * 与原版 execute 使用同一入口 CommandBase.getEntity。解析失败返回 null，交给 vanilla 报错。
     */
    private static Entity dollan$resolveTarget(MinecraftServer server, ICommandSender sender, String s) {
        try {
            return CommandBase.getEntity(server, sender, s);
        } catch (CommandException e) {
            return null;
        }
    }

    /**
     * 解析 X/Z 坐标字符串，逻辑与原版 CommandBase.parseCoordinate 的 X/Z 分支一致：
     * - 绝对数字：直接返回（整数且无小数点时 +0.5 中心对齐，与原版一致）；
     * - ~ / ~N：base + N；
     * - ^ 或非法输入：返回 NaN（1.12.2 原版不支持 ^，交给 vanilla 报错；核心注入点仍会兜底）。
     */
    private static double dollan$resolveXZ(double base, String s) {
        if (s == null) {
            return base;
        }
        s = s.trim();
        if (s.isEmpty()) {
            return base;
        }
        if (s.startsWith("^")) {
            return Double.NaN; // 1.12.2 无 ^ 局部坐标：不拦截，交给 vanilla 报错
        }
        boolean tilde = s.startsWith("~");
        if (tilde && Double.isNaN(base)) {
            return Double.NaN;
        }
        double d0 = 0.0D;
        if (!tilde || s.length() > 1) {
            boolean hasDot = s.contains(".");
            String num = tilde ? s.substring(1) : s;
            try {
                d0 += Double.parseDouble(num);
            } catch (NumberFormatException e) {
                return Double.NaN;
            }
            if (!hasDot && !tilde) {
                d0 += 0.5D; // centerBlock：与原版 parseCoordinate(..., true) 一致
            }
        }
        return d0 + (tilde ? base : 0.0D);
    }
}
