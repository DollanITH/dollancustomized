package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanPlanks extends net.minecraft.block.Block {
        public DollanPlanks(String name) {
        super(Material.WOOD);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1F);
        this.setResistance(3F);
        this.setLightLevel(0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}

