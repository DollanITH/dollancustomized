package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;

/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanCherryPlanks extends net.minecraft.block.Block {
        public DollanCherryPlanks(String name) {
        super(Material.WOOD);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1F);
        this.setResistance(3F);
        this.setLightLevel(0F);
        this.setSoundType(new SoundType(1.5F, 1.0F, DollanSounds.BLOCK_SAKURA_PLANKS_BREAK, DollanSounds.BLOCK_SAKURA_PLANKS_STEP, SoundEvents.BLOCK_WOOD_PLACE, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL));
        }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}

