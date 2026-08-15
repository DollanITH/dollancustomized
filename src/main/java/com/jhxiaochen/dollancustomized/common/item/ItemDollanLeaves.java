package com.jhxiaochen.dollancustomized.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemDollanLeaves extends ItemBlock {
    public ItemDollanLeaves(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int damage)
    {
        return damage | 4;
    }

}
