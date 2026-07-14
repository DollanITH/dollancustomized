package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class MessageHeightLimitSignCustom implements IMessage {
    private BlockPos pos;
    private String heightValue;

    public MessageHeightLimitSignCustom() {}

    public MessageHeightLimitSignCustom(BlockPos pos, String heightValue) {
        this.pos = pos;
        this.heightValue = heightValue;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());

        int length = buf.readInt();
        // 【关键】使用 UTF-8 解码，支持中文和特殊字符
        heightValue = buf.readBytes(length).toString(StandardCharsets.UTF_8);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());

        // 【关键】使用 UTF-8 编码，支持中文和特殊字符
        byte[] bytes = heightValue.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public static class Handler implements IMessageHandler<MessageHeightLimitSignCustom, IMessage> {
        @Override
        public IMessage onMessage(MessageHeightLimitSignCustom message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                World world = ctx.getServerHandler().player.world;
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof TileEntityHeightLimitSignCustom) {
                    TileEntityHeightLimitSignCustom heightSign = (TileEntityHeightLimitSignCustom) te;
                    heightSign.heightValue = message.heightValue;

                    heightSign.markDirty();
                    world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos), world.getBlockState(message.pos), 3);
                }
            });
            return null;
        }
    }
}