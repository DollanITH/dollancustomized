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
 * 蓝花楹木告示牌GUI网络包（服务端 -> 客户端）
 */
public class PacketJacarandaSignGui extends BasePacket {
    private NBTTagCompound nbtData;

    public PacketJacarandaSignGui() {}

    public PacketJacarandaSignGui(BlockPos pos, NBTTagCompound nbtData) {
        super(pos);
        this.nbtData = nbtData;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        // 读取NBT数据
        int length = buf.readInt();
        byte[] nbtBytes = new byte[length];
        buf.readBytes(nbtBytes);
        nbtData = new NBTTagCompound();
        // 注意：这里需要更复杂的NBT反序列化逻辑
        // 暂时使用简单的方式，实际项目中需要完整的NBT读写
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        if (nbtData != null) {
            // 简化处理，实际项目中需要完整的NBT序列化
            byte[] nbtBytes = nbtData.toString().getBytes();
            buf.writeInt(nbtBytes.length);
            buf.writeBytes(nbtBytes);
        } else {
            buf.writeInt(0);
        }
    }

    public static class Handler implements IMessageHandler<PacketJacarandaSignGui, IMessage> {
        @Override
        public IMessage onMessage(PacketJacarandaSignGui message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    World world = Minecraft.getMinecraft().world;
                    if (world != null) {
                        TileEntity tileEntity = world.getTileEntity(message.pos);
                        if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                            com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                                (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;
                            Minecraft.getMinecraft().displayGuiScreen(
                                new com.jhxiaochen.dollancustomized.client.gui.GuiJacarandaSign(sign, world, message.pos)
                            );
                        }
                    }
                });
            }
            return null;
        }
    }
}