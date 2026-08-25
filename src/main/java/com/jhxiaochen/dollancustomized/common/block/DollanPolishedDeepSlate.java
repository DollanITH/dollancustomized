package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.registry.DollanSounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;

/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanPolishedDeepSlate extends net.minecraft.block.Block {
        public DollanPolishedDeepSlate(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setTranslationKey(Reference.MOD_ID + "." + name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(2.3F);
        this.setResistance(25F);
        this.setLightLevel(0F);
        this.setSoundType(new SoundType(1.0F, 1.0F, DollanSounds.BLOCK_DEEPSLATE_BREAK, DollanSounds.BLOCK_DEEPSLATE_STEP, DollanSounds.BLOCK_DEEPSLATE_PLACE, SoundEvents.BLOCK_STONE_HIT, DollanSounds.BLOCK_DEEPSLATE_FALL));
        }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}
