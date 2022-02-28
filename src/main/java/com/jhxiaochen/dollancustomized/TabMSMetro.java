package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabMSMetro extends CreativeTabs {

    public TabMSMetro() {
        super("tabMSMetro");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.mingsha_S1);
    }


}