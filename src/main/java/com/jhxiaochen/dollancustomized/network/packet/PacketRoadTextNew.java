package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.network.BasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * 道路文字网络包（客户端 -> 服务端）
 */
public class PacketRoadTextNew extends BasePacket implements IMessage {
    private String text;
    private int colorMode;

    public PacketRoadTextNew() {}

    public PacketRoadTextNew(net.minecraft.util.math.BlockPos pos, String text, int colorMode) {
        super(pos);
        this.text = text;
        this.colorMode = colorMode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        pos = packetBuffer.readBlockPos();
        int length = packetBuffer.readInt();
        byte[] bytes = new byte[length];
        packetBuffer.readBytes(bytes);
        text = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        colorMode = packetBuffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeBlockPos(pos);
        byte[] bytes = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        packetBuffer.writeInt(bytes.length);
        packetBuffer.writeBytes(bytes);
        packetBuffer.writeInt(colorMode);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketRoadTextNew, IMessage> {
        @Override
        public IMessage onMessage(PacketRoadTextNew message, net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx) {
            return null;
        }
    }
}