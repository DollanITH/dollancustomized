package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockMetroBlock2 extends Block {
    public BlockMetroBlock2(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.DOLLAN_METRO_TAB);
        this.setHardness(1.5F);
        this.setResistance(15F);
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
