package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAsphalt1 extends Block {

    public BlockAsphalt1(String name) {
        super(Material.GOURD);
        this.setRegistryName(name);
        this.setTickRandomly(true);
        this.setUnlocalizedName(name);
        this.setHardness(1.0F);
        this.setResistance(450F);
        this.setCreativeTab(DollanCustomized.ROAD_TAB);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public boolean isFullCube(IBlockState state){
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return true;
    }

}
