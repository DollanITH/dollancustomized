package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.block.road.ArrowNew;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemRoadMarkArrow extends ItemMultiTexture {
    public ItemRoadMarkArrow(Block block) {
        super(block, block, var1 -> ArrowNew.MarkType.byMetadata(var1.getMetadata()).getUnlocalizedName());
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        int i = stack.getMetadata();
        return i > 0 ? this.block.getTranslationKey() + "." + this.nameFunction.apply(stack) : this.block.getTranslationKey();
    }

}
