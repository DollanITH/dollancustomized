package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.network.packet.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkManager {

    // 检查玩家是否为服务器玩家的方法
    public static boolean isServerPlayer(net.minecraft.entity.player.EntityPlayer player) {
        return player != null && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER;
    }
    public static final SimpleNetworkWrapper INSTANCE =
        NetworkRegistry.INSTANCE.newSimpleChannel("dollancustomized");

    public static void init() {
        PacketType.validateIds();

        // 注册包
        INSTANCE.registerMessage(PacketRoadText.Handler.class, PacketRoadText.class,
            PacketType.ROAD_TEXT.getId(), PacketType.ROAD_TEXT.getTargetSide());
        INSTANCE.registerMessage(PacketHeightLimitSign.Handler.class, PacketHeightLimitSign.class,
            PacketType.HEIGHT_LIMIT_SIGN.getId(), PacketType.HEIGHT_LIMIT_SIGN.getTargetSide());
        INSTANCE.registerMessage(PacketEditState.Handler.class, PacketEditState.class,
            PacketType.JACARANDA_SIGN_TEXT.getId(), PacketType.JACARANDA_SIGN_TEXT.getTargetSide());
                INSTANCE.registerMessage(PacketJacarandaSignUpdate.Handler.class, PacketJacarandaSignUpdate.class,
            PacketType.UPDATE_SIGN_DATA.getId(), PacketType.UPDATE_SIGN_DATA.getTargetSide());
    }

    // 发送方法
    public static void sendRoadText(EntityPlayerMP player, BlockPos pos, String text, int color) {
        INSTANCE.sendTo(new PacketRoadText(pos, text, color), player);
    }

    public static void sendHeightLimitSign(EntityPlayerMP player, BlockPos pos, String height) {
        INSTANCE.sendTo(new PacketHeightLimitSign(pos, height), player);
    }

    public static void sendEditState(EntityPlayerMP player, BlockPos pos, int line, String[] lines) {
        INSTANCE.sendTo(new PacketEditState(pos, line, lines), player);
    }

    
    public static void updateSignData(EntityPlayerMP player, BlockPos pos, NBTTagCompound nbt) {
        INSTANCE.sendTo(new PacketJacarandaSignUpdate(pos, nbt), player);
    }

    
    // NBT工具
    public static NBTTagCompound createSignNBT(TileEntity te) {
        if (te instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
            com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) te;
            NBTTagCompound nbt = new NBTTagCompound();
            sign.writeToNBT(nbt);
            return nbt;
        }
        return null;
    }
}