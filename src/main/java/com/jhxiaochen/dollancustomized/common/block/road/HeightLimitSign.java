package com.jhxiaochen.dollancustomized.common.block.road;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class HeightLimitSign extends BlockRoadSign {
    public HeightLimitSign(String name) {
        super(name);
    }

    public enum EnumHeight implements IStringSerializable {
        HEIGHT_1(0, "subtype_1", "height_1"),
        HEIGHT_2(1, "subtype_2", "height_2");

        final int metadata;
        private final String name;
        private final String unlocalizedName;
        private static final EnumHeight[] METADATA_LOOKUP = new EnumHeight[values().length];

        EnumHeight(int metadataIn, String name, String unlocalizedNameIn) {
            this.metadata = metadataIn;
            this.name = name;
            this.unlocalizedName = unlocalizedNameIn;
        }

        public String toString() { return this.name; }

        public String getName() { return this.name; }

        public String getUnlocalizedName() { return this.unlocalizedName; }

        public static EnumHeight byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }
            return METADATA_LOOKUP[metadata];
        }

        public int getMetadata() { return this.metadata; }

        static {
            for (EnumHeight height : values()) {
                METADATA_LOOKUP[height.getMetadata()] = height;
            }
        }
    }

    public static final PropertyEnum<EnumHeight> TYPE = PropertyEnum.create("type", EnumHeight.class);

    public int damageDropped(IBlockState state) {
        return (state.getValue(TYPE)).getMetadata();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, state.getValue(TYPE).getMetadata());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(TYPE, EnumHeight.byMetadata(meta)).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, WEST, EAST, NORTH, SOUTH, UP, DOWN, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
                .withProperty(TYPE, EnumHeight.byMetadata(meta >> 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | state.getValue(TYPE).getMetadata() << 2;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumHeight height : EnumHeight.values()) {
            items.add(new ItemStack(this, 1, height.getMetadata()));
        }
    }

}
