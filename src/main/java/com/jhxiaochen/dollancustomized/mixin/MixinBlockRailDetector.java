package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailDetector;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockRailDetector.class)
public abstract class MixinBlockRailDetector extends Block {
    public MixinBlockRailDetector(Material materialIn) {
        super(materialIn);
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        EnumFacing direction = placer.getHorizontalFacing().getOpposite();
        boolean flag1 = direction == EnumFacing.EAST || direction == EnumFacing.WEST;
        return state.withProperty(BlockRailDetector.SHAPE, flag1 ? BlockRailBase.EnumRailDirection.EAST_WEST : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }
}
