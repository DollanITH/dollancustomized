package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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

import javax.annotation.Nonnull;
import java.util.List;

public class OverheadSteelDiagonal extends OverheadSteelHorzional {
    private static final AxisAlignedBB NORTH_1 = new AxisAlignedBB(0, 0, 0.20270381313759323, 0.6039322304658027, 0.296875, 0.23395381313759323);
    private static final AxisAlignedBB NORTH_2 = new AxisAlignedBB(0, 0, 0.2541554392112171, 0.6418570609361389, 0.296875, 0.2854054392112171);
    private static final AxisAlignedBB NORTH_3 = new AxisAlignedBB(0, 0, 0.20270381313759323, 0.6039322304658027, 0.296875, 0.23395381313759323);
    private static final AxisAlignedBB NORTH_4 = new AxisAlignedBB(0, 0, 0.2541554392112171, 0.6418570609361389, 0.296875, 0.2854054392112171);
    private static final AxisAlignedBB NORTH_5 = new AxisAlignedBB(0, 0, 0.515625, 0.010937500000000044, 0.296875, 0.546875);
    private static final AxisAlignedBB NORTH_6 = new AxisAlignedBB(0, 0, 0.515625, 0.010937500000000044, 0.296875, 0.546875);
    private static final AxisAlignedBB NORTH_7 = new AxisAlignedBB(0.515625, 0, 0, 0.546875, 0.296875, 0.010937500000000044);
    private static final AxisAlignedBB NORTH_8 = new AxisAlignedBB(0.515625, 0, 0, 0.546875, 0.296875, 0.010937500000000044);

    private static final AxisAlignedBB SOUTH_1 = new AxisAlignedBB(0.3960677695341973, 0, 0.7660461868624068, 1, 0.296875, 0.7972961868624068);
    private static final AxisAlignedBB SOUTH_2 = new AxisAlignedBB(0.3581429390638611, 0, 0.7145945607887829, 1, 0.296875, 0.7458445607887829);
    private static final AxisAlignedBB SOUTH_3 = new AxisAlignedBB(0.3960677695341973, 0, 0.7660461868624068, 1, 0.296875, 0.7972961868624068);
    private static final AxisAlignedBB SOUTH_4 = new AxisAlignedBB(0.3581429390638611, 0, 0.7145945607887829, 1, 0.296875, 0.7458445607887829);
    private static final AxisAlignedBB SOUTH_5 = new AxisAlignedBB(0.9890625, 0, 0.453125, 1, 0.296875, 0.484375);
    private static final AxisAlignedBB SOUTH_6 = new AxisAlignedBB(0.9890625, 0, 0.453125, 1, 0.296875, 0.484375);
    private static final AxisAlignedBB SOUTH_7 = new AxisAlignedBB(0.453125, 0, 0.9890625, 0.484375, 0.296875, 1);
    private static final AxisAlignedBB SOUTH_8 = new AxisAlignedBB(0.453125, 0, 0.9890625, 0.484375, 0.296875, 1);

    private static final AxisAlignedBB EAST_1 = new AxisAlignedBB(0, 0, 0.776473125, 0.581263125, 0.296875, 0.807723125);
    private static final AxisAlignedBB EAST_2 = new AxisAlignedBB(0, 0, 0.714463125, 0.629053125, 0.296875, 0.745713125);
    private static final AxisAlignedBB EAST_3 = new AxisAlignedBB(0, 0, 0.776473125, 0.581263125, 0.296875, 0.807723125);
    private static final AxisAlignedBB EAST_4 = new AxisAlignedBB(0, 0, 0.714463125, 0.629053125, 0.296875, 0.745713125);
    private static final AxisAlignedBB EAST_5 = new AxisAlignedBB(0, 0, 0.453125, 0.0109375, 0.296875, 0.484375);
    private static final AxisAlignedBB EAST_6 = new AxisAlignedBB(0, 0, 0.453125, 0.0109375, 0.296875, 0.484375);
    private static final AxisAlignedBB EAST_7 = new AxisAlignedBB(0.515625, 0, 0.9890625, 0.546875, 0.296875, 1);
    private static final AxisAlignedBB EAST_8 = new AxisAlignedBB(0.515625, 0, 0.9890625, 0.546875, 0.296875, 1);

    private static final AxisAlignedBB WEST_1 = new AxisAlignedBB(0.3960677695341973, 0, 0.20270381313759323, 1.0, 0.296875, 0.23395381313759323);
    private static final AxisAlignedBB WEST_2 = new AxisAlignedBB(0.3581429390638611, 0, 0.2541554392112171, 1.0, 0.296875, 0.2854054392112171);
    private static final AxisAlignedBB WEST_3 = new AxisAlignedBB(0.3960677695341973, 0, 0.20270381313759323, 1.0, 0.296875, 0.23395381313759323);
    private static final AxisAlignedBB WEST_4 = new AxisAlignedBB(0.3581429390638611, 0, 0.2541554392112171, 1.0, 0.296875, 0.2854054392112171);
    private static final AxisAlignedBB WEST_5 = new AxisAlignedBB(0.9890625, 0, 0.515625, 1, 0.296875, 0.546875);
    private static final AxisAlignedBB WEST_6 = new AxisAlignedBB(0.9890625, 0, 0.515625, 1, 0.296875, 0.546875);
    private static final AxisAlignedBB WEST_7 = new AxisAlignedBB(0.453125, 0, 0, 0.484375, 0.296875, 0.010937500000000044);
    private static final AxisAlignedBB WEST_8 = new AxisAlignedBB(0.453125, 0, 0, 0.484375, 0.296875, 0.010937500000000044);
    public OverheadSteelDiagonal(String name) {
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setUnlocalizedName(name);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setLightOpacity(0);
    }

    public static final PropertyEnum<EnumLineHalf>
            TYPE = PropertyEnum.create("type", EnumLineHalf.class);

    public enum EnumLineHalf implements IStringSerializable
    {
        SINGLE,
        DOUBLE,
        DOUBLE_1;
        private static final OverheadSteelDiagonal.EnumLineHalf[] METADATA_LOOKUP = new OverheadSteelDiagonal.EnumLineHalf[values().length];

        public String toString()
        {
            return this.getName();
        }

        public int getMetadata()
        {
            switch (this){
                case SINGLE: return 0;
                case DOUBLE: return 1;
                default: return 2;
            }
        }

        public String getName()
        {
            return this.name().toLowerCase();
        }

        public static OverheadSteelDiagonal.EnumLineHalf byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        static {
            for (OverheadSteelDiagonal.EnumLineHalf subtype : values()) {
                METADATA_LOOKUP[subtype.getMetadata()] = subtype;
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(0.45D, 0.0D, 0.0D, 1.0D, 0.296875D, 0.55D);
            case SOUTH:
            default:
                return new AxisAlignedBB(0.55D, 0.0D, 0.45D, 1.0d, 0.296875D, 1.0D);
            case NORTH:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.45D, 0.296875D, 0.55D);
            case EAST:
                return new AxisAlignedBB(0.0D, 0.0D, 0.45D, 0.45D, 0.296875D, 1.0D);
        }
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);
            switch(facing) {
                case SOUTH:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_6);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_7);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_8);
                    break;
                case NORTH:
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
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_6);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_7);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_8);
                    break;
                default:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
                    break;
            }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumLineHalf type = EnumLineHalf.SINGLE;
        if ((meta & 4) != 0) type = EnumLineHalf.DOUBLE;
        if ((meta & 8) != 0) type = EnumLineHalf.DOUBLE_1;
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(TYPE, type);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumLineHalf type = state.getValue(TYPE);
        int v = 0x0;
        if (type == EnumLineHalf.DOUBLE) v = 0x4;
        if (type == EnumLineHalf.DOUBLE_1) v = 0x8;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

        @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumLineHalf subtype = EnumLineHalf.byMetadata(meta);
     return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, placer.getHorizontalFacing()).withProperty(TYPE, subtype);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        super.getPickBlock(state, target, world, pos, player);
        return new ItemStack(this,1,state.getValue(TYPE).getMetadata());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumLineHalf subtype : EnumLineHalf.values( )) {
            items.add(new ItemStack(this, 1, subtype.getMetadata( )));
        }

    }
}
