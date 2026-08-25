package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.client.gui.GuiEditDollanSign;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
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
    private int slot; // 0 = 正面, 1 = 背面

    public BlockPos getPos() {
        return pos;
    }

    public int getSlot() {
        return slot;
    }

    @SuppressWarnings("unused")
    public PacketJacarandaSignGui() {
    }

    public PacketJacarandaSignGui(BlockPos pos) {
        this(pos, 0);
    }

    public PacketJacarandaSignGui(BlockPos pos, int slot) {
        this.pos = pos;
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        this.pos = packetBuf.readBlockPos();
        this.slot = packetBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        packetBuf.writeBlockPos(pos);
        packetBuf.writeInt(slot);
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

                    if (!(tileEntity instanceof TileEntityDollanSign)) {
                        // 如果找不到或类型不对，创建一个新的（模仿原版逻辑）
                        tileEntity = new TileEntityDollanSign();
                        tileEntity.setWorld(world);
                        tileEntity.setPos(message.pos);
                    }

                    TileEntityDollanSign jacarandaSign = (TileEntityDollanSign) tileEntity;
                    // 设置编辑的槽位
                    jacarandaSign.setEditedSlot(message.getSlot());
                    minecraft.displayGuiScreen(new GuiEditDollanSign(jacarandaSign));
                });
            }

            return null;
        }
    }
}
