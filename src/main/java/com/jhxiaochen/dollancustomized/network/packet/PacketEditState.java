package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.network.NetworkManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEditState extends BasePacket {
    private int lineBeingEdited;
    public String[] textLines;

    public PacketEditState() {}
    public PacketEditState(BlockPos pos, int line, String[] lines) {
        super(pos);
        this.lineBeingEdited = line;
        this.textLines = new String[4];
        if (lines != null) System.arraycopy(lines, 0, this.textLines, 0, Math.min(lines.length, 4));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        lineBeingEdited = buf.readInt();
        textLines = readStringArray(buf, 4);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        buf.writeInt(lineBeingEdited);
        writeStringArray(buf, textLines);
    }

    public static class Handler implements IMessageHandler<PacketEditState, IMessage> {
        @Override
        public IMessage onMessage(PacketEditState msg, MessageContext ctx) {
            if (!ctx.side.isServer()) return null;

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                try {
                    World world = ctx.getServerHandler().player.world;
                    if (world == null || msg.pos == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                        com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                            (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) te;

                        // 使用NBT更新数据
                        NBTTagCompound nbt = new NBTTagCompound();

                        // 保存文本行 (使用与TileEntitySign相同的键名)
                        for (int i = 0; i < 4; i++) {
                            String text = msg.textLines != null && msg.textLines[i] != null ?
                                msg.textLines[i] : "";
                            nbt.setString("Text" + (i + 1), text);
                        }

                        // 应用更新
                        sign.readFromNBT(nbt);
                        sign.markDirty();

                        // 通知更新
                        world.notifyBlockUpdate(msg.pos, world.getBlockState(msg.pos),
                            world.getBlockState(msg.pos), 3);

                        // 同步到客户端
                        if (ctx.getServerHandler().player != null) {
                            NetworkManager.updateSignData(ctx.getServerHandler().player, msg.pos,
                                                          NetworkManager.createSignNBT(sign));
                        }
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketEditState", e);
                }
            });
            return null;
        }
    }
}