package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

public class DollanTrapdoor extends BlockTrapDoor {
    public DollanTrapdoor() {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB).setHardness(3.0F);
    }

    @Override
    public Block setSoundType(@Nonnull SoundType sound) {
        return super.setSoundType(sound);
    }
}
