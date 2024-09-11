package com.jhxiaochen.dollancustomized.common.metro;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class OverheadSteelSupport1 extends OverheadSteelHorzional {
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.45D, 0.0D, 0.0D, 0.55D, 1.25D, 1.0D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.45D, 1.0D, 1.25D, 0.55D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.45D, 0.0D, 0.0D, 0.55D, 1.25D, 1.0D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.45D, 1.0D, 1.25D, 0.55D);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public OverheadSteelSupport1(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
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
}
