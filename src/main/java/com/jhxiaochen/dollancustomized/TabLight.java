package com.jhxiaochen.dollancustomized;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;

public class TabLight extends CreativeTabs {

    public TabLight() {
        super("tabLight");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(DollanBlocks.light);
    }

}
