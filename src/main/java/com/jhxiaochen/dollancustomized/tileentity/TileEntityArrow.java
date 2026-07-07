package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityArrow extends TileEntity {

    // 强制扩大渲染体积判定
    private static final AxisAlignedBB RENDER_AABB = new AxisAlignedBB(-0.75D, -1.0D, -0.75D, 1.5D, 0.75D, 1.75D);

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        BlockPos pos = this.getPos();
        return RENDER_AABB.offset(pos);
    }


    // 确保该 TE 的数据在客户端和客户端之间同步（如果需要的话）
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

}
