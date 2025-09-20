package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.road.BusLaneMark;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemBusLaneMark extends ItemMultiTexture {
    public ItemBusLaneMark() {
        super(DollanBlocks.bus_lane_mark, DollanBlocks.bus_lane_mark, var1 -> BusLaneMark.EnumType.byMetadata(var1.getMetadata()).getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.block.getUnlocalizedName();
    }
}
