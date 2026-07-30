package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.network.packet.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
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
        INSTANCE.registerMessage(PacketJacarandaSignGui.Handler.class, PacketJacarandaSignGui.class,
            PacketType.OPEN_JACARANDA_SIGN_GUI.getId(), PacketType.OPEN_JACARANDA_SIGN_GUI.getTargetSide());
        INSTANCE.registerMessage(PacketUpdateSignToServer.Handler.class, PacketUpdateSignToServer.class,
            PacketType.UPDATE_SIGN_TO_SERVER.getId(), PacketType.UPDATE_SIGN_TO_SERVER.getTargetSide());
        INSTANCE.registerMessage(PacketItemUpdate.Handler.class, PacketItemUpdate.class,
            PacketType.ITEM_UPDATE.getId(), PacketType.ITEM_UPDATE.getTargetSide());
            }

    // 发送方法
    public static void sendRoadText(EntityPlayerMP player, BlockPos pos, String text, int color) {
        INSTANCE.sendTo(new PacketRoadText(pos, text, color), player);
    }

    public static void sendHeightLimitSign(EntityPlayerMP player, BlockPos pos, String height) {
        INSTANCE.sendTo(new PacketHeightLimitSign(pos, height), player);
    }

    public static void sendEditState(EntityPlayerMP player, BlockPos pos, int line, String[] lines) {
        INSTANCE.sendTo(new PacketEditState(pos, line, 0, lines), player);
    }

    public static void sendEditState(EntityPlayerMP player, BlockPos pos, int line, int slot, String[] lines) {
        INSTANCE.sendTo(new PacketEditState(pos, line, slot, lines), player);
    }

    public static void sendSignTextUpdate(EntityPlayerMP player, BlockPos pos, int slot, ITextComponent[] text, boolean isLocking) {
        // 创建NBT数据
        NBTTagCompound nbt = new NBTTagCompound();
        for (int i = 0; i < 4; i++) {
            if (text != null && i < text.length) {
                nbt.setString("Text" + (i + 1), ITextComponent.Serializer.componentToJson(text[i]));
            }
        }
        nbt.setInteger("EditSlot", slot);
        nbt.setBoolean("IsLocking", isLocking);

        INSTANCE.sendTo(new PacketUpdateSignToServer(pos, nbt), player);
    }

    
    public static void updateSignData(EntityPlayerMP player, BlockPos pos, NBTTagCompound nbt) {
        INSTANCE.sendTo(new PacketUpdateSignToServer(pos, nbt), player);
    }

    public static void updateSignData(BlockPos pos, NBTTagCompound nbt) {
        // Send to server without specific player (server will determine who needs it)
        // This is called from client, will be received on server
        PacketUpdateSignToServer packet = new PacketUpdateSignToServer(pos, nbt);
        INSTANCE.sendToServer(packet);
    }

    public static void sendUpdateSignToServer(BlockPos pos, NBTTagCompound nbt) {
        // 使用新的包类型发送更新到服务器
        PacketUpdateSignToServer packet = new PacketUpdateSignToServer(pos, nbt);
        INSTANCE.sendToServer(packet);
    }

    public static void openJacarandaSignGui(EntityPlayerMP player, BlockPos pos) {
        INSTANCE.sendTo(new PacketJacarandaSignGui(pos), player);
    }

    public static void openJacarandaSignGui(EntityPlayerMP player, BlockPos pos, int slot) {
        INSTANCE.sendTo(new PacketJacarandaSignGui(pos, slot), player);
    }

    // 发送文本更新到所有玩家
    public static void sendSignTextUpdateToAll(BlockPos pos, int slot, ITextComponent[] text, boolean isLocking, int dimension, double x, double y, double z, double range) {
        // 创建NBT数据
        NBTTagCompound nbt = new NBTTagCompound();
        for (int i = 0; i < 4; i++) {
            if (text != null && i < text.length) {
                nbt.setString("Text" + (i + 1), ITextComponent.Serializer.componentToJson(text[i]));
            }
        }
        nbt.setInteger("EditSlot", slot);
        nbt.setBoolean("IsLocking", isLocking);

        // 使用优化后的包发送更新到所有玩家
        INSTANCE.sendToAllAround(new PacketUpdateSignToServer(pos, nbt),
            new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    // 发送物品更新到所有玩家
    public static void sendSignItemUpdateToAll(BlockPos pos, int slot, ItemStack stack, int dimension, double x, double y, double z, double range) {
        INSTANCE.sendToAllAround(new PacketItemUpdate(pos, slot, stack),
            new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
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