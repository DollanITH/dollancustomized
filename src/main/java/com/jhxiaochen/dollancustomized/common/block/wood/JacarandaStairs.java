package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class JacarandaStairs extends BlockStairs {

    public JacarandaStairs(Block block) {
        super(block.getDefaultState());
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(DollanSounds.CHERRY_WOOD);
    }


}
