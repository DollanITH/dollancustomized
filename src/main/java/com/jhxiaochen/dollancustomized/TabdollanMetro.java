package com.jhxiaochen.dollancustomized;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;

public class TabdollanMetro extends CreativeTabs {

    public TabdollanMetro() {
        super("tabdollanMetro");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.dollan_line_1);
    }


}