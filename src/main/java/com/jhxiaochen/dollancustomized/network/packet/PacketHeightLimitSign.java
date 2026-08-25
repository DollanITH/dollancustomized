package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityHeightLimitSignCustom;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
        public IMessage onMessage(PacketHeightLimitSign msg, MessageContext ctx) {
            if (!ctx.side.isServer()) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                try {
                    World world = ctx.getServerHandler().player.world;
                    if (world == null || msg.pos == null || msg.heightValue == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof TileEntityHeightLimitSignCustom) {
                        TileEntityHeightLimitSignCustom sign =
                            (TileEntityHeightLimitSignCustom) te;

                        try {
                            if (msg.heightValue.contains(".")) {
                                String[] parts = msg.heightValue.split("\\.");
                                sign.setIntegerPart(parts.length > 0 ? Integer.parseInt(parts[0]) : 0);
                                sign.setDecimalPart(parts.length > 1 ? Integer.parseInt(parts[1]) : 0);
                            } else {
                                sign.setIntegerPart(Integer.parseInt(msg.heightValue));
                                sign.setDecimalPart(0);
                            }
                        } catch (NumberFormatException e) {
                            sign.setIntegerPart(4);
                            sign.setDecimalPart(8);
                        }

                        sign.markDirty();
                        world.notifyBlockUpdate(msg.pos, world.getBlockState(msg.pos),
                            world.getBlockState(msg.pos), 3);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketHeightLimitSign", e);
                }
            });
            return null;
        }
    }
}
