package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.init.blocks.DollanBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BusLaneMarkSlab extends IronHorizontal {
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(-0.5D, -0.499D, -0.5D, 1.5D, 0.15D, 1.5D);
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    public enum EnumType implements IStringSerializable {
        GONG(0), JIAO(1), ZHUAN(2), YONG(3);
        final int metadata;
        private static final EnumType[] METADATA_LOOKUP = new EnumType[values().length];
        EnumType(int metadataIn) { this.metadata = metadataIn; }
        public String toString() { return this.name().toLowerCase(); }
        public String getName() { return this.toString(); }
        public String getUnlocalizedName() { return this.toString(); }
        public static EnumType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) { metadata = 0; }
            return METADATA_LOOKUP[metadata];
        }
        public int getMetadata() { return this.metadata; }
        static { for (EnumType varient : values()) { METADATA_LOOKUP[varient.getMetadata()] = varient; } }
    }

    public BusLaneMarkSlab() {
        super("bus_lane_mark_slab");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_BOTTOM_HALF;
    }

    @Override @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumType varient = EnumType.GONG;
        if (meta >= 4 && meta <= 7) varient = EnumType.ZHUAN;
        else if (meta >= 8 && meta <= 11) varient = EnumType.JIAO;
        else if (meta >= 12 && meta <= 15) varient = EnumType.YONG;
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(TYPE, varient);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumType varient = state.getValue(TYPE);
        int v = 0x0;
        if (varient == EnumType.JIAO) v = 0x8;
        else if (varient == EnumType.ZHUAN) v = 0x4;
        else if (varient == EnumType.YONG) v = 0xc;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    public boolean SlabChange(IBlockState state){
        return state.getBlock() instanceof BlockAsphaltSlab || (state.getBlock() instanceof BlockSlab && !state.getBlock().isTopSolid(state)) || state.getBlock() instanceof BlockAsphaltSlab1 || state.getBlock() instanceof BlockAsphaltSlabHorizontal || state.getBlock() instanceof BlockAsphaltSlabHorizontal2;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        if (!SlabChange(downState) && downState.isTopSolid() && DollanBlocks.bus_lane_mark != null) {
            worldIn.setBlockState(pos, DollanBlocks.bus_lane_mark.getDefaultState()
                    .withProperty(FACING, state.getValue(FACING))
                    .withProperty(TYPE, state.getValue(TYPE)));
        } else if (!SlabChange(downState) && !downState.isTopSolid()) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    public int damageDropped(IBlockState state) {
        return ((state.getValue(TYPE)).getMetadata());
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        super.getPickBlock(state, target, world, pos, player);
        return new ItemStack(DollanBlocks.bus_lane_mark, 1 , (state.getValue(TYPE).getMetadata()));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        EnumType varient = EnumType.byMetadata(meta);
        state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, varient);
        return state;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(DollanBlocks.bus_lane_mark);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(DollanBlocks.bus_lane_mark));
    }

}
