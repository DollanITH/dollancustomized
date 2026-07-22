package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;

public class JacarandaStairs extends BlockStairs {

    public JacarandaStairs(Block block) {
        super(block.getDefaultState());
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
    }


}
