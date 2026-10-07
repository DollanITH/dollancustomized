package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTP;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
 * （命令名 "teleport"，/teleport）是两个独立命令类，之前误注入 CommandTeleport 导致 /tp 完全没被拦）。
 *
 * 两个注入点（SRG / MCP 名）：
 * 1) func_189863_a = teleportEntityToCoordinates —— 传送核心静态方法
 *    (Entity, CoordinateArg x/y/z/yaw/pitch)。/tp 的所有形态（3 参 / 4 参 / 5-6 参）
 *    最终都汇聚到这里完成实际传送，HEAD 越界即 cancel。
 * 2) func_184881_a = execute —— 命令入口，更早拦截（双保险，仅覆盖"传自己"的 3/5 参形态）。
 *
 * 注：mixin 注解的 method 必须写 MCP 名（开发环境名），由 remapJar 生成 refmap
 * 映射到运行时 notch 短名；直接写 SRG 名（func_XXX）会导致运行时
 * "Cannot resolve method" 报错。
 */
@Mixin(CommandTP.class)
public abstract class MixinCommandTP {

    private static final Logger LOGGER = LogManager.getLogger("DollanTP");

    // ---- 传送核心（所有 /tp 形态的最终汇聚点）----
    @Inject(method = "teleportEntityToCoordinates", at = @At("HEAD"), cancellable = true)
    private static void dollan$limitTeleportCore(Entity entity,
            CommandBase.CoordinateArg x, CommandBase.CoordinateArg y,
            CommandBase.CoordinateArg z, CommandBase.CoordinateArg yaw,
            CommandBase.CoordinateArg pitch, CallbackInfo ci) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
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

        double tx = x.getAmount();
        double tz = z.getAmount();
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

    // ---- 命令执行入口（更早拦截；仅"传自己"的 3/5 参形态）----
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private void dollan$limitExecute(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        if (args == null || args.length < 3) {
            return;
        }
        if (!(sender instanceof EntityPlayerMP)) {
            return; // 4/6 参（传别人）与命令方块场景由 teleportEntityToCoordinates 兜底
        }
        // 奇数长度（3/5）坐标从 index 0 开始（传自己）；偶数长度（4/6）index 0 是目标玩家名，跳过
        if (args.length % 2 == 0) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;
        World world = player.getEntityWorld();
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

        CommandBase.CoordinateArg xArg;
        CommandBase.CoordinateArg zArg;
        try {
            xArg = CommandBase.parseCoordinate(player.posX, args[0], true);
            zArg = CommandBase.parseCoordinate(player.posZ, args[2], true);
        } catch (Exception ex) {
            return; // 坐标格式非法：交给原逻辑报错
        }
        double tx = xArg.getAmount();
        double tz = zArg.getAmount();
        boolean blocked = (tx < minX || tx > maxX || tz < minZ || tz > maxZ);
        LOGGER.info("[DollanTP] CommandTP.execute x={} z={} dim={} border=(c={},{} d={}) blocked={}",
                tx, tz, world.provider.getDimension(), cx, cz, d, blocked);
        if (blocked) {
            ci.cancel();
            player.sendMessage(new TextComponentTranslation("commands.dollancustomized.tp_out_of_border"));
        }
    }
}
