package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.network.packet.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * 网络包工厂类
 * 提供创建和验证网络包的方法
 */
public class PacketFactory {

    /**
     * 创建道路文字包
     */
    public static PacketRoadText createRoadTextPacket(BlockPos pos, String text, int colorMode) {
        if (text == null || text.length() > NetworkConfig.MAX_STRING_LENGTH) {
            throw new IllegalArgumentException("Text exceeds maximum length");
        }
        return new PacketRoadText(pos, text, colorMode);
    }

    /**
     * 创建限高路牌包
     */
    public static PacketHeightLimitSign createHeightLimitSignPacket(BlockPos pos, String heightValue) {
        if (heightValue == null || heightValue.length() > NetworkConfig.MAX_STRING_LENGTH) {
            throw new IllegalArgumentException("Height value exceeds maximum length");
        }
        return new PacketHeightLimitSign(pos, heightValue);
    }

    /**
     * 创建告示牌编辑状态包
     */
    public static PacketEditState createEditStatePacket(BlockPos pos, int lineBeingEdited, String[] textLines) {
        if (textLines == null || textLines.length != 4) {
            throw new IllegalArgumentException("Text lines must be array of size 4");
        }
        return new PacketEditState(pos, lineBeingEdited, textLines);
    }

    /**
     * 创建告示牌GUI打开包
     */
    public static PacketJacarandaSignGui createSignGuiPacket(BlockPos pos) {
        return new PacketJacarandaSignGui(pos, null);
    }

    /**
     * 创建告示牌数据更新包
     */
    public static PacketJacarandaSignUpdate createSignUpdatePacket(BlockPos pos, NBTTagCompound nbtData) {
        return new PacketJacarandaSignUpdate(pos, nbtData);
    }

    /**
     * 验证位置是否有效
     */
    public static boolean isValidPosition(BlockPos pos) {
        return pos != null &&
               pos.getX() >= -30000000 && pos.getX() <= 30000000 &&
               pos.getY() >= -30000000 && pos.getY() <= 30000000 &&
               pos.getZ() >= -30000000 && pos.getZ() <= 30000000;
    }

    /**
     * 验证玩家是否有效
     */
    public static boolean isValidPlayer(EntityPlayerMP player) {
        return player != null && !player.hasDisconnected();
    }

    /**
     * 从TileEntity创建NBT数据
     */
    public static NBTTagCompound createNBTFromTileEntity(TileEntity tileEntity) {
        if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
            com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;
            NBTTagCompound nbt = new NBTTagCompound();
            sign.writeToNBT(nbt);
            return nbt;
        }
        return null;
    }
}