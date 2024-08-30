package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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

public class OverheadLines extends BlockRotatedPillar
{
    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 1.0D);
    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4D, 1.0D, 1.0D, 0.6D);

    public static final PropertyEnum<OverheadLines.EnumAxis> SHAPE = PropertyEnum.<OverheadLines.EnumAxis>create("shape", OverheadLines.EnumAxis.class);

    public OverheadLines(String name) {
        super(Material.CLOTH);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(1.25F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.CLOTH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
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
            case X:
            default:
                return X_AABB;
            case Z:
                return Z_AABB;
        }
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(SHAPE, OverheadLines.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, OverheadLines.EnumAxis.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((OverheadLines.EnumAxis)state.getValue(SHAPE)).getMetadata();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((EnumAxis)state.getValue(SHAPE))
                {
                    case X:
                        return state.withProperty(SHAPE, EnumAxis.Z);
                    case Z:
                        return state.withProperty(SHAPE, EnumAxis.X);
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
    public static enum EnumAxis implements IStringSerializable
    {
        X("x",0),
        Z("z",1);

        private static final OverheadLines.EnumAxis[] METADATA_LOOKUP = new OverheadLines.EnumAxis[values().length];
        private final String name;
        private final int metadata;
        public int getMetadata() {
            return this.metadata;
        }
        public static OverheadLines.EnumAxis byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }
        static {
            for (OverheadLines.EnumAxis enumAxis : values()) {
                METADATA_LOOKUP[enumAxis.getMetadata()] = enumAxis;
            }
        }

        private EnumAxis(String name, int metadata)
        {
            this.name = name;
            this.metadata = metadata;
        }

        public String toString()
        {
            return this.name;
        }

        public static OverheadLines.EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Z:
                    return Z;
                default:
                    return X;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }

}



