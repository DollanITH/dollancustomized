package com.jhxiaochen.dollancustomized.common.block.metro;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class OverheadLinesWye extends OverheadLinesHorzional {
    private static final AxisAlignedBB NORTH_1 = new AxisAlignedBB(0, 0, 0.20270381313759323, 0.6039322304658027, 0.84375, 0.23395381313759323);
    private static final AxisAlignedBB NORTH_2 = new AxisAlignedBB(0, 0, 0.2541554392112171, 0.6418570609361389, 0.84375, 0.2854054392112171);
    private static final AxisAlignedBB NORTH_3 = new AxisAlignedBB(0, 0, 0.20270381313759323, 0.6039322304658027, 0.84375, 0.23395381313759323);
    private static final AxisAlignedBB NORTH_4 = new AxisAlignedBB(0, 0, 0.2541554392112171, 0.6418570609361389, 0.84375, 0.2854054392112171);
    private static final AxisAlignedBB NORTH_5 = new AxisAlignedBB(0, 0, 0.515625, 0.010937500000000044, 0.84375, 0.546875);
    private static final AxisAlignedBB NORTH_6 = new AxisAlignedBB(0, 0, 0.515625, 0.010937500000000044, 0.84375, 0.546875);
    private static final AxisAlignedBB NORTH_7 = new AxisAlignedBB(0.515625, 0, 0, 0.546875, 0.84375, 0.010937500000000044);
    private static final AxisAlignedBB NORTH_8 = new AxisAlignedBB(0.515625, 0, 0, 0.546875, 0.84375, 0.010937500000000044);

    private static final AxisAlignedBB SOUTH_1 = new AxisAlignedBB(0.3960677695341973, 0, 0.7660461868624068, 1, 0.84375, 0.7972961868624068);
    private static final AxisAlignedBB SOUTH_2 = new AxisAlignedBB(0.3581429390638611, 0, 0.7145945607887829, 1, 0.84375, 0.7458445607887829);
    private static final AxisAlignedBB SOUTH_3 = new AxisAlignedBB(0.3960677695341973, 0, 0.7660461868624068, 1, 0.84375, 0.7972961868624068);
    private static final AxisAlignedBB SOUTH_4 = new AxisAlignedBB(0.3581429390638611, 0, 0.7145945607887829, 1, 0.84375, 0.7458445607887829);
    private static final AxisAlignedBB SOUTH_5 = new AxisAlignedBB(0.9890625, 0, 0.453125, 1, 0.84375, 0.484375);
    private static final AxisAlignedBB SOUTH_6 = new AxisAlignedBB(0.9890625, 0, 0.453125, 1, 0.84375, 0.484375);
    private static final AxisAlignedBB SOUTH_7 = new AxisAlignedBB(0.453125, 0, 0.9890625, 0.484375, 0.84375, 1);
    private static final AxisAlignedBB SOUTH_8 = new AxisAlignedBB(0.453125, 0, 0.9890625, 0.484375, 0.84375, 1);

    private static final AxisAlignedBB EAST_1 = new AxisAlignedBB(0, 0, 0.776473125, 0.581263125, 0.84375, 0.807723125);
    private static final AxisAlignedBB EAST_2 = new AxisAlignedBB(0, 0, 0.714463125, 0.629053125, 0.84375, 0.745713125);
    private static final AxisAlignedBB EAST_3 = new AxisAlignedBB(0, 0, 0.776473125, 0.581263125, 0.84375, 0.807723125);
    private static final AxisAlignedBB EAST_4 = new AxisAlignedBB(0, 0, 0.714463125, 0.629053125, 0.84375, 0.745713125);
    private static final AxisAlignedBB EAST_5 = new AxisAlignedBB(0, 0, 0.453125, 0.0109375, 0.84375, 0.484375);
    private static final AxisAlignedBB EAST_6 = new AxisAlignedBB(0, 0, 0.453125, 0.0109375, 0.84375, 0.484375);
    private static final AxisAlignedBB EAST_7 = new AxisAlignedBB(0.515625, 0, 0.9890625, 0.546875, 0.84375, 1);
    private static final AxisAlignedBB EAST_8 = new AxisAlignedBB(0.515625, 0, 0.9890625, 0.546875, 0.84375, 1);

    private static final AxisAlignedBB WEST_1 = new AxisAlignedBB(0.3960677695341973, 0, 0.20270381313759323, 1.0, 0.84375, 0.23395381313759323);
    private static final AxisAlignedBB WEST_2 = new AxisAlignedBB(0.3581429390638611, 0, 0.2541554392112171, 1.0, 0.84375, 0.2854054392112171);
    private static final AxisAlignedBB WEST_3 = new AxisAlignedBB(0.3960677695341973, 0, 0.20270381313759323, 1.0, 0.84375, 0.23395381313759323);
    private static final AxisAlignedBB WEST_4 = new AxisAlignedBB(0.3581429390638611, 0, 0.2541554392112171, 1.0, 0.84375, 0.2854054392112171);
    private static final AxisAlignedBB WEST_5 = new AxisAlignedBB(0.9890625, 0, 0.515625, 1, 0.84375, 0.546875);
    private static final AxisAlignedBB WEST_6 = new AxisAlignedBB(0.9890625, 0, 0.515625, 1, 0.84375, 0.546875);
    private static final AxisAlignedBB WEST_7 = new AxisAlignedBB(0.453125, 0, 0, 0.484375, 0.84375, 0.010937500000000044);
    private static final AxisAlignedBB WEST_8 = new AxisAlignedBB(0.453125, 0, 0, 0.484375, 0.84375, 0.010937500000000044);

    public OverheadLinesWye(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING)){
            case WEST:
            return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.84375D, 1.0D);
            case SOUTH:
            default:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.84375D, 0.5D);
            case NORTH:
                return new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.84375D, 1.0D);
            case EAST:
                return new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.84375D, 1.0D);
        }
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);
        switch(facing) {
            case NORTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_8);
                break;
            case SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_8);

                break;
            case EAST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_8);
                break;
            case WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_8);
                break;
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.84375D, 1.0D));
                break;
        }
    }
}
