package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketStationNameBoard extends BasePacket {
    public String chineseText;
    public String englishText;
    public int fontSize;
    public int colorRed;
    public int colorGreen;
    public int colorBlue;
    public String fontFamilyChinese;
    public String fontFamilyEnglish;

    public PacketStationNameBoard() {}
    public PacketStationNameBoard(BlockPos pos, String chineseText, String englishText, int fontSize, int red, int green, int blue, String fontFamilyChinese, String fontFamilyEnglish) {
        super(pos);
        this.chineseText = chineseText;
        this.englishText = englishText;
        this.fontSize = fontSize;
        this.colorRed = red;
        this.colorGreen = green;
        this.colorBlue = blue;
        this.fontFamilyChinese = fontFamilyChinese;
        this.fontFamilyEnglish = fontFamilyEnglish;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        chineseText = readString(buf);
        englishText = readString(buf);
        fontSize = buf.readInt();
        colorRed = buf.readInt();
        colorGreen = buf.readInt();
        colorBlue = buf.readInt();
        fontFamilyChinese = readString(buf);
        fontFamilyEnglish = readString(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        writeString(buf, chineseText);
        writeString(buf, englishText);
        buf.writeInt(fontSize);
        buf.writeInt(colorRed);
        buf.writeInt(colorGreen);
        buf.writeInt(colorBlue);
        writeString(buf, fontFamilyChinese);
        writeString(buf, fontFamilyEnglish);
    }

    public static class Handler implements IMessageHandler<PacketStationNameBoard, IMessage> {
        @Override
        public IMessage onMessage(PacketStationNameBoard msg, MessageContext ctx) {
            if (!ctx.side.isServer()) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                try {
                    World world = ctx.getServerHandler().player.world;
                    if (world == null || msg.pos == null || msg.chineseText == null || msg.englishText == null) return;

                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof TileEntityStationNameBoard) {
                        TileEntityStationNameBoard sign =
                            (TileEntityStationNameBoard) te;
                        sign.setChineseText(msg.chineseText);
                        sign.setEnglishText(msg.englishText);
                        sign.fontSize = msg.fontSize;
                        sign.setColor(msg.colorRed, msg.colorGreen, msg.colorBlue);
                        sign.fontFamilyChinese = msg.fontFamilyChinese;
                        sign.markDirty();
                        world.notifyBlockUpdate(msg.pos, world.getBlockState(msg.pos),
                            world.getBlockState(msg.pos), 3);
                    }
                } catch (Exception e) {
                    PacketUtils.error("PacketStationSign", e);
                }
            });
            return null;
        }
    }
}
