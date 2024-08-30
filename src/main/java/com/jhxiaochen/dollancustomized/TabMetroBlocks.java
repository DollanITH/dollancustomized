package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabMetroBlocks extends CreativeTabs {

    public TabMetroBlocks() {
        super("tabMetroBlocks");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.overhead_lines);
    }


}