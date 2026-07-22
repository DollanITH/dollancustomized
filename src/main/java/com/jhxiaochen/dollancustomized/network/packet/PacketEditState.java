package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.network.BasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 告示牌编辑状态更新（客户端 -> 服务端）
 */
public class PacketEditState extends BasePacket {
    private int lineBeingEdited;
    public String[] textLines;

    public PacketEditState() {}

    public PacketEditState(BlockPos pos, int lineBeingEdited, String[] textLines) {
        super(pos);
        this.lineBeingEdited = lineBeingEdited;
        this.textLines = new String[4];
        System.arraycopy(textLines, 0, this.textLines, 0, 4);
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
        public IMessage onMessage(PacketEditState message, MessageContext ctx) {
            if (ctx.side.isServer()) {
                net.minecraftforge.fml.common.FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                    World world = ctx.getServerHandler().player.world;
                    TileEntity te = world.getTileEntity(message.pos);
                    if (te instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                        com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                            (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) te;

                        // 更新编辑状态
                        sign.setLineBeingEdited(message.lineBeingEdited);

                        // 更新文本内容
                        for (int i = 0; i < 4; i++) {
                            sign.setLineText(i,
                                message.textLines[i].isEmpty()
                                    ? new net.minecraft.util.text.TextComponentString("")
                                    : new net.minecraft.util.text.TextComponentString(message.textLines[i])
                            );
                        }

                        sign.markDirty();
                        world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos),
                            world.getBlockState(message.pos), 3);
                    }
                });
            }
            return null;
        }
    }
}