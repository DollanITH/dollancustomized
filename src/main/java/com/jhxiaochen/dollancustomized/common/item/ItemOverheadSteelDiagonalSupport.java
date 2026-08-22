package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.block.metro.OverheadSteelDiagonalSupport;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemOverheadSteelDiagonalSupport extends ItemMultiTexture {
    public ItemOverheadSteelDiagonalSupport(Block block) {
        super(block, block, var1 -> OverheadSteelDiagonalSupport.EnumLineHalf.byMetadata(var1.getMetadata()).getName());
    }
    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return this.block.getTranslationKey();
    }
}
