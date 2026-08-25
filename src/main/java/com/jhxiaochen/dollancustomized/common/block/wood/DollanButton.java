package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.registry.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DollanButton extends BlockButton {
    boolean cherry_sound;
    public DollanButton(boolean cherry_sound) {
        super(true);
        this.cherry_sound = cherry_sound;
        this.setHardness(0.5F);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos) {
        if (cherry_sound) {
            world.playSound(player, pos, DollanSounds.BLOCK_CHERRY_BUTTON_CLICK, SoundCategory.BLOCKS, 2.0F, 1.0F);
        } else {
            world.playSound(player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }

    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType sound) {
        this.blockSoundType = sound;
        return this;
    }

    @Override
    protected void playReleaseSound(@Nonnull World world, @Nonnull BlockPos pos) {
        if (cherry_sound) {
            world.playSound(null, pos, DollanSounds.BLOCK_CHERRY_BUTTON_RESTORE, SoundCategory.BLOCKS, 2.0F, 1.0F);
        }
        else {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
        }
    }
}
