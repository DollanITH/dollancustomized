package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.network.BasePacket;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 道路文字网络包（客户端 -> 服务端）
 */
public class PacketRoadText extends BasePacket {
    public String text;
    private int colorMode;

    public PacketRoadText() {}

    public PacketRoadText(BlockPos pos, String text, int colorMode) {
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
        public IMessage onMessage(PacketRoadText message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                World world = ctx.getServerHandler().player.world;
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof TileEntityRoadText) {
                    TileEntityRoadText roadText = (TileEntityRoadText) te;
                    roadText.text = message.text;
                    roadText.colorMode = message.colorMode;
                    roadText.markDirty();
                    world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos),
                        world.getBlockState(message.pos), 3);
                }
            });
            return null;
        }
    }
}