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
        // 使用 UTF-8 解码，支持中文和特殊字符
        heightValue = buf.readBytes(length).toString(java.nio.charset.StandardCharsets.UTF_8);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());

        // 使用 UTF-8 编码，支持中文和特殊字符
        byte[] bytes = heightValue.getBytes(java.nio.charset.StandardCharsets.UTF_8);
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

                    // 解析字符串格式为整数和小数部分
                    try {
                        if (message.heightValue != null && !message.heightValue.isEmpty()) {
                            if (message.heightValue.contains(".")) {
                                String[] parts = message.heightValue.split("\\.");
                                if (parts.length >= 1) {
                                    heightSign.setIntegerPart(Integer.parseInt(parts[0]));
                                }
                                if (parts.length >= 2) {
                                    heightSign.setDecimalPart(Integer.parseInt(parts[1]));
                                } else {
                                    heightSign.setDecimalPart(0);
                                }
                            } else {
                                // 只有整数部分
                                heightSign.setIntegerPart(Integer.parseInt(message.heightValue));
                                heightSign.setDecimalPart(0);
                            }
                        }
                    } catch (NumberFormatException e) {
                        // 解析失败，使用默认值4.8
                        heightSign.setIntegerPart(4);
                        heightSign.setDecimalPart(8);
                    }

                    heightSign.markDirty();
                    world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos),
                        world.getBlockState(message.pos), 3);
                }
            });
            return null;
        }
    }
}
