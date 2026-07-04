package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityRoadText extends TileEntity {  // ← 【关键】改为继承 TileEntity

    public String text = "";

    // 1. 保存到硬盘 (NBT)
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound); // 让父类写入坐标等信息
        compound.setString("text", this.text); // 写入我们的文字
        return compound;
    }

    // 2. 从硬盘读取 (NBT)
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.text = compound.getString("text");
    }

    // 3. 获取用于客户端同步的数据包 (当方块状态改变时触发)
    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    // 4. 处理客户端接收到的同步数据
    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    // 5. 获取同步数据包
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    // 6. 客户端处理同步数据包
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    // 辅助方法：获取带格式化代码的文字组件
    public ITextComponent getFormattedText() {
        return new TextComponentString(this.text);
    }
}
