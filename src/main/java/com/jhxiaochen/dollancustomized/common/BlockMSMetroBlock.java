package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

@SuppressWarnings("Duplicates")
public class BlockMSMetroBlock extends Block {
    public BlockMSMetroBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.MS_METRO_TAB);
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
