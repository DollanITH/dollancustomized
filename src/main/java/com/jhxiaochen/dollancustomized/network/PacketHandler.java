package com.jhxiaochen.dollancustomized.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("dollancustomized");

    public static void init() {
        // 注册网络包
        INSTANCE.registerMessage(PacketUpdateJacarandaSign.Handler.class, PacketUpdateJacarandaSign.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(PacketOpenJacarandaSignGui.Handler.class, PacketOpenJacarandaSignGui.class, 1, Side.CLIENT);
    }

    /**
     * 发送蓝花楹木告示牌更新包
     */
    public static void sendUpdateJacarandaSignPacket(EntityPlayerMP player, TileEntityJacarandaSign sign) {
        String[] textLines = new String[4];
        for (int i = 0; i < 4; i++) {
            textLines[i] = sign.getLineText(i).getFormattedText();
        }

        PacketUpdateJacarandaSign packet = new PacketUpdateJacarandaSign(sign.getPos(), sign.getLineBeingEdited(), textLines);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 发送打开GUI的包
     */
    public static void sendOpenJacarandaSignGuiPacket(EntityPlayerMP player, BlockPos pos) {
        PacketOpenJacarandaSignGui packet = new PacketOpenJacarandaSignGui(pos);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 判断是否为服务端玩家
     */
    public static boolean isServerPlayer(EntityPlayer player) {
        return player instanceof EntityPlayerMP;
    }

    /**
     * 获取玩家所在的世界
     */
    public static World getWorldForPlayer(EntityPlayer player) {
        return player.world;
    }
}