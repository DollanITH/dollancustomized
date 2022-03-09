package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

@SuppressWarnings("Duplicates")
public class BlockMetroBlock extends Block {
    public BlockMetroBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.DOLLAN_METRO_TAB);
        this.setHardness(3F);
        this.setResistance(20F);
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
}
