package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateJacarandaSign implements IMessage {
    private BlockPos pos;
    private int lineBeingEdited;
    private String[] textLines = new String[4];

    public PacketUpdateJacarandaSign() {
    }

    public PacketUpdateJacarandaSign(BlockPos pos, int lineBeingEdited, String[] textLines) {
        this.pos = pos;
        this.lineBeingEdited = lineBeingEdited;
        this.textLines = textLines;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);

        // 读取位置
        this.pos = packetBuffer.readBlockPos();

        // 读取当前编辑行
        this.lineBeingEdited = packetBuffer.readInt();

        // 读取4行文本
        for (int i = 0; i < 4; i++) {
            int length = packetBuffer.readInt();
            if (length > 0) {
                byte[] textBytes = new byte[length];
                packetBuffer.readBytes(textBytes);
                this.textLines[i] = new String(textBytes);
            } else {
                this.textLines[i] = "";
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);

        // 写入位置
        packetBuffer.writeBlockPos(pos);

        // 写入当前编辑行
        packetBuffer.writeInt(lineBeingEdited);

        // 写入4行文本
        for (int i = 0; i < 4; i++) {
            byte[] textBytes = textLines[i].getBytes();
            packetBuffer.writeInt(textBytes.length);
            if (textBytes.length > 0) {
                packetBuffer.writeBytes(textBytes);
            }
        }
    }

    public static class Handler implements IMessageHandler<PacketUpdateJacarandaSign, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateJacarandaSign message, MessageContext ctx) {
            // 在客户端处理消息
            if (ctx.side.isClient()) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    World world = Minecraft.getMinecraft().world;
                    if (world != null) {
                        TileEntity tileEntity = world.getTileEntity(message.pos);
                        if (tileEntity instanceof TileEntityJacarandaSign) {
                            TileEntityJacarandaSign sign = (TileEntityJacarandaSign) tileEntity;

                            // 更新编辑状态
                            sign.setLineBeingEdited(message.lineBeingEdited);

                            // 更新文本内容
                            for (int i = 0; i < 4; i++) {
                                sign.setLineText(i, net.minecraft.util.text.TextComponentString.isNullOrEmpty(message.textLines[i])
                                    ? new net.minecraft.util.text.TextComponentString("")
                                    : new net.minecraft.util.text.TextComponentString(message.textLines[i]));
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}