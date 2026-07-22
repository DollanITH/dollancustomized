package com.jhxiaochen.dollancustomized.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRoadText extends BasePacket {
    public String text;
    public int    colorMode;

    public PacketRoadText() {}
    public PacketRoadText(
            BlockPos pos, String text, int colorMode) {
        super(pos);
        this.text = text;
        this.colorMode = colorMode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        text = readString(buf);
        colorMode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        writeString(buf, text);
        buf.writeInt(colorMode);
    }

    public static class Handler implements IMessageHandler<PacketRoadText, IMessage> {
        @Override
        public IMessage onMessage(PacketRoadText msg, MessageContext ctx) {
            if (!ctx.side.isServer()) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                try {
                    World world = ctx.getServerHandler().player.world;
                    if (world == null || msg.pos == null || msg.text == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText) {
                        com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText sign =
                            (com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText) te;
                        sign.text = msg.text;
                        sign.colorMode = msg.colorMode;
                        sign.markDirty();
                        world.notifyBlockUpdate(msg.pos, world.getBlockState(msg.pos),
                            world.getBlockState(msg.pos), 3);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketRoadText", e);
                }
            });
            return null;
        }
    }
}