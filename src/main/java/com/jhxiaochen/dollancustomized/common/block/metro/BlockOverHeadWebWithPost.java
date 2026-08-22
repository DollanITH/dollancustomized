package com.jhxiaochen.dollancustomized.common.block.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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

public class BlockOverHeadWebWithPost extends Block {
    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE          =
            PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, BlockRailBase.EnumRailDirection.NORTH_SOUTH, BlockRailBase.EnumRailDirection.EAST_WEST);


    protected static final AxisAlignedBB X_BOUNDING_BOX = new AxisAlignedBB(0,0,0.25,1,0.75,0.75);
    protected static final AxisAlignedBB X_AABB_0 = new AxisAlignedBB(0, 0.25, 0.25, 1, 0.375, 0.375);
    protected static final AxisAlignedBB X_AABB_1 = new AxisAlignedBB(0, 0.25, 0.625, 1, 0.375, 0.75);
    protected static final AxisAlignedBB X_AABB_2 = new AxisAlignedBB(0, 0.625, 0.25, 1, 0.75, 0.375);
    protected static final AxisAlignedBB X_AABB_3 = new AxisAlignedBB(0, 0.625, 0.625, 1, 0.75, 0.75);
    protected static final AxisAlignedBB X_AABB_4 = new AxisAlignedBB( 0.015625,0.625, 0.375,0.953125,  0.75, 0.625);
    protected static final AxisAlignedBB X_AABB_5 = new AxisAlignedBB(0.015625, 0.25, 0.375, 0.953125, 0.375, 0.625);
    protected static final AxisAlignedBB X_AABB_6 = new AxisAlignedBB( 0.015625,0.375, 0.25, 0.953125, 0.625, 0.375);
    protected static final AxisAlignedBB X_AABB_7 = new AxisAlignedBB( 0.015625,0.375, 0.625, 0.953125, 0.625, 0.75);

    protected static final AxisAlignedBB Z_BOUNDING_BOX = new AxisAlignedBB(0.25,0,0,0.75,0.75,1);
    protected static final AxisAlignedBB Z_AABB_0 = new AxisAlignedBB(0.25, 0.25, 0, 0.375, 0.375,1);
    protected static final AxisAlignedBB Z_AABB_1 = new AxisAlignedBB(0.25, 0.625,0,  0.375,0.75,1);
    protected static final AxisAlignedBB Z_AABB_2 = new AxisAlignedBB(0.625,0.25,  0,0.75,  0.375,1);
    protected static final AxisAlignedBB Z_AABB_3 = new AxisAlignedBB(0.625,0.625, 0, 0.75, 0.75,1);
    protected static final AxisAlignedBB Z_AABB_4 = new AxisAlignedBB(0.625,0.375,0.015625, 0.75, 0.625, 0.953125);
    protected static final AxisAlignedBB Z_AABB_5 = new AxisAlignedBB(0.25, 0.375,0.015625, 0.375, 0.625, 0.953125);
    protected static final AxisAlignedBB Z_AABB_6 = new AxisAlignedBB(0.375,0.25, 0.015625,0.625, 0.375, 0.953125);
    protected static final AxisAlignedBB Z_AABB_7 = new AxisAlignedBB(0.375,0.625,0.015625, 0.625, 0.75 ,0.953125);
    protected static final AxisAlignedBB POST_BOX = new AxisAlignedBB(0.4,0,0.4,0.6,0.25,0.6);

    public BlockOverHeadWebWithPost(String name) {
        super(Material.IRON, MapColor.IRON);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(5.0F);
        this.setResistance(8.0F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
    }


    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {

        return BlockFaceShape.UNDEFINED;

    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
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

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(SHAPE))
        {
            case NORTH_SOUTH:
            default:
                return X_BOUNDING_BOX;
            case EAST_WEST:
                return Z_BOUNDING_BOX;
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_BOX);
        BlockRailBase.EnumRailDirection direction = state.getValue(SHAPE);
        switch(direction) {
            case NORTH_SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_0);
                break;
            case EAST_WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_AABB_0);
                break;
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_BOX);
                break;
        }
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        EnumFacing direction = placer.getHorizontalFacing().getOpposite();
        boolean flag1 = direction == EnumFacing.EAST || direction == EnumFacing.WEST;
        return state.withProperty(SHAPE, flag1 ? BlockRailBase.EnumRailDirection.EAST_WEST : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }


    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE)).getMetadata();
    }

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

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {SHAPE});
    }

}

