package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.network.packet.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * 统一的网络管理器
 * 负责所有网络包的注册和发送
 */
public class NetworkManager {
    public static final SimpleNetworkWrapper INSTANCE =
        NetworkRegistry.INSTANCE.newSimpleChannel("dollancustomized");

    /**
     * 初始化网络系统
     * 在preInit阶段调用
     */
    public static void init() {
        // 注册所有网络包
        // 从客户端到服务端
        INSTANCE.registerMessage(PacketRoadText.Handler.class, PacketRoadText.class,
            PacketType.ROAD_TEXT.getId(), PacketType.ROAD_TEXT.getTargetSide());
        INSTANCE.registerMessage(PacketHeightLimitSign.Handler.class, PacketHeightLimitSign.class,
            PacketType.HEIGHT_LIMIT_SIGN.getId(), PacketType.HEIGHT_LIMIT_SIGN.getTargetSide());
        INSTANCE.registerMessage(PacketEditState.Handler.class, PacketEditState.class,
            PacketType.JACARANDA_SIGN_TEXT.getId(), PacketType.JACARANDA_SIGN_TEXT.getTargetSide());

        // 从服务端到客户端
        INSTANCE.registerMessage(PacketJacarandaSignGui.Handler.class, PacketJacarandaSignGui.class,
            PacketType.GUI_OPEN.getId(), PacketType.GUI_OPEN.getTargetSide());
        INSTANCE.registerMessage(PacketJacarandaSignUpdate.Handler.class, PacketJacarandaSignUpdate.class,
            PacketType.UPDATE_SIGN_DATA.getId(), PacketType.UPDATE_SIGN_DATA.getTargetSide());
    }

    // ========== 客户端 -> 服务端的方法 ==========

    /**
     * 发送道路文字更新
     */
    public static void sendRoadText(EntityPlayerMP player, BlockPos pos, String text, int colorMode) {
        PacketRoadText packet = new PacketRoadText(pos, text, colorMode);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 发送限高路牌更新
     */
    public static void sendHeightLimitSign(EntityPlayerMP player, BlockPos pos, String heightValue) {
        PacketHeightLimitSign packet = new PacketHeightLimitSign(pos, heightValue);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 发送告示牌文本编辑
     */
    public static void sendEditState(EntityPlayerMP player, BlockPos pos, int lineBeingEdited, String[] textLines) {
        PacketEditState packet = new PacketEditState(pos, lineBeingEdited, textLines);
        INSTANCE.sendTo(packet, player);
    }

    // ========== 服务端 -> 客户端的方法 ==========

    /**
     * 打开告示牌GUI
     */
    public static void openSignGui(EntityPlayerMP player, BlockPos pos) {
        PacketJacarandaSignGui packet = new PacketJacarandaSignGui(pos, null);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 更新告示牌数据
     */
    public static void updateSignData(EntityPlayerMP player, BlockPos pos, NBTTagCompound nbtData) {
        PacketJacarandaSignUpdate packet = new PacketJacarandaSignUpdate(pos, nbtData);
        INSTANCE.sendTo(packet, player);
    }

    // ========== 工具方法 ==========

    /**
     * 从TileEntity创建NBT数据
     */
    public static NBTTagCompound createSignNBT(TileEntity tileEntity) {
        if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
            com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;
            NBTTagCompound nbt = new NBTTagCompound();
            sign.writeToNBT(nbt);
            return nbt;
        }
        return null;
    }

    /**
     * 检查是否为服务端玩家
     */
    public static boolean isServerPlayer(Object player) {
        return player instanceof EntityPlayerMP;
    }
}