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

public class StationStopWallSign extends RailwaySignBase {
    public static final PropertyEnum<EnumVarient>
            WALL   = PropertyEnum.create("wall", EnumVarient.class);

    public enum EnumVarient implements IStringSerializable {
        SINGLE_WALL_RIGHT(0),
        DOUBLE_WALL_RIGHT(1),
        SINGLE_WALL_LEFT(2),
        DOUBLE_WALL_LEFT(3),;

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
                return new AxisAlignedBB(z, y,x1, z1, y1,x);
            case NORTH:
                return new AxisAlignedBB(1-z, y, 1-x,1-z1, y1, 1-x1);
            case EAST:
                return new AxisAlignedBB(x, y, 1 - z, x1, y1, 1 - z1);
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
        EnumVarient wall = state.getValue(WALL);
        switch (wall){
            case DOUBLE_WALL_LEFT:
                return getBoxX2z2(state, 3.0 / 16.0, 0, 0.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 13.0 / 16.0);
            case DOUBLE_WALL_RIGHT:
                return getBoxX1Z1(state, 3.0 / 16.0, 0, 0.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 13.0 / 16.0);
            case SINGLE_WALL_LEFT:
                return getBoxX1Z1(state,3.0 / 16.0, 0, 3.0 / 16.0, 5.0 / 16.0, 10.0 / 16.0, 16.0 / 16.0);
            case SINGLE_WALL_RIGHT:
            default:
                return getBoxX2z2(state,11.0 / 16.0, 0, 3.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 16.0 / 16.0);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumVarient connected = EnumVarient.SINGLE_WALL_RIGHT;
        if ((meta & 4) != 0) connected = EnumVarient.DOUBLE_WALL_RIGHT;
        if ((meta & 8) != 0) connected = EnumVarient.SINGLE_WALL_LEFT;
        if ((meta & 12) != 0) connected = EnumVarient.DOUBLE_WALL_LEFT;
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(WALL, connected);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumVarient connected = state.getValue(WALL);
        int v = 0x0;
        if (connected == EnumVarient.DOUBLE_WALL_RIGHT) v = 0x4;
        if (connected == EnumVarient.SINGLE_WALL_LEFT) v = 0x8;
        if (connected == EnumVarient.DOUBLE_WALL_LEFT) v = 0xc;
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
        items.add(new ItemStack(this,1,3));
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
        if (state.getValue(WALL) == EnumVarient.DOUBLE_WALL_RIGHT)
            return new ItemStack(this, 1 , 1);
        else if (state.getValue(WALL) == EnumVarient.SINGLE_WALL_LEFT)
            return new ItemStack(this, 1 , 2);
        else if (state.getValue(WALL) == EnumVarient.DOUBLE_WALL_LEFT)
            return new ItemStack(this, 1 , 3);
        else return new ItemStack(this, 1 , 0);
    }
}
