package com.jhxiaochen.dollancustomized.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public abstract class BasePacket implements IMessage {
    protected BlockPos pos;

    public BasePacket() {}
    public BasePacket(BlockPos pos) { this.pos = pos; }

    protected static BlockPos readBlockPos(ByteBuf buf) {
        return new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    protected static void writeBlockPos(ByteBuf buf, BlockPos pos) {
        buf.writeInt(pos.getX()).writeInt(pos.getY()).writeInt(pos.getZ());
    }

    protected static String readString(ByteBuf buf) {
        int len = buf.readInt();
        return len > 0 ? new String(readBytes(buf, len), java.nio.charset.StandardCharsets.UTF_8) : "";
    }

    protected static void writeString(ByteBuf buf, String str) {
        byte[] bytes = (str == null ? "" : str).getBytes(java.nio.charset.StandardCharsets.UTF_8);
        buf.writeInt(bytes.length).writeBytes(bytes);
    }

    protected static String[] readStringArray(ByteBuf buf, int size) {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) array[i] = readString(buf);
        return array;
    }

    protected static void writeStringArray(ByteBuf buf, String[] array) {
        if (array == null) array = new String[0];
        for (String str : array) writeString(buf, str);
    }

    protected static NBTTagCompound readNBT(ByteBuf buf) {
        try {
            int len = buf.readInt();
            if (len <= 0) return null;
            return CompressedStreamTools.readCompressed(new ByteArrayInputStream(readBytes(buf, len)));
        } catch (Exception e) {
            return new NBTTagCompound();
        }
    }

    protected static void writeNBT(ByteBuf buf, NBTTagCompound nbt) {
        if (nbt == null || nbt.hasNoTags()) {
            buf.writeInt(0);
            return;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            CompressedStreamTools.writeCompressed(nbt, baos);
            byte[] bytes = baos.toByteArray();
            buf.writeInt(bytes.length).writeBytes(bytes);
        } catch (Exception e) {
            buf.writeInt(0);
        }
    }

    private static byte[] readBytes(ByteBuf buf, int len) {
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return bytes;
    }

    public BlockPos getPos() { return pos; }
}