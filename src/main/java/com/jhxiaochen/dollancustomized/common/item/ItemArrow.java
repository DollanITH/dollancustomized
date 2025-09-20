package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.road.ArrowNew;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemArrow extends ItemMultiTexture {
    public ItemArrow(Block block) {
        super(block, block, var1 -> ArrowNew.MarkType.byMetadata(var1.getMetadata()).getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getMetadata();
        return i > 0 ? this.block.getUnlocalizedName() + "." + this.nameFunction.apply(stack) : this.block.getUnlocalizedName();
    }

}
