package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class MessageRoadText implements IMessage {
    private BlockPos pos;
    private String text;

    public MessageRoadText() {}

    public MessageRoadText(BlockPos pos, String text) {
        this.pos = pos;
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        int length = buf.readInt();
        // 【关键】使用 UTF-8 解码，支持中文
        text = buf.readBytes(length).toString(StandardCharsets.UTF_8);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        // 【关键】使用 UTF-8 编码，支持中文
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public static class Handler implements IMessageHandler<MessageRoadText, IMessage> {
        @Override
        public IMessage onMessage(MessageRoadText message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                World world = ctx.getServerHandler().player.world;
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof TileEntityRoadText) {
                    TileEntityRoadText roadText = (TileEntityRoadText) te;
                    roadText.text = message.text;
                    roadText.markDirty();
                    world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos), world.getBlockState(message.pos), 3);
                }
            });
            return null;
        }
    }
}
