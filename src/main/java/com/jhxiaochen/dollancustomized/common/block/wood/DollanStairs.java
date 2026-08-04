package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;

public class DollanStairs extends BlockStairs {

    public DollanStairs(Block block) {
        super(block.getDefaultState());
        this.setLightOpacity(255);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(SoundType.WOOD);
    }


}
