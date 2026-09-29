package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityRoadText;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int ROAD_TEXT_GUI_ID = 1; // GUI 的唯一 ID
    public static final int HEIGHT_LIMIT_CUSTOM_GUI_ID = 2; // 自定义限高路牌 GUI ID
    public static final int JACARANDA_SIGN_EDIT_GUI_ID = 3; // 蓝花楹木告示牌编辑 GUI ID
    public static final int STATION_NAME_BOARD_GUI_ID = 4; // 车站站名牌编辑 GUI ID

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == ROAD_TEXT_GUI_ID && te instanceof TileEntityRoadText) {
            return new GuiRoadText((TileEntityRoadText) te);
        }
        if (ID == HEIGHT_LIMIT_CUSTOM_GUI_ID && te instanceof TileEntityHeightLimitSignCustom) {
            return new GuiHeightLimitSignCustom((TileEntityHeightLimitSignCustom) te);
        }
        if (ID == JACARANDA_SIGN_EDIT_GUI_ID && te instanceof TileEntityDollanSign) {
            // 从TileEntity获取当前编辑的槽位
            TileEntityDollanSign sign = (TileEntityDollanSign) te;
            return new GuiEditDollanSign(sign);
        }
        if (ID == STATION_NAME_BOARD_GUI_ID && te instanceof TileEntityStationNameBoard) {
            return new GuiStationNameBoard((TileEntityStationNameBoard) te);
        }
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
