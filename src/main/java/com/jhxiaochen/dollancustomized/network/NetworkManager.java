package com.jhxiaochen.dollancustomized.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 网络包管理器
 * 统一管理所有网络包的注册和发送
 */
public class NetworkManager {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("dollancustomized_jacaranda");

    /**
     * 初始化网络系统
     */
    public static void init() {
        // 注册网络包
        // 参数：包ID, 发送端, 接收端, 处理器类
        INSTANCE.registerMessage(NetworkMessages.OpenGuiPacket.Handler.class, NetworkMessages.OpenGuiPacket.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(NetworkMessages.UpdateSignPacket.Handler.class, NetworkMessages.UpdateSignPacket.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(NetworkMessages.EditStatePacket.Handler.class, NetworkMessages.EditStatePacket.class, 2, Side.CLIENT);
    }

    /**
     * 打开告示牌GUI
     */
    public static void openSignGui(EntityPlayerMP player, BlockPos pos) {
        NetworkMessages.OpenGuiPacket packet = new NetworkMessages.OpenGuiPacket(pos);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 更新告示牌数据
     */
    public static void updateSignData(EntityPlayerMP player, BlockPos pos, NBTTagCompound nbtData) {
        NetworkMessages.UpdateSignPacket packet = new NetworkMessages.UpdateSignPacket(pos, nbtData);
        INSTANCE.sendTo(packet, player);
    }

    /**
     * 发送编辑状态更新
     */
    public static void sendEditState(EntityPlayerMP player, BlockPos pos, int lineBeingEdited, String[] textLines) {
        NetworkMessages.EditStatePacket packet = new NetworkMessages.EditStatePacket(pos, lineBeingEdited, textLines);
        INSTANCE.sendTo(packet, player);
    }

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