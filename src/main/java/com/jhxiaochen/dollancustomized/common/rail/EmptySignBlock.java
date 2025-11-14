package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.rail.base.RailwaySignBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EmptySignBlock extends RailwaySignBase {
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            return getBoxXZ(state, 2.0 / 16.0, 8.0 / 16.0 , 14.0 / 16.0);
        }

}
