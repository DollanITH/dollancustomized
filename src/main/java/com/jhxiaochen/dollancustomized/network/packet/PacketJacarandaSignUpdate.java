package com.jhxiaochen.dollancustomized.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketJacarandaSignUpdate extends BasePacket {
    private NBTTagCompound nbtData;

    public PacketJacarandaSignUpdate() {}
    public PacketJacarandaSignUpdate(BlockPos pos, NBTTagCompound nbt) {
        super(pos);
        this.nbtData = nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        nbtData = readNBT(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        writeNBT(buf, nbtData);
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<PacketJacarandaSignUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketJacarandaSignUpdate msg, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                try {
                    World world = Minecraft.getMinecraft().world;
                    if (world == null || msg.pos == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                        com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                            (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) te;

                        if (msg.nbtData != null) sign.readFromNBT(msg.nbtData);
                        sign.markDirty();
                        world.markChunkDirty(msg.pos, sign);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketJacarandaSignUpdate", e);
                }
            });
            return null;
        }
    }
}