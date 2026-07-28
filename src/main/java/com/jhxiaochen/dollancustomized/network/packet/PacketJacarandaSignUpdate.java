package com.jhxiaochen.dollancustomized.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
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
    public static class ClientHandler implements IMessageHandler<PacketJacarandaSignUpdate, IMessage> {
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

                        if (msg.nbtData != null) {
                            // 读取槽位和锁定状态
                            int slot = msg.nbtData.getInteger("EditSlot");
                            boolean isLocking = msg.nbtData.getBoolean("IsLocking");

                            // 读取文本数据
                            ITextComponent[] text = new ITextComponent[4];
                            for (int i = 0; i < 4; i++) {
                                String s = msg.nbtData.getString("Text" + (i + 1));
                                if (!s.isEmpty()) {
                                    text[i] = ITextComponent.Serializer.jsonToComponent(s);
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
                        }
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

    @SideOnly(Side.SERVER)
    public static class ServerHandler implements IMessageHandler<PacketJacarandaSignUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketJacarandaSignUpdate msg, MessageContext ctx) {
            net.minecraft.world.WorldServer world = (net.minecraft.world.WorldServer) ctx.getServerHandler().player.getServerWorld();
            world.addScheduledTask(() -> {
                try {
                    if (world == null || msg.pos == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                        com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                            (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) te;

                        if (msg.nbtData != null) {
                            // 读取槽位和锁定状态
                            int slot = msg.nbtData.getInteger("EditSlot");
                            boolean isLocking = msg.nbtData.getBoolean("IsLocking");

                            // 读取文本数据
                            ITextComponent[] text = new ITextComponent[4];
                            for (int i = 0; i < 4; i++) {
                                String s = msg.nbtData.getString("Text" + (i + 1));
                                if (!s.isEmpty()) {
                                    text[i] = ITextComponent.Serializer.jsonToComponent(s);
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
                        }
                        sign.markDirty();
                        world.markChunkDirty(msg.pos, sign);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketJacarandaSignUpdate.ServerHandler", e);
                }
            });
            return null;
        }
    }
}