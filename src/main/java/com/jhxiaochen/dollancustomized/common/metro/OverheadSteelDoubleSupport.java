package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class OverheadSteelDoubleSupport extends Block {
    private static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public OverheadSteelDoubleSupport() {
        super(Material.ROCK);
        this.setRegistryName("overhead_steel_double_support");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(UP, Boolean.FALSE));
        this.setUnlocalizedName("overhead_steel_double_support");
        this.setHardness(2.25F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setLightOpacity(0);
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        Block block = iblockstate.getBlock();
        return block instanceof BlockSlab && blockfaceshape != BlockFaceShape.SOLID;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(UP, canFenceConnectTo(worldIn, pos, EnumFacing.UP));
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return canConnectTo(world, pos.offset(facing), facing.getOpposite());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);
        switch (facing) {
            case NORTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_0);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_9);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_10);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_11);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_12);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_13);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_14);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_15);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_16);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_17);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_18);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_19);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_20);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_21);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_22);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_23);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_24);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_25);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.NORTH_AABB_26);
                break;
            case SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_0);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_9);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_10);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_11);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_12);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_13);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_14);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_15);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_16);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_17);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_18);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_19);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_20);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_21);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_22);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_23);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_24);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_25);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.SOUTH_AABB_26);
                break;
            case EAST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_0);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_9);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_10);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_11);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_12);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_13);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_14);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_15);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_16);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_17);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_18);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_19);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_20);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_21);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_22);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_23);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_24);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_25);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.EAST_AABB_26);
                break;
            case WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_0);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_9);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_10);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_11);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_12);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_13);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_14);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_15);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_16);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_17);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_18);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_19);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_20);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_21);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_22);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_23);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_24);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_25);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, OverheadLineTran0_BoundingBox.WEST_AABB_26);
                break;
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
                break;
        }
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, UP});
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing());
        return state;
    }
}
