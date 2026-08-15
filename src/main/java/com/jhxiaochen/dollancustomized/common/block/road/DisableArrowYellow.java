package com.jhxiaochen.dollancustomized.common.block.road;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class DisableArrowYellow extends ArrowNew {
    public DisableArrowYellow(String name) {
        super(name);
    }

    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(-0.625D, 0.0D, -0.625D, 1.5D, 0.15D, 1.625D);
    protected static final AxisAlignedBB AABB_BOTTOM_DOWN = new AxisAlignedBB(-0.625D, -0.5D, -0.625D, 1.5D, 0.15D, 1.625D);

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(TYPE) == MarkType.FULL ? AABB_BOTTOM_HALF : AABB_BOTTOM_DOWN;
    }

}
