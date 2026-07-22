package com.jhxiaochen.dollancustomized.network.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class PacketUtils {
    private PacketUtils() {}

    public static boolean validate(MessageContext ctx, String name) {
        return ctx != null && ctx.netHandler != null && ctx.side != null;
    }

    public static boolean validate(EntityPlayerMP player, String name) {
        return player != null && player.world != null;
    }

    public static boolean validate(BlockPos pos, String name) {
        return pos != null;
    }

    public static void log(String packet, String message) {
        System.out.println("[Packet] " + packet + ": " + message);
    }

    public static void error(String packet, Exception e) {
        System.err.println("[Packet] " + packet + " error: " + e.getMessage());
    }

    public static TextComponentString errorText(String msg) {
        return new TextComponentString("§cError: " + msg);
    }

    public static TextComponentString successText(String msg) {
        return new TextComponentString("§aSuccess: " + msg);
    }
}