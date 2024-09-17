package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockRotatedPillar;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockOverHeadWebRotated extends BlockRotatedPillar {
    public static final PropertyEnum<BlockOverHeadWebRotated.EnumAxis> AXIS = PropertyEnum.create("axis", BlockOverHeadWebRotated.EnumAxis.class);

    protected static final AxisAlignedBB Y_BOUNDING_BOX = new AxisAlignedBB(0.25,0,0.25,0.75,1,0.75);
    protected static final AxisAlignedBB Y_AABB_0 = new AxisAlignedBB(0.25, 0, 0.25, 0.375, 1, 0.375);
    protected static final AxisAlignedBB Y_AABB_1 = new AxisAlignedBB(0.25, 0, 0.625, 0.375, 1, 0.75);
    protected static final AxisAlignedBB Y_AABB_2 = new AxisAlignedBB(0.625, 0, 0.25, 0.75, 1, 0.375);
    protected static final AxisAlignedBB Y_AABB_3 = new AxisAlignedBB(0.625, 0, 0.625, 0.75, 1, 0.75);
    protected static final AxisAlignedBB Y_AABB_4 = new AxisAlignedBB(0.625, 0.015625, 0.375, 0.75, 0.953125, 0.625);
    protected static final AxisAlignedBB Y_AABB_5 = new AxisAlignedBB(0.25, 0.015625, 0.375, 0.375, 0.953125, 0.625);
    protected static final AxisAlignedBB Y_AABB_6 = new AxisAlignedBB(0.375, 0.015625, 0.25, 0.625, 0.953125, 0.375);
    protected static final AxisAlignedBB Y_AABB_7 = new AxisAlignedBB(0.375, 0.015625, 0.625, 0.625, 0.953125, 0.75);
    
    protected static final AxisAlignedBB X_BOUNDING_BOX = new AxisAlignedBB(0,0.25,0.25,1,0.75,0.75);
    protected static final AxisAlignedBB X_AABB_0 = new AxisAlignedBB(0, 0.25, 0.25, 1, 0.375, 0.375);
    protected static final AxisAlignedBB X_AABB_1 = new AxisAlignedBB(0, 0.25, 0.625, 1, 0.375, 0.75);
    protected static final AxisAlignedBB X_AABB_2 = new AxisAlignedBB(0, 0.625, 0.25, 1, 0.75, 0.375);
    protected static final AxisAlignedBB X_AABB_3 = new AxisAlignedBB(0, 0.625, 0.625, 1, 0.75, 0.75);
    protected static final AxisAlignedBB X_AABB_4 = new AxisAlignedBB( 0.015625,0.625, 0.375,0.953125,  0.75, 0.625);
    protected static final AxisAlignedBB X_AABB_5 = new AxisAlignedBB(0.015625, 0.25, 0.375, 0.953125, 0.375, 0.625);
    protected static final AxisAlignedBB X_AABB_6 = new AxisAlignedBB( 0.015625,0.375, 0.25, 0.953125, 0.625, 0.375);
    protected static final AxisAlignedBB X_AABB_7 = new AxisAlignedBB( 0.015625,0.375, 0.625, 0.953125, 0.625, 0.75);

    protected static final AxisAlignedBB Z_BOUNDING_BOX = new AxisAlignedBB(0.25,0.25,0,0.75,0.75,1);
    protected static final AxisAlignedBB Z_AABB_0 = new AxisAlignedBB(0.25, 0.25, 0, 0.375, 0.375,1);
    protected static final AxisAlignedBB Z_AABB_1 = new AxisAlignedBB(0.25, 0.625,0,  0.375,0.75,1);
    protected static final AxisAlignedBB Z_AABB_2 = new AxisAlignedBB(0.625,0.25,  0,0.75,  0.375,1);
    protected static final AxisAlignedBB Z_AABB_3 = new AxisAlignedBB(0.625,0.625, 0, 0.75, 0.75,1);
    protected static final AxisAlignedBB Z_AABB_4 = new AxisAlignedBB(0.625,0.375,0.015625, 0.75, 0.625, 0.953125);
    protected static final AxisAlignedBB Z_AABB_5 = new AxisAlignedBB(0.25, 0.375,0.015625, 0.375, 0.625, 0.953125);
    protected static final AxisAlignedBB Z_AABB_6 = new AxisAlignedBB(0.375,0.25, 0.015625,0.625, 0.375, 0.953125);
    protected static final AxisAlignedBB Z_AABB_7 = new AxisAlignedBB(0.375,0.625,0.015625, 0.625, 0.75 ,0.953125);

    public BlockOverHeadWebRotated(String name) {
        super(Material.IRON, MapColor.IRON);
        this.setUnlocalizedName(name);
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

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(AXIS)){
            case X:
                return X_BOUNDING_BOX;
            case Y:
            default:
                return Y_BOUNDING_BOX;
            case Z:
                return Z_BOUNDING_BOX;
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumAxis axis = state.getValue(AXIS);
        switch(axis) {
            case Y:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Y_AABB_0);
                break;
            case X:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_5);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_6);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_7);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_AABB_0);
                break;
            case Z:
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
                addCollisionBoxToList(pos, entityBox, collidingBoxes, FULL_BLOCK_AABB);
                break;
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, BlockOverHeadWebRotated.EnumAxis.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(AXIS)).getMetadata();
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(AXIS, BlockOverHeadWebRotated.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{AXIS});
    }


    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((BlockOverHeadWebRotated.EnumAxis)state.getValue(AXIS))
                {
                    case X:
                        return state.withProperty(AXIS, BlockOverHeadWebRotated.EnumAxis.Z);
                    case Z:
                        return state.withProperty(AXIS, BlockOverHeadWebRotated.EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    public enum EnumAxis implements IStringSerializable
    {
        X("x",0),
        Y("y",1),
        Z("z",2);

        private static final BlockOverHeadWebRotated.EnumAxis[] METADATA_LOOKUP = new BlockOverHeadWebRotated.EnumAxis[values().length];
        private final String name;
        private final int metadata;

        EnumAxis(String name, int metadata)
        {
            this.name = name;
            this.metadata = metadata;
        }

        public String toString()
        {
            return this.name;
        }

        public int getMetadata() {
            return this.metadata;
        }

        public static BlockOverHeadWebRotated.EnumAxis byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        static {
            for (BlockOverHeadWebRotated.EnumAxis type : values()) {
                METADATA_LOOKUP[type.getMetadata()] = type;
            }
        }
        public static BlockOverHeadWebRotated.EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Z:
                    return Z;
                case Y:
                default:
                    return Y;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }
}

