package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.network.BasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 告示牌数据更新（服务端 -> 客户端）
 */
public class PacketJacarandaSignUpdate extends BasePacket {
    private NBTTagCompound nbtData;

    public PacketJacarandaSignUpdate() {}

    public PacketJacarandaSignUpdate(BlockPos pos, NBTTagCompound nbtData) {
        super(pos);
        this.nbtData = nbtData;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        // 简化的NBT读取，实际项目中需要完整的NBT反序列化
        int length = buf.readInt();
        if (length > 0) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            nbtData = new NBTTagCompound();
            // 这里需要更复杂的NBT解析逻辑
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        if (nbtData != null) {
            byte[] bytes = nbtData.toString().getBytes();
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        } else {
            buf.writeInt(0);
        }
    }

    public static class Handler implements IMessageHandler<PacketJacarandaSignUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketJacarandaSignUpdate message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    World world = Minecraft.getMinecraft().world;
                    if (world != null) {
                        TileEntity tileEntity = world.getTileEntity(message.pos);
                        if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                            com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                                (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                            // 从NBT读取数据
                            if (message.nbtData != null) {
                                sign.readFromNBT(message.nbtData);
                            }
                            sign.markDirty();
                        }
                    }
                });
            }
            return null;
        }
    }
}