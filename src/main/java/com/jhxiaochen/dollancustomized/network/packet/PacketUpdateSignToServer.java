package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateSignToServer extends BasePacket {
    private NBTTagCompound nbtData;

    public PacketUpdateSignToServer() {}
    public PacketUpdateSignToServer(BlockPos pos, NBTTagCompound nbt) {
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

    public static class Handler implements IMessageHandler<PacketUpdateSignToServer, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateSignToServer msg, MessageContext ctx) {
            if (!ctx.side.isServer()) return null;

            net.minecraft.world.WorldServer world = (net.minecraft.world.WorldServer) ctx.getServerHandler().player.getServerWorld();
                world.addScheduledTask(() -> {
                try {
                    if (ctx.getServerHandler().player == null || msg.pos == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof TileEntityDollanSign) {
                        TileEntityDollanSign sign =
                            (TileEntityDollanSign) te;

                        if (msg.nbtData != null) {
                            // 读取槽位和锁定状态
                            int slot = msg.nbtData.getInteger("EditSlot");
                            boolean isLocking = msg.nbtData.getBoolean("IsLocking");

                            // 读取文本数据
                            net.minecraft.util.text.ITextComponent[] text = new net.minecraft.util.text.ITextComponent[4];
                            for (int i = 0; i < 4; i++) {
                                String s = msg.nbtData.getString("Text" + (i + 1));
                                if (!s.isEmpty()) {
                                    text[i] = net.minecraft.util.text.ITextComponent.Serializer.jsonToComponent(s);
                                }
                            }

                            // 更新文本
                            if (slot == 0) {
                                System.arraycopy(text, 0, sign.signText, 0, 4);
                            } else if (slot == 1) {
                                System.arraycopy(text, 0, sign.backText, 0, 4);
                            }

                            // 设置锁定状态和编辑槽位
                            sign.isUsing = isLocking;
                            sign.setEditedSlot(slot);

                            // 标记为脏并更新
                            sign.markDirty();
                            sign.upTextSides();

                            // 通知客户端更新
                            world.getPlayerChunkMap().markBlockForUpdate(msg.pos);

                            // 广播更新到所有客户端
                            NetworkManager.sendSignTextUpdateToAll(
                                msg.pos,
                                msg.nbtData.getInteger("EditSlot"),
                                text,
                                msg.nbtData.getBoolean("IsLocking"),
                                world.provider.getDimension(),
                                msg.pos.getX(),
                                msg.pos.getY(),
                                msg.pos.getZ(),
                                64
                            );
                        }
                        sign.markDirty();
                        world.markChunkDirty(msg.pos, sign);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketUpdateSignToServer", e);
                }
            });
            return null;
        }
    }
}
