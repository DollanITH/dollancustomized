package com.jhxiaochen.dollancustomized.common;


import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockTranLight extends BlockColored
    {
    public BlockTranLight(String name)
        {
            super(Material.ROCK);
            this.setUnlocalizedName(name);
            this.setCreativeTab(DollanCustomized.LIGHT_TAB);
            this.setSoundType(SoundType.GLASS);
            this.setLightLevel(1.0F);
            this.setResistance(15F);
            this.setLightOpacity(0);
            this.isAmbientOcclusion();
        }
        public boolean isAmbientOcclusion()
        {
            return false;
        }

        public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
        {

            return BlockFaceShape.UNDEFINED;

        }

        @SideOnly(Side.CLIENT)
        @Override
        public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }
        @Override
        public boolean isFullCube(IBlockState state) {
            return true;
        }
    }
