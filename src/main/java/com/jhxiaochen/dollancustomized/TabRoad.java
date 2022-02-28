package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabRoad extends CreativeTabs {

    public TabRoad() {
        super("tabRoad");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.asphalt);
    }


}