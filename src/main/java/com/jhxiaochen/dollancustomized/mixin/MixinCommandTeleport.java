package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
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
 * 与 MixinCommandTP（/tp）同逻辑：
 * 1) doTeleport（SRG func_189862_a）—— 传送核心静态方法 (Entity, CoordinateArg×5)，
 *    所有 /teleport 形态的最终汇聚点，HEAD 越界即 cancel。
 * 2) execute（SRG func_184881_a）—— 命令入口，更早拦截（仅"传自己"的 3/5 参形态）。
 *
 * method 必须写 MCP 名（refmap 由 remapJar 生成，运行时映射到 notch 短名）。
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
        LOGGER.info("[DollanTP] CommandTeleport.doTeleport x={} z={} dim={} border=(c={},{} d={}) blocked={}",
                tx, tz, world.provider.getDimension(), cx, cz, d, blocked);
        if (blocked) {
            ci.cancel();
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP) entity).sendMessage(new TextComponentString("你不能传送出世界边界！"));
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
            return; // 4/6 参（传别人）与命令方块场景由 doTeleport 兜底
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
        LOGGER.info("[DollanTP] CommandTeleport.execute x={} z={} dim={} border=(c={},{} d={}) blocked={}",
                tx, tz, world.provider.getDimension(), cx, cz, d, blocked);
        if (blocked) {
            ci.cancel();
            player.sendMessage(new TextComponentString("你不能传送出世界边界！"));
        }
    }
}
