package com.jhxiaochen.dollancustomized.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.nio.charset.StandardCharsets;

/**
 * 基础网络包类
 * 提供通用的读写方法，减少重复代码
 */
public abstract class BasePacket implements IMessage {
    protected BlockPos pos;

    public BasePacket() {}

    public BasePacket(BlockPos pos) {
        this.pos = pos;
    }

    /**
     * 从字节缓冲区读取BlockPos
     */
    protected BlockPos readBlockPos(ByteBuf buf) {
        return BlockPos.fromLong(buf.readLong());
    }

    /**
     * 将BlockPos写入字节缓冲区
     */
    protected void writeBlockPos(ByteBuf buf, BlockPos pos) {
        buf.writeLong(pos.toLong());
    }

    /**
     * 从字节缓冲区读取字符串（UTF-8编码）
     */
    protected String readString(ByteBuf buf) {
        int length = buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 将字符串写入字节缓冲区（UTF-8编码）
     */
    protected void writeString(ByteBuf buf, String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        if (bytes.length > 0) {
            buf.writeBytes(bytes);
        }
    }

    /**
     * 从字节缓冲区读取字符串数组
     */
    protected String[] readStringArray(ByteBuf buf, int size) {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = readString(buf);
        }
        return array;
    }

    /**
     * 将字符串数组写入字节缓冲区
     */
    protected void writeStringArray(ByteBuf buf, String[] array) {
        for (String text : array) {
            writeString(buf, text);
        }
    }
}