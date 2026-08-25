package com.jhxiaochen.dollancustomized.common.block.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class OverheadSteelSupport extends Block
{
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final AxisAlignedBB WEST_1 = new AxisAlignedBB(0.40625, 0.29687499999999994, 0.40625, 0.59375, 0.328125, 0.59375);
    private static final AxisAlignedBB WEST_2 = new AxisAlignedBB(0.390625, 0.3046875, 0.390625, 0.609375, 0.3203125, 0.609375);
    private static final AxisAlignedBB WEST_3 = new AxisAlignedBB(0.375, 0.3281249999999999, 0.15625, 0.625, 0.5140624999999999, 0.84375);
    private static final AxisAlignedBB WEST_4 = new AxisAlignedBB(0.375, 0.3171874999999999, 0.2421875, 0.625, 0.5140625, 0.2890625);
    private static final AxisAlignedBB WEST_5 = new AxisAlignedBB(0.375, 0.3171874999999999, 0.7109375, 0.625, 0.5140625, 0.7578125);
    private static final AxisAlignedBB WEST_6 = new AxisAlignedBB(0.375, 0.5140624999999999, 0.078125, 0.625, 0.5593749999999998, 0.921875);
    private static final AxisAlignedBB WEST_7 = new AxisAlignedBB(0.375, 0.890625, 0.375, 0.625, 1, 0.625);
    private static final AxisAlignedBB WEST_8 = new AxisAlignedBB(0.375, 0.5484374999999998, 0.4140625000000002, 0.625, 1, 0.6328125000000003);
    private static final AxisAlignedBB WEST_9 = new AxisAlignedBB(0.375, 0.5484374999999998, 0.36718749999999967, 0.625, 1, 0.5859374999999998);
    private static final AxisAlignedBB WEST_10 = new AxisAlignedBB(0.375, 0.5593749999999998, 0.34375, 0.625, 0.7453124999999999, 0.65625);

    private static final AxisAlignedBB SOUTH_1 = new AxisAlignedBB(0.40625, 0.29687499999999994, 0.40625, 0.59375, 0.328125, 0.59375);
    private static final AxisAlignedBB SOUTH_2 = new AxisAlignedBB(0.390625, 0.3046875, 0.390625, 0.609375, 0.3203125, 0.609375);
    private static final AxisAlignedBB SOUTH_3 = new AxisAlignedBB(0.15625, 0.3281249999999999, 0.375, 0.84375, 0.5140624999999999, 0.625);
    private static final AxisAlignedBB SOUTH_4 = new AxisAlignedBB(0.7109375, 0.3171874999999999, 0.375, 0.7578125, 0.5140625, 0.625);
    private static final AxisAlignedBB SOUTH_5 = new AxisAlignedBB(0.2421875, 0.3171874999999999, 0.375, 0.2890625, 0.5140625, 0.625);
    private static final AxisAlignedBB SOUTH_6 = new AxisAlignedBB(0.078125, 0.5140624999999999, 0.375, 0.921875, 0.5593749999999998, 0.625);
    private static final AxisAlignedBB SOUTH_7 = new AxisAlignedBB(0.375, 0.890625, 0.375, 0.625, 1, 0.625);
    private static final AxisAlignedBB SOUTH_8 = new AxisAlignedBB(0.49671041578186503, 0.4574056696454627, 0.375, 0.7154604157818651, 1, 0.625);
    private static final AxisAlignedBB SOUTH_9 = new AxisAlignedBB(0.28453958421813486, 0.4574056696454627, 0.375, 0.503289584218135, 1, 0.625);
    private static final AxisAlignedBB SOUTH_10 = new AxisAlignedBB(0.34375, 0.5593749999999998, 0.375, 0.65625, 0.7453124999999999, 0.625);

    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 0.296875D, 1.0D);
    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 0.296875D, 0.625D);

    protected static final AxisAlignedBB X_BOUNDING_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
    protected static final AxisAlignedBB Z_BOUNDING_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE =
            PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, BlockRailBase.EnumRailDirection.NORTH_SOUTH, BlockRailBase.EnumRailDirection.EAST_WEST);
    public OverheadSteelSupport(String name) {
        super(Material.IRON);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty(UP, Boolean.FALSE));
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
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
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(SHAPE))
        {
            case NORTH_SOUTH:
            default:
                return X_BOUNDING_AABB;
            case EAST_WEST:
                return Z_BOUNDING_AABB;
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        BlockRailBase.EnumRailDirection direction = state.getValue(SHAPE);

        switch(direction) {
            case NORTH_SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_9);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_10);
                break;
            case EAST_WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_8);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_9);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_10);
                break;
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
                break;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        EnumFacing direction = placer.getHorizontalFacing().getOpposite();
        boolean flag1 = direction == EnumFacing.EAST || direction == EnumFacing.WEST;
        return state.withProperty(SHAPE, flag1 ? BlockRailBase.EnumRailDirection.EAST_WEST : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }


    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE)).getMetadata();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE))
                {
                    case EAST_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                    case NORTH_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {SHAPE, UP});
    }

}
