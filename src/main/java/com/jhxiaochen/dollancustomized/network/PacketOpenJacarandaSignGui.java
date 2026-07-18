package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.client.gui.GuiJacarandaSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenJacarandaSignGui implements IMessage {
    private BlockPos pos;

    public PacketOpenJacarandaSignGui() {
    }

    public PacketOpenJacarandaSignGui(BlockPos pos) {
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

    public static class Handler implements IMessageHandler<PacketOpenJacarandaSignGui, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenJacarandaSignGui message, MessageContext ctx) {
            // 在客户端处理消息，打开GUI
            if (ctx.side.isClient()) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    World world = Minecraft.getMinecraft().world;
                    if (world != null) {
                        net.minecraft.tileentity.TileEntity tileEntity = world.getTileEntity(message.pos);
                        if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                            com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                                (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                            // 打开告示牌编辑GUI
                            Minecraft.getMinecraft().displayGuiScreen(new GuiJacarandaSign(sign, world, message.pos));
                        }
                    }
                });
            }
            return null;
        }
    }
}