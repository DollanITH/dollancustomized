package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.rail.base.RailwaySignBase;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StationStopSign extends RailwaySignBase {
    public static final PropertyEnum<EnumVarient>
            WALL   = PropertyEnum.create("wall", EnumVarient.class);

    public enum EnumVarient implements IStringSerializable {
        NONE(0),
        SINGLE_WALL(2),
        DOUBLE_WALL(1),;

        final                int                                                metadata;
        private static final EnumVarient[] METADATA_LOOKUP = new EnumVarient[values( ).length];

        EnumVarient(int metadataIn) {
            this.metadata = metadataIn;
        }

        public String toString() {
            return this.name().toLowerCase();
        }

        public String getName() {
            return this.toString();
        }

        public String getUnlocalizedName() {
            return this.toString();
        }

        public static EnumVarient byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }


        public int getMetadata() {
            return this.metadata;
        }

        static {
            for (EnumVarient varient : values( )) {
                METADATA_LOOKUP[varient.getMetadata( )] = varient;
            }
        }

    }

    public AxisAlignedBB getBoxX1Z1(IBlockState state, double x, double y, double z, double x1, double y1, double z1)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(1 - x, y, z,1 - x1, y1, z1);
            case SOUTH:
            default:
                return new AxisAlignedBB(z, y,1 - x1, z1, y1,1 - x);
            case NORTH:
                return new AxisAlignedBB(1-z, y, 1-x,1-z1, y1, 1-x1);
            case EAST:
                return new AxisAlignedBB(1 - x1, y, 1 - z, 1 - x, y1, 1 - z1);
        }
    }
    public AxisAlignedBB getBoxX2z2(IBlockState state, double x, double y, double z, double x1, double y1, double z1)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(x, y, 1 - z,x1, y1,1 - z1);
            case NORTH:
            default:
                return new AxisAlignedBB(z, y, x1, z1, y1, x);
            case SOUTH:
                return new AxisAlignedBB(1 -z, y, 1 - x,1 - z1, y1, 1 - x1);
            case EAST:
                return new AxisAlignedBB(1 - x1, y, z, 1 - x, y1, z1);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(WALL) != EnumVarient.NONE ?
                (state.getValue(WALL) == EnumVarient.DOUBLE_WALL ? getBoxX1Z1(state, 3.0 / 16.0, 0, 0.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 13.0 / 16.0)
                        : getBoxX2z2(state,11.0 / 16.0, 0, 3.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 16.0 / 16.0)) :
                getBoxXZ(state, 2.0 / 16.0, 10.0 / 16.0, 3.0 / 16.0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumVarient connected = EnumVarient.NONE;
        if ((meta & 4) !=0) connected = EnumVarient.DOUBLE_WALL;
        if ((meta & 8) !=0) connected = EnumVarient.SINGLE_WALL;
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(WALL, connected);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumVarient connected = state.getValue(WALL);
        int v = 0x0;
        if (connected == EnumVarient.SINGLE_WALL) v = 0x8;
        if (connected == EnumVarient.DOUBLE_WALL) v = 0x4;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        EnumVarient subtype = EnumVarient.byMetadata(meta);
        return state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(WALL, subtype);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this,1,0));
        items.add(new ItemStack(this,1,1));
        items.add(new ItemStack(this,1,2));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(WALL).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, WALL , FACING);
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        super.getPickBlock(state, target, world, pos, player);
        if (state.getValue(WALL) == EnumVarient.DOUBLE_WALL) return new ItemStack(this, 1 , 1);
        if (state.getValue(WALL) == EnumVarient.SINGLE_WALL) return new ItemStack(this, 1 , 2);
        else return new ItemStack(this, 1 , 0);
    }
}
