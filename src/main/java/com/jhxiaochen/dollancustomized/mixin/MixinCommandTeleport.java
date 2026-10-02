package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 为 /tp 指令添加世界边界限制：
 * 解析目标 x、z 坐标（绝对坐标，支持 ~ 相对坐标），若任一超出
 * 当前世界的 WorldBorder（世界边界）范围，则阻止该 tp 执行。
 */
@Mixin(CommandTeleport.class)
public class MixinCommandTeleport {

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private void dollan$limitTeleport(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci)
            throws CommandException {
        // 没有坐标参数（如 /tp <玩家>）不检查
        if (args == null || args.length < 3) {
            return;
        }

        // 偶数长度（4/6）时 args[0] 是目标玩家名，坐标从 index 1 开始；奇数长度（3/5）坐标从 index 0 开始
        int i = (args.length % 2 == 0) ? 1 : 0;
        if (i + 2 >= args.length) {
            return;
        }

        World world = sender.getEntityWorld();
        if (world == null) {
            return;
        }
        WorldBorder border = world.getWorldBorder();
        if (border == null) {
            return;
        }

        CommandBase.CoordinateArg xArg = CommandBase.parseCoordinate(sender.getPosition().getX(), args[i], true);
        CommandBase.CoordinateArg zArg = CommandBase.parseCoordinate(sender.getPosition().getZ(), args[i + 2], true);
        double x = xArg.getAmount();
        double z = zArg.getAmount();

        double minX = border.getCenterX() - border.getDiameter() / 2.0D;
        double maxX = border.getCenterX() + border.getDiameter() / 2.0D;
        double minZ = border.getCenterZ() - border.getDiameter() / 2.0D;
        double maxZ = border.getCenterZ() + border.getDiameter() / 2.0D;

        if (x < minX || x > maxX || z < minZ || z > maxZ) {
            throw new CommandException("commands.teleport.outOfWorld");
        }
    }
}
