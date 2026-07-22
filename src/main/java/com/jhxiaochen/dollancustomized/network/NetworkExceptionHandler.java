package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.network.packet.PacketEditState;
import com.jhxiaochen.dollancustomized.network.packet.PacketHeightLimitSign;
import com.jhxiaochen.dollancustomized.network.packet.PacketRoadText;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * 网络异常处理器
 * 统一处理网络包发送和接收过程中的错误
 */
public class NetworkExceptionHandler {

    /**
     * 处理网络包发送失败的情况
     */
    public static void handleSendFailure(EntityPlayerMP player, String packetType, Exception e) {
        if (player != null) {
            TextComponentTranslation errorMessage = new TextComponentTranslation(
                "dollancustomized.network.error.send",
                packetType,
                e.getMessage()
            );
            player.sendMessage(errorMessage);
        }

        if (NetworkConfig.ENABLE_DEBUG_LOG) {
            System.err.println("[Network Error] Failed to send " + packetType + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理网络包接收失败的情况
     */
    public static void handleReceiveFailure(String packetType, Exception e) {
        if (NetworkConfig.ENABLE_DEBUG_LOG) {
            System.err.println("[Network Error] Failed to process " + packetType + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建错误回复消息
     */
    public static IMessage createErrorResponse(String errorType, String details) {
        TextComponentString error = new TextComponentString("Network Error: " + errorType);
        if (details != null) {
            error.appendText(" - ").appendText(details);
        }
        return null; // SimpleImp doesn't support reply messages
    }

    /**
     * 验证网络包数据的有效性
     */
    public static boolean validatePacketData(BasePacket packet) {
        if (packet == null) {
            return false;
        }

        // 检查位置是否有效
        if (!PacketFactory.isValidPosition(packet.pos)) {
            return false;
        }

        // 特定包类型的验证
        if (packet instanceof PacketRoadText) {
            PacketRoadText roadText = (PacketRoadText) packet;
            return roadText.text != null &&
                   roadText.text.length() <= NetworkConfig.MAX_STRING_LENGTH;
        }

        if (packet instanceof PacketHeightLimitSign) {
            PacketHeightLimitSign heightSign = (PacketHeightLimitSign) packet;
            return heightSign.heightValue != null &&
                   heightSign.heightValue.length() <= NetworkConfig.MAX_STRING_LENGTH;
        }

        if (packet instanceof PacketEditState) {
            PacketEditState editState = (PacketEditState) packet;
            return editState.textLines != null &&
                   editState.textLines.length == 4;
        }

        return true;
    }
}