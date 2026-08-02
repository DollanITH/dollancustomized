package com.jhxiaochen.dollancustomized.common.block.rail;

import com.jhxiaochen.dollancustomized.common.block.rail.base.RailwaySignBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EmptySignBlock extends RailwaySignBase {
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            return getBoxXZ(state, 2.0 / 16.0, 10.0 / 16.0 , 10.0 / 16.0);
        }

}
