package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityRoadText extends TileEntity {
    public String text = "字";
    public int colorMode = 0;

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
        compound.setString("text", this.text);
        compound.setInteger("colorMode", this.colorMode);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.text = compound.getString("text");
        this.colorMode = compound.getInteger("colorMode");
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
        return new TextComponentString(this.text);
    }

}
