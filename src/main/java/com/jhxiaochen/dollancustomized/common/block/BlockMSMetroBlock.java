package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SuppressWarnings("Duplicates")
public class BlockMSMetroBlock extends Block {
    public BlockMSMetroBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(DollanCustomized.MS_METRO_TAB);
        this.setHardness(1.5F);
        this.setResistance(10F);
    }

}
