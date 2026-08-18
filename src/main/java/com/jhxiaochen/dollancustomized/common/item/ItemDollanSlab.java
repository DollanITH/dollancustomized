package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.block.wood.DollanSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemDollanSlab extends ItemSlab {
    public ItemDollanSlab(DollanSlab singleSlab, DollanSlab doubleSlab) {
        super(singleSlab, singleSlab, doubleSlab);
        singleSlab.setItem(this);
        doubleSlab.setItem(this);
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        return 150; //返回燃烧时间（单位：tick）
    }
}
