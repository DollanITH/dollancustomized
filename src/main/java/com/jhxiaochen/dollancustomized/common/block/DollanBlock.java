package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanBlock extends net.minecraft.block.Block {
        public DollanBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.7F);
        this.setResistance(20F);
        this.setLightLevel(0F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}
