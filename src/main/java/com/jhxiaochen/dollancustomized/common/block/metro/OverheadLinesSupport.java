package com.jhxiaochen.dollancustomized.common.block.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class OverheadLinesSupport extends OverheadLinesHorzional {
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.45D, 0.0D, 0.0D, 0.55D, 1D, 1.0D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.45D, 1.0D, 1D, 0.55D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.45D, 0.0D, 0.0D, 0.55D, 1D, 1.0D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.45D, 1.0D, 1D, 0.55D);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public OverheadLinesSupport(String name) {
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setTranslationKey(name);
        this.setHardness(0.7F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.CLOTH);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        switch (state.getValue(FACING)){
            case WEST:
                return WEST_AABB;
            case NORTH:
            default:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case EAST:
                return EAST_AABB;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        switch (state.getValue(FACING)){
            case WEST:
                return WEST_AABB;
            case NORTH:
            default:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case EAST:
                return EAST_AABB;
        }
    }
}
