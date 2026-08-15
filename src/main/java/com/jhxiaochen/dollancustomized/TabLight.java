package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.blocks.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabLight extends CreativeTabs {

    public TabLight() {
        super("tabLight");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(DollanBlocks.light);
    }

}
