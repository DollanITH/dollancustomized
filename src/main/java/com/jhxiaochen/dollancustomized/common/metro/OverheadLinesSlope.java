package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class OverheadLinesSlope extends OverheadLinesHorzional {
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.45D, 0.0D, 0.0D, 0.55D, 1.0D, 1.0D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.45D, 1.0D, 1.0D, 0.55D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.45D, 0.0D, 0.0D, 0.55D, 1.0D, 1.0D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.45D, 1.0D, 1.0D, 0.55D);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public OverheadLinesSlope(String name) {
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setUnlocalizedName(name);
        this.setHardness(0.7F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.CLOTH);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return WEST_AABB;
    }
}
