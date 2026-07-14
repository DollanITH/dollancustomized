package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int ROAD_TEXT_GUI_ID = 1; // GUI 的唯一 ID
    public static final int HEIGHT_LIMIT_CUSTOM_GUI_ID = 2; // 自定义限高路牌 GUI ID    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("4. [服务端] GuiHandler 被调用, ID: " + ID);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("5. [客户端] GuiHandler 被调用, ID: " + ID);
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == ROAD_TEXT_GUI_ID && te instanceof TileEntityRoadText) {
            System.out.println("6. [客户端] 返回 GuiRoadText 实例");
            return new GuiRoadText((TileEntityRoadText) te);
        }
        if (ID == HEIGHT_LIMIT_CUSTOM_GUI_ID && te instanceof TileEntityHeightLimitSignCustom) {
            System.out.println("6. [客户端] 返回 GuiHeightLimitSignCustom 实例");
            return new GuiHeightLimitSignCustom((TileEntityHeightLimitSignCustom) te);
        }
        System.out.println("6. [客户端] 返回了 NULL! TE 类型: " + (te != null ? te.getClass().getName() : "NULL"));
        return null;
    }

/*
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    // 【客户端】返回 GUI 界面
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == ROAD_TEXT_GUI_ID && te instanceof TileEntityRoadText) {
            // 将 TileEntity 传给客户端 GUI 用于显示和保存数据
            return new GuiRoadText((TileEntityRoadText) te);
        }
        return null;
    }*/
}
