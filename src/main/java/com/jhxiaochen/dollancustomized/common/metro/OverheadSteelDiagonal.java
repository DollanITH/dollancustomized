package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class OverheadSteelDiagonal extends OverheadLinesHorzional {
    private static final AxisAlignedBB SOUTH_1 = new AxisAlignedBB(0.77, 0, 0, 0.8, 0.375, 0.58);
    private static final AxisAlignedBB SOUTH_2 = new AxisAlignedBB(0.71, 0, 0, 0.74, 0.375, 0.62);
    private static final AxisAlignedBB SOUTH_3 = new AxisAlignedBB(0.5, 0, 0, 0.5, 0.375, 0);
    private static final AxisAlignedBB SOUTH_4 = new AxisAlignedBB(0.4, 0 ,0, 0.48, 0.375, 0);
    private static final AxisAlignedBB SOUTH_5 = new AxisAlignedBB(0.98,0 , 0.51, 1, 0.375, 0.5);
    private static final AxisAlignedBB SOUTH_6 = new AxisAlignedBB(1, 0, 0.44, 1, 0.375, 0.48);

    private static final AxisAlignedBB NORTH_1 = new AxisAlignedBB(1 - 0.77, 0, 0, 1 - 0.8, 0.375, 1 - 0.58);
    private static final AxisAlignedBB NORTH_2 = new AxisAlignedBB(1 - 0.71, 0, 0, 1 - 0.74, 0.375, 1 - 0.62);
    private static final AxisAlignedBB NORTH_3 = new AxisAlignedBB(1 - 0.5, 0, 0, 1 - 0.5, 0.375, 0);
    private static final AxisAlignedBB NORTH_4 = new AxisAlignedBB(1 - 0.4, 0 ,0, 1 - 0.48,  0.375, 0);
    private static final AxisAlignedBB NORTH_5 = new AxisAlignedBB(1 - 0.98,0 , 0.51, 0, 0.375, 1 - 0.5);
    private static final AxisAlignedBB NORTH_6 = new AxisAlignedBB(0, 0, 1 - 0.44, 0, 0.375, 1 - 0.48);

    private static final AxisAlignedBB EAST_1 = new AxisAlignedBB(0, 0, 0.77, 0.58, 0.375, 0.8);
    private static final AxisAlignedBB EAST_2 = new AxisAlignedBB(0, 0, 0.71, 0.62, 0.375, 0.74);
    private static final AxisAlignedBB EAST_3 = new AxisAlignedBB(0, 0, 0.5, 0, 0.375, 0.5);
    private static final AxisAlignedBB EAST_4 = new AxisAlignedBB(0, 0, 0.4, 0.01, 0.375, 0.48);
    private static final AxisAlignedBB EAST_5 = new AxisAlignedBB(0.51,0 , 0.98, 0.5, 0.375, 1);
    private static final AxisAlignedBB EAST_6 = new AxisAlignedBB(0.44, 0, 1, 0.48, 0.375, 1.0);

    private static final AxisAlignedBB WEST_1 = new AxisAlignedBB(1, 0, 1 - 0.77, 1 - 0.58, 0.375, 1 - 0.8);
    private static final AxisAlignedBB WEST_2 = new AxisAlignedBB(1, 0, 1 - 0.71, 1 - 0.62, 0.375, 1 - 0.74);
    private static final AxisAlignedBB WEST_3 = new AxisAlignedBB(1, 0, 1 - 0.5, 0, 0.375, 1 - 0.5);
    private static final AxisAlignedBB WEST_4 = new AxisAlignedBB(1, 0, 1 - 0.4, 1 - 0.01, 0.375, 1 - 0.48);
    private static final AxisAlignedBB WEST_5 = new AxisAlignedBB(1 - 0.51,0 , 1 - 0.98, 1 - 0.5, 0.375, 0);
    private static final AxisAlignedBB WEST_6 = new AxisAlignedBB(1 - 0.44, 0, 1, 1 - 0.48, 0.375, 0);
    public OverheadSteelDiagonal(String name) {
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setUnlocalizedName(name);
        this.setHardness(0.7F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.CLOTH);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setLightOpacity(1);
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(0.4D, 0.0D, 0.0D, 1.0D, 0.375D, 0.75D);
            case NORTH:
            default:
                return new AxisAlignedBB(0.4D, 0.0D, 0.25D, 1.0D, 0.375D, 1.0D);
            case SOUTH:
                return new AxisAlignedBB(0.45D, 0.0D, 0.0D, 1.0D, 0.375D, 0.75D);
            case EAST:
                return new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.6D, 0.375D, 1.0D);
        }
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);
            switch(facing) {
                case SOUTH:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_6);
                    break;
                case NORTH:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_6);
                    break;
                case WEST:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_6);
                    break;
                case EAST:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_6);
                    break;
                default:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
                    break;
            }
    }
}
