package com.jhxiaochen.dollancustomized.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 蓝花楹木告示牌网络包合集
 * 整合了所有相关网络包，避免代码重复
 */
public class NetworkMessages {

    /**
     * 打开GUI的网络包
     */
    public static class OpenGuiPacket implements IMessage {
        private BlockPos pos;

        public OpenGuiPacket() {}

        public OpenGuiPacket(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            this.pos = packetBuffer.readBlockPos();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            packetBuffer.writeBlockPos(pos);
        }

        public static class Handler implements IMessageHandler<OpenGuiPacket, IMessage> {
            @Override
            public IMessage onMessage(OpenGuiPacket message, MessageContext ctx) {
                // 在客户端处理消息
                if (ctx.side.isClient()) {
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        World world = Minecraft.getMinecraft().world;
                        if (world != null) {
                            TileEntity tileEntity = world.getTileEntity(message.pos);
                            if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                                com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                                    (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;
                                Minecraft.getMinecraft().displayGuiScreen(
                                    new com.jhxiaochen.dollancustomized.client.gui.GuiJacarandaSign(sign, world, message.pos)
                                );
                            }
                        }
                    });
                }
                return null;
            }
        }
    }

    /**
     * 更新告示牌数据的网络包
     */
    public static class UpdateSignPacket implements IMessage {
        private BlockPos pos;
        private NBTTagCompound nbtData;

        public UpdateSignPacket() {}

        public UpdateSignPacket(BlockPos pos, NBTTagCompound nbtData) {
            this.pos = pos;
            this.nbtData = nbtData;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            this.pos = packetBuffer.readBlockPos();
            this.nbtData = packetBuffer.readCompoundTag();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            packetBuffer.writeBlockPos(pos);
            packetBuffer.writeCompoundTag(nbtData);
        }

        public static class Handler implements IMessageHandler<UpdateSignPacket, IMessage> {
            @Override
            public IMessage onMessage(UpdateSignPacket message, MessageContext ctx) {
                // 在客户端处理消息
                if (ctx.side.isClient()) {
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        World world = Minecraft.getMinecraft().world;
                        if (world != null) {
                            TileEntity tileEntity = world.getTileEntity(message.pos);
                            if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                                com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                                    (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                                // 从NBT读取数据
                                sign.readFromNBT(message.nbtData);
                                sign.markDirty();
                            }
                        }
                    });
                }
                return null;
            }
        }
    }

    /**
     * 发送编辑状态更新的网络包
     */
    public static class EditStatePacket implements IMessage {
        private BlockPos pos;
        private int lineBeingEdited;
        private String[] textLines = new String[4];

        public EditStatePacket() {}

        public EditStatePacket(BlockPos pos, int lineBeingEdited, String[] textLines) {
            this.pos = pos;
            this.lineBeingEdited = lineBeingEdited;
            System.arraycopy(textLines, 0, this.textLines, 0, 4);
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            this.pos = packetBuffer.readBlockPos();
            this.lineBeingEdited = packetBuffer.readInt();

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
            packetBuffer.writeBlockPos(pos);
            packetBuffer.writeInt(lineBeingEdited);

            for (int i = 0; i < 4; i++) {
                byte[] textBytes = textLines[i].getBytes();
                packetBuffer.writeInt(textBytes.length);
                if (textBytes.length > 0) {
                    packetBuffer.writeBytes(textBytes);
                }
            }
        }

        public static class Handler implements IMessageHandler<EditStatePacket, IMessage> {
            @Override
            public IMessage onMessage(EditStatePacket message, MessageContext ctx) {
                // 在客户端处理消息
                if (ctx.side.isClient()) {
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        World world = Minecraft.getMinecraft().world;
                        if (world != null) {
                            TileEntity tileEntity = world.getTileEntity(message.pos);
                            if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                                com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                                    (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                                // 更新编辑状态
                                sign.setLineBeingEdited(message.lineBeingEdited);

                                // 更新文本内容
                                for (int i = 0; i < 4; i++) {
                                    sign.setLineText(i,
                                        message.textLines[i].isEmpty()
                                            ? new net.minecraft.util.text.TextComponentString("")
                                            : new net.minecraft.util.text.TextComponentString(message.textLines[i])
                                    );
                                }
                            }
                        }
                    });
                }
                return null;
            }
        }
    }

    /**
     * 客户端网络包处理器
     */
    public static class ClientHandler {
        public static void processEditState(BlockPos pos, int lineBeingEdited, String[] textLines) {
            World world = Minecraft.getMinecraft().world;
            if (world != null) {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                    com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                        (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                    // 更新编辑状态
                    sign.setLineBeingEdited(lineBeingEdited);

                    // 更新文本内容
                    for (int i = 0; i < 4; i++) {
                        sign.setLineText(i,
                            textLines[i].isEmpty()
                                ? new net.minecraft.util.text.TextComponentString("")
                                : new net.minecraft.util.text.TextComponentString(textLines[i])
                        );
                    }
                }
            }
        }

        public static void openGui(BlockPos pos) {
            World world = Minecraft.getMinecraft().world;
            if (world != null) {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                    com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                        (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                    Minecraft.getMinecraft().displayGuiScreen(
                        new com.jhxiaochen.dollancustomized.client.gui.GuiJacarandaSign(sign, world, pos)
                    );
                }
            }
        }
    }
}