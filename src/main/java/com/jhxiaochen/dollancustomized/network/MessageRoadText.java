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
    private int colorMode; // 【新增】颜色模式：0=白色，1=黄色

    public MessageRoadText() {}

    // 【修改】构造函数增加 colorMode 参数
    public MessageRoadText(BlockPos pos, String text, int colorMode) {
        this.pos = pos;
        this.text = text;
        this.colorMode = colorMode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());

        int length = buf.readInt();
        // 【关键】使用 UTF-8 解码，支持中文
        text = buf.readBytes(length).toString(StandardCharsets.UTF_8);

        // 【新增】读取颜色模式
        colorMode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());

        // 【关键】使用 UTF-8 编码，支持中文
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        // 【新增】写入颜色模式
        buf.writeInt(colorMode);
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
                    // 【新增】将客户端传来的颜色模式应用到 TileEntity
                    roadText.colorMode = message.colorMode;

                    roadText.markDirty();
                    world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos), world.getBlockState(message.pos), 3);
                }
            });
            return null;
        }
    }
}
