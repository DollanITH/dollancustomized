package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.tileentity.TileEntityDollanHangingSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketItemUpdate extends BasePacket {
    private int slot;
    private ItemStack stack;

    public PacketItemUpdate() {}
    public PacketItemUpdate(BlockPos pos, int slot, ItemStack stack) {
        super(pos);
        this.slot = slot;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        slot = buf.readInt();
        NBTTagCompound nbt = readNBT(buf);
        stack = new ItemStack(nbt);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        buf.writeInt(slot);
        writeNBT(buf, stack.serializeNBT());
    }

    public static class Handler implements IMessageHandler<PacketItemUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketItemUpdate msg, MessageContext ctx) {
            if (!ctx.side.isServer()) return null;

            net.minecraft.world.WorldServer world = (net.minecraft.world.WorldServer) ctx.getServerHandler().player.getServerWorld();
            world.addScheduledTask(() -> {
                try {
                    if (msg.pos == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof TileEntityDollanHangingSign) {
                        TileEntityDollanHangingSign sign =
                            (TileEntityDollanHangingSign) te;

                        // 修改物品
                        sign.modifyDisplayedItem(world, msg.pos, msg.slot, msg.stack);

                        // 通知客户端更新
                        world.getPlayerChunkMap().markBlockForUpdate(msg.pos);
                        world.markChunkDirty(msg.pos, sign);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketItemUpdate", e);
                }
            });
            return null;
        }
    }
}
