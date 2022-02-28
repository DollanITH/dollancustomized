package com.jhxiaochen.dollancustomized.common;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.state.IBlockState;

@SuppressWarnings("Duplicates")
public class BlockMetroBlock extends Block {
    public BlockMetroBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.DOLLAN_METRO_TAB);
        this.setHardness(1.2F);
        this.setResistance(10F);
        this.setLightLevel(0F);
        this.setLightOpacity(255);
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
}
