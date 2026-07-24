package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.client.gui.GuiEditJacarandaSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketJacarandaSignGui implements IMessage {

    private BlockPos pos;

    public BlockPos getPos() {
        return pos;
    }

    @SuppressWarnings("unused")
    public PacketJacarandaSignGui() {
    }

    public PacketJacarandaSignGui(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        this.pos = packetBuf.readBlockPos();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        packetBuf.writeBlockPos(pos);
    }


    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketJacarandaSignGui, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketJacarandaSignGui message, MessageContext ctx) {
            Minecraft minecraft = Minecraft.getMinecraft();
            World world = minecraft.world;

            if (world != null) {
                minecraft.addScheduledTask(() -> {
                    TileEntity tileEntity = world.getTileEntity(message.pos);

                    if (!(tileEntity instanceof TileEntityJacarandaSign)) {
                        // 如果找不到或类型不对，创建一个新的（模仿原版逻辑）
                        tileEntity = new TileEntityJacarandaSign();
                        tileEntity.setWorld(world);
                        tileEntity.setPos(message.pos);
                    }

                    TileEntityJacarandaSign jacarandaSign = (TileEntityJacarandaSign) tileEntity;
                    minecraft.displayGuiScreen(new GuiEditJacarandaSign(jacarandaSign));
                });
            }

            return null;
        }
    }
}