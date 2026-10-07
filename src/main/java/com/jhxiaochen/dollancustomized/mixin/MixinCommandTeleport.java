package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
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
 * 指令层拦截：/teleport 命令（net.minecraft.command.server.CommandTeleport）。
 *
 * 与 MixinCommandTP（/tp）同思路，但注意两者差异：
 * 1) /teleport 恒为 ≥4 参，args[0] 永远是目标实体，坐标从 [1] 开始（x/y/z）；
 * 2) /teleport 的相对坐标基准是"发送者位置"（原版 sender.getPositionVector()），
 *    不是目标实体位置 —— 与 /tp 不同，拦截时必须一致，否则 ~ 形态漏判。
 *
 * 注入点（MCP 名，refmap 由 remapJar 生成）：
 * 1) doTeleport（SRG func_189862_a）—— 传送核心静态方法 (Entity, CoordinateArg×5)，
 *    所有 /teleport 形态的最终汇聚点，HEAD 越界即 cancel。
 *    边界检查必须用 CoordinateArg.getResult()（最终绝对坐标）而非 getAmount()。
 * 2) execute（SRG func_184881_a）—— 命令入口，更早拦截。
 */
@Mixin(CommandTeleport.class)
public abstract class MixinCommandTeleport {

    private static final Logger LOGGER = LogManager.getLogger("DollanTP");

    // ---- 传送核心（所有 /teleport 形态的最终汇聚点）----
    @Inject(method = "doTeleport", at = @At("HEAD"), cancellable = true)
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
        LOGGER.debug("[DollanTP] CommandTeleport.doTeleport x={} z={} dim={} border=(c={},{} d={}) blocked={}",
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
        if (args == null || args.length < 4) {
            return; // 原版 /teleport 要求 ≥4 参，其余交给 vanilla 报错
        }
        Entity target = dollan$resolveTarget(server, sender, args[0]);
        if (target == null) {
            return; // 目标解析失败：交给 vanilla 报错
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

        // 相对坐标基准 = 发送者位置（原版 sender.getPositionVector()），与 /tp 不同
        Vec3d basePos = sender.getPositionVector();
        double tx = dollan$resolveXZ(basePos.x, args[1]);
        double tz = dollan$resolveXZ(basePos.z, args[3]);
        if (Double.isNaN(tx) || Double.isNaN(tz)) {
            return; // 解析失败（^ 或非法输入）：交给 vanilla 报错
        }
        boolean blocked = (tx < minX || tx > maxX || tz < minZ || tz > maxZ);
        LOGGER.debug("[DollanTP] CommandTeleport.execute x={} z={} dim={} border=(c={},{} d={}) blocked={}",
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
