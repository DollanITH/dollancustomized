package com.jhxiaochen.dollancustomized.common.block.rail;

import com.jhxiaochen.dollancustomized.common.block.rail.base.RailwaySignBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class StationStopSign extends RailwaySignBase {
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        // 牌子后面还带一根 brushed_aluminum 立柱（模型 z: 2 -> 4），碰撞箱取两者并集。
        return getBoxXZRange(state, 3.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 0.0, 4.0 / 16.0);
    }
}
