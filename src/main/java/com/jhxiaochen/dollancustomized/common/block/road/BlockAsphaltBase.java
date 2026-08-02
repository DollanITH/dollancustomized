package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAsphaltBase extends Block {
    protected BlockAsphaltBase(Material material) {
        super(material);
        this.setCreativeTab(DollanCustomized.ROAD_TAB);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightLevel(0F);
    }
}
