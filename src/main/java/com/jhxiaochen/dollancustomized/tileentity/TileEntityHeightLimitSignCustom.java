package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityHeightLimitSignCustom extends TileEntity {
    private int integerPart = 4;  // 整数部分
    private int decimalPart = 8;  // 小数部分（0-9）

    public TileEntityHeightLimitSignCustom() {
        super();
    }

    // 【核心修复1】解除 64 格距离限制
    @Override
    public double getMaxRenderDistanceSquared() {
        return 102400D;
    }

    // 【核心修复2】不要返回 INFINITE_EXTENT_AABB！
    // 返回一个紧贴方块的 1x1x1 包围盒，引擎会认为它永远在视野内，不会裁剪边缘导致闪烁
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos, pos.add(1, 1, 1));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0; // 确保只在主通道渲染，防止透明度叠加闪烁
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("integerPart", this.integerPart);
        compound.setInteger("decimalPart", this.decimalPart);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        // 兼容性处理：检查是否有旧格式的字符串数据
        if (compound.hasKey("heightValue")) {
            String heightValue = compound.getString("heightValue");
            this.integerPart = 4;  // 默认整数部分
            this.decimalPart = 8;  // 默认小数部分

            // 尝试解析字符串格式
            if (heightValue != null && !heightValue.isEmpty()) {
                try {
                    // 如果是带小数点的格式（如"3.0"）
                    if (heightValue.contains(".")) {
                        String[] parts = heightValue.split("\\.");
                        if (parts.length >= 1) {
                            this.integerPart = Math.max(2, Math.min(6, Integer.parseInt(parts[0])));
                        }
                        if (parts.length >= 2) {
                            this.decimalPart = Math.max(0, Math.min(9, Integer.parseInt(parts[1])));
                        }
                    } else {
                        // 如果只有整数部分（如"3"）
                        this.integerPart = Math.max(2, Math.min(6, Integer.parseInt(heightValue)));
                        this.decimalPart = 0;
                    }
                } catch (NumberFormatException e) {
                    // 解析失败，使用默认值
                    this.integerPart = 4;
                    this.decimalPart = 8;
                }
            }
        } else {
            // 新格式，直接读取
            this.integerPart = Math.max(2, Math.min(6, compound.getInteger("integerPart")));
            this.decimalPart = Math.max(0, Math.min(9, compound.getInteger("decimalPart")));
            // 默认值：4.8
            if (this.integerPart == 2 && this.decimalPart == 0) {
                this.integerPart = 4;
                this.decimalPart = 8;
            }
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    /**
     * 获取格式化后的高度值字符串
     * 如果小数部分为0，则不显示小数点
     */
    public String getFormattedValue() {
        if (decimalPart == 0) {
            return String.valueOf(integerPart);
        }
        return integerPart + "." + decimalPart;
    }

    /**
     * 获取整数部分
     */
    public int getIntegerPart() {
        return integerPart;
    }

    /**
     * 获取小数部分
     */
    public int getDecimalPart() {
        return decimalPart;
    }

    /**
     * 设置整数部分（限制为2-6之间）
     */
    public void setIntegerPart(int integerPart) {
        // 限制整数部分为2-6之间
        this.integerPart = Math.max(2, Math.min(6, integerPart));
    }

    /**
     * 设置小数部分
     */
    public void setDecimalPart(int decimalPart) {
        this.decimalPart = Math.max(0, Math.min(9, decimalPart));
    }

    /**
     * 设置高度值（自动格式化）
     */
    public void setHeightValue(int integerPart, int decimalPart) {
        setIntegerPart(integerPart);
        setDecimalPart(decimalPart);
    }
}
