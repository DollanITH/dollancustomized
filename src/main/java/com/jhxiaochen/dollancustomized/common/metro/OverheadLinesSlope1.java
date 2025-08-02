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

public class OverheadLinesSlope1 extends OverheadLinesHorzional {
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.45D, 0, 0.0D, 0.55D, 1.3125, 1.0D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0, 0.45D, 1.0D, 1.3125, 0.55D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.45D, 0, 0.0D, 0.55D, 1.3125, 1.0D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0, 0.45D, 1.0D, 1.3125, 0.55D);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public OverheadLinesSlope1(String name) {
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setUnlocalizedName(name);
        this.setSoundType(SoundType.CLOTH);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
    }

    @Override
    @Nonnull
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos){
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

/*    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);
        switch(facing) {
            case SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                break;
            case NORTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_4);
                break;
            case WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_4);
                break;
            case EAST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_4);
                break;
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
                break;
        }
    }
}*/

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(0.0D, -0.8125, 0.45D, 1.0D, 1.3125, 0.55D);
            case NORTH:
            default:
                return new AxisAlignedBB(0.45D, -0.8125, 0.0D, 0.55D, 1.3125, 1.0D);
            case SOUTH:
                return new AxisAlignedBB(0.45D, -0.8125, 0.0D, 0.55D, 1.3125, 1.0D);
            case EAST:
                return new AxisAlignedBB(0.0D, -0.8125, 0.45D, 1.0D, 1.3125, 0.55D);
        }
    }
}
