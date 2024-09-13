package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabdollanTran extends CreativeTabs {

    public TabdollanTran() {
        super("tabdollanTran");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.dollan_s1);
    }


}