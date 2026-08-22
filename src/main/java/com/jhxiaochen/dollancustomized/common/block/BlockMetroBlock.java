package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

@SuppressWarnings("Duplicates")
public class BlockMetroBlock extends Block {
    public BlockMetroBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(DollanCustomized.DOLLAN_METRO_TAB);
        this.setHardness(1.5F);
        this.setResistance(10F);
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }
}
