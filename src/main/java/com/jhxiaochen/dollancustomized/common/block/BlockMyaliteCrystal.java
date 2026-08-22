package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockMyaliteCrystal extends BlockBreakable
{
    public BlockMyaliteCrystal()
    {
        super(Material.GLASS, true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setRegistryName("myalite_crystal");
        this.setTranslationKey(Reference.MOD_ID + ".myalite.crystal");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setSoundType(SoundType.GLASS);
        this.setHardness(1.0F);
        this.setResistance(10F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();


            if (blockState != iblockstate)
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }


        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

}
