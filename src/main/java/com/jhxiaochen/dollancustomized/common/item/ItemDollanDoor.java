package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanDoor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;

public class ItemDollanDoor extends ItemDoor
{
    public ItemDollanDoor(Block block) {
        super(block);
        DollanDoor blockDollanDoor = (DollanDoor) block;
        blockDollanDoor.setItem(this);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

}
