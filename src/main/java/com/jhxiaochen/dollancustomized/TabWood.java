package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.common.block.wood.DollanLog;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabWood extends CreativeTabs {

    public TabWood() {
        super("tabWood");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanLog.jacaranda_log);
    }

}