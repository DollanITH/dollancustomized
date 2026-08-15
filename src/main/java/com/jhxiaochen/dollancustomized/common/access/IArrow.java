package com.jhxiaochen.dollancustomized.common.access;

import com.jhxiaochen.dollancustomized.common.block.road.BlockAsphaltSlab;
import com.jhxiaochen.dollancustomized.common.block.road.BlockAsphaltSlab1;
import com.jhxiaochen.dollancustomized.common.block.road.BlockAsphaltSlabHorizontal;
import com.jhxiaochen.dollancustomized.common.block.road.BlockAsphaltSlabHorizontal2;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;

public interface IArrow {
    default boolean SlabChange(IBlockState state) {
        return state.getBlock() instanceof BlockAsphaltSlab || state.getBlock() instanceof BlockSlab || state.getBlock() instanceof BlockAsphaltSlab1 || state.getBlock() instanceof BlockAsphaltSlabHorizontal || state.getBlock() instanceof BlockAsphaltSlabHorizontal2;
    }
}
