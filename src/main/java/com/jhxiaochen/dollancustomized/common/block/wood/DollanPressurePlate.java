package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.registry.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DollanPressurePlate extends BlockPressurePlate {
    boolean cherry_sound;
    public DollanPressurePlate(boolean cherry_sound) {
        super(Material.WOOD, BlockPressurePlate.Sensitivity.EVERYTHING);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(0.5F);
        this.cherry_sound = cherry_sound;
    }

    @Override
    protected void playClickOnSound(World world, BlockPos pos) {
        if (cherry_sound) {
            world.playSound(null, pos, DollanSounds.BLOCK_CHERRY_BUTTON_CLICK, SoundCategory.BLOCKS, 2.0F, 1.0F);
        } else {
            super.playClickOnSound(world, pos);
        }
    }

    @Override
    protected void playClickOffSound(World world, BlockPos pos) {
        if (cherry_sound) {
            world.playSound(null, pos, DollanSounds.BLOCK_CHERRY_BUTTON_RESTORE, SoundCategory.BLOCKS, 2.0F, 1.0F);
        } else {
            super.playClickOffSound(world, pos);
        }
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

}
