package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;

public class BlockRoadSign extends Block {
    public BlockRoadSign(String name) {
        super(Material.IRON);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.ROAD_Signs);
        this.setHardness(1.4F);
    }

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool WEST = PropertyBool.create("west");

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        return state;
    }
    static LinkedList<AxisAlignedBB> AABBs = new LinkedList<>();
    static AxisAlignedBB getAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        AxisAlignedBB aabb = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        for (AxisAlignedBB i : AABBs) {
            if (i.equals(aabb)) return i;
        }
        AABBs.add(aabb);
        return aabb;
    }

    protected AxisAlignedBB getBoxByXYZ(IBlockState state, double x, double y, double z) {
        double x1 = 0.5 - x / 2, y1 = 0, z1 = 0.5 - z / 2;
        double x2 = 0.5 + x / 2, y2 = y, z2 = 0.5 + z / 2;
        EnumFacing facing = state.getValue(FACING);

        switch (facing) {
            case EAST:
                return getAABB(1.0 - x2, z1, 1.0 - y2, 1.0 - x1, z2, 1.0 - y1);
            case NORTH:
                return getAABB(y1, z1, 1.0 - x2, y2, z2, 1.0 - x1);
            case WEST:
                return getAABB(x1, z1, y1, x2, z2, y2);
            case SOUTH:
                return getAABB(1.0 - y2, z1, x1, 1.0 - y1, z2, x2);
        }
        return Block.FULL_BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getBoxByXYZ(state, 0.35, 0.9, 0.75);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
                                        EnumFacing side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        getActualState(state, worldIn, pos);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        switch (meta) {
            case 0:
                return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
                        .withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
                        .withProperty(NORTH, this.canConnectTo(worldIn, pos.north()))
                        .withProperty(SOUTH, this.canConnectTo(worldIn, pos.south()))
                        .withProperty(EAST, this.canConnectTo(worldIn, pos.east()))
                        .withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
            case 1:
                return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
                        .withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
                        .withProperty(NORTH, this.canConnectTo(worldIn, pos.east()))
                        .withProperty(SOUTH, this.canConnectTo(worldIn, pos.west()))
                        .withProperty(EAST, this.canConnectTo(worldIn, pos.south()))
                        .withProperty(WEST, this.canConnectTo(worldIn, pos.north()));
            case 2:
                return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
                        .withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
                        .withProperty(NORTH, this.canConnectTo(worldIn, pos.south()))
                        .withProperty(SOUTH, this.canConnectTo(worldIn, pos.north()))
                        .withProperty(EAST, this.canConnectTo(worldIn, pos.west()))
                        .withProperty(WEST, this.canConnectTo(worldIn, pos.east()));
            case 3:
                return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
                        .withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
                        .withProperty(NORTH, this.canConnectTo(worldIn, pos.west()))
                        .withProperty(SOUTH, this.canConnectTo(worldIn, pos.east()))
                        .withProperty(EAST, this.canConnectTo(worldIn, pos.north()))
                        .withProperty(WEST, this.canConnectTo(worldIn, pos.south()));
            default:
                return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
                        .withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
                        .withProperty(NORTH, this.canConnectTo(worldIn, pos.north()))
                        .withProperty(SOUTH, this.canConnectTo(worldIn, pos.south()))
                        .withProperty(EAST, this.canConnectTo(worldIn, pos.east()))
                        .withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
        }

    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
            return block instanceof BlockRoadSign || block instanceof BlockRoadSignPost;
        }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
