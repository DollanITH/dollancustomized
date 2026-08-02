package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.block.metro.OverheadSteelDiagonal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemOverheadSteelDiagonal extends ItemMultiTexture {
    public ItemOverheadSteelDiagonal(Block block) {
        super(block, block, var1 -> OverheadSteelDiagonal.EnumLineHalf.byMetadata(var1.getMetadata()).getName());
    }
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.block.getUnlocalizedName();
    }
}
