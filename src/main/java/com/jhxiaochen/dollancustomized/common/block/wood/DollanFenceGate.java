package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;

import javax.annotation.Nonnull;

public class DollanFenceGate extends BlockFenceGate {
    public DollanFenceGate() {
        super(BlockPlanks.EnumType.OAK);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType sound) {
        this.blockSoundType = sound;
        return this;
    }

}
