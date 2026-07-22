package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.network.BasePacket;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 限高路牌网络包（客户端 -> 服务端）
 */
public class PacketHeightLimitSign extends BasePacket {
    public String heightValue;

    public PacketHeightLimitSign() {}

    public PacketHeightLimitSign(BlockPos pos, String heightValue) {
        super(pos);
        this.heightValue = heightValue;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        heightValue = readString(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        writeString(buf, heightValue);
    }

    public static class Handler implements IMessageHandler<PacketHeightLimitSign, IMessage> {
        @Override
        public IMessage onMessage(PacketHeightLimitSign message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                World world = ctx.getServerHandler().player.world;
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof TileEntityHeightLimitSignCustom) {
                    TileEntityHeightLimitSignCustom heightSign = (TileEntityHeightLimitSignCustom) te;

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
                                heightSign.setIntegerPart(Integer.parseInt(message.heightValue));
                                heightSign.setDecimalPart(0);
                            }
                        }
                    } catch (NumberFormatException e) {
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