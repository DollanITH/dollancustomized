package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabBlocks extends CreativeTabs {

    public TabBlocks() {
        super("tabDollanBlocks");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanItems.dollancraft);
    }


}