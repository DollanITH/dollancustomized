package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.rail.base.RailwaySignBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RailwaySignPost extends BlockHorizontal {
    private static final PropertyBool DOWN = PropertyBool.create("down");

    public RailwaySignPost() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setLightOpacity(0);
        this.setRegistryName("railway_sign_post");
        this.setUnlocalizedName("RailwaySignPost");
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {

        return BlockFaceShape.UNDEFINED;

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
        return state.withProperty(DOWN, canFenceConnectTo(worldIn, pos, EnumFacing.DOWN));
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return getBoxXZ(state, 0.125,0.4375);
        /*switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(0.875, 0, 0.4375, 1, 1, 0.5625);
            case SOUTH:
            default:
                return new AxisAlignedBB(0.4375, 0, 0, 0.5625, 1, 0.125);
            case NORTH:
                return new AxisAlignedBB(0.4375, 0, 0.875, 0.5625, 1, 1);
            case EAST:
                return new AxisAlignedBB(0, 0, 0.4375, 0.125, 1, 0.5625);
        }*/
    }

    public AxisAlignedBB getBoxXZ(IBlockState state, double x, double z)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(1 - x, 0, z, 1, 1, 1 - z);
            case SOUTH:
            default:
                return new AxisAlignedBB(z, 0, 0, 1 - z, 1, x);
            case NORTH:
                return new AxisAlignedBB(z, 0, 1 - x, 1 - z, 1, 1);
            case EAST:
                return new AxisAlignedBB(0, 0, z, x, 1, 1 - z);
        }
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
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, DOWN);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing());
        return state;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP && blockAccess.getBlockState(pos.up()).getBlock() instanceof RailwaySignBase ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
