package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabRoadSigns extends CreativeTabs {

    public TabRoadSigns() {
        super("tabRoadSigns");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.road_sign_sl_15);
    }

}