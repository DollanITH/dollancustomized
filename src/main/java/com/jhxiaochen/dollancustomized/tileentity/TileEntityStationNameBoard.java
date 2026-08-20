package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityStationNameBoard extends TileEntity {
    public String chineseText = "车站名"; // 中文文本
    public String englishText = "Station Name"; // 英文文本
    public int fontSize = 2; // 字体大小：1=小，2=中，3=大
    public int colorRed = 255; // RGB颜色 - 红色分量
    public int colorGreen = 255; // RGB颜色 - 绿色分量
    public int colorBlue = 255; // RGB颜色 - 蓝色分量
    public String fontFamilyChinese = "default"; // 中文字体

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
        compound.setString("chineseText", this.chineseText);
        compound.setString("englishText", this.englishText);
        compound.setInteger("fontSize", this.fontSize);
        compound.setInteger("colorRed", this.colorRed);
        compound.setInteger("colorGreen", this.colorGreen);
        compound.setInteger("colorBlue", this.colorBlue);
        compound.setString("fontFamilyChinese", this.fontFamilyChinese);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.chineseText = compound.getString("chineseText");
        this.englishText = compound.getString("englishText");
        this.fontSize = compound.getInteger("fontSize");
        this.colorRed = compound.getInteger("colorRed");
        this.colorGreen = compound.getInteger("colorGreen");
        this.colorBlue = compound.getInteger("colorBlue");
        this.fontFamilyChinese = compound.getString("fontFamilyChinese");
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

    public ITextComponent getFormattedText() {
        String combinedText = this.chineseText + "\n" + this.englishText;
        return new TextComponentString(combinedText);
    }

    // 设置RGB颜色
    public void setColor(int red, int green, int blue) {
        this.colorRed = Math.max(0, Math.min(255, red));
        this.colorGreen = Math.max(0, Math.min(255, green));
        this.colorBlue = Math.max(0, Math.min(255, blue));
    }

    // 获取字体大小
    public float getFontSizeMultiplier() {
        switch (this.fontSize) {
            case 1: return 0.8125f; // 小
            case 2: return 0.9325f; // 中
            case 3: return 1.1625f; // 大
            default: return 0.9325f;
        }
    }

    // getter方法
    public String getFontFamilyChinese() {
        return fontFamilyChinese;
    }


    // getter方法
    public String getChineseText() {
        return chineseText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setChineseText(String text) {
        this.chineseText = text;
    }

    public void setEnglishText(String text) {
        this.englishText = text;
    }

}