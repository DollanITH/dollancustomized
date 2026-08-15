package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.common.access.IArrow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
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

import java.util.Random;

public class ArrowNew extends IronHorizontal implements IArrow {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<MarkType> TYPE = PropertyEnum.create("type", MarkType.class);

    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(-0.5D, 0.001D, -0.5D, 1.5D, 0.05D, 1.5D);
    protected static final AxisAlignedBB AABB_BOTTOM_DOWN = new AxisAlignedBB(-0.5D, -0.499D, -0.5D, 1.2D, 0.1D, 1.5D);

    public ArrowNew(String name) {
        super(name);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!worldIn.isRemote){
            IBlockState downState = worldIn.getBlockState(pos.down());
            if (state.getValue(TYPE) == MarkType.FULL && SlabChange(downState)) {
                worldIn.setBlockState(pos, state.withProperty(TYPE, MarkType.SLAB));
            } else if (state.getValue(TYPE) == MarkType.SLAB && !SlabChange(downState) && downState.isTopSolid()) {
                worldIn.setBlockState(pos, state.withProperty(TYPE, MarkType.FULL));
            }
        }
    }

    public enum MarkType implements IStringSerializable {
        FULL(0), SLAB(1);
        final int metadata;
        private static final MarkType[] METADATA_LOOKUP = new MarkType[values( ).length];
        MarkType(int metadataIn) { this.metadata = metadataIn; }
        public String toString() { return this.name().toLowerCase(); }
        public String getName() { return this.toString(); }
        public String getUnlocalizedName() { return this.toString(); }
        public static MarkType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) { metadata = 0; }
            return METADATA_LOOKUP[metadata];
        }
        public int getMetadata() { return this.metadata; }
        static { for (MarkType varient : values( )) { METADATA_LOOKUP[varient.getMetadata( )] = varient; } }
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(TYPE) == MarkType.FULL ? AABB_BOTTOM_HALF : AABB_BOTTOM_DOWN;
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
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(TYPE, (meta & 4) != 0 ? MarkType.SLAB : MarkType.FULL);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        MarkType type = state.getValue(TYPE);
        int v = 0x0;
        if (type == MarkType.SLAB) v = 0x4;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(TYPE, MarkType.FULL);
        if (SlabChange(world.getBlockState(pos.down()))) {
            state = state.withProperty(TYPE, MarkType.SLAB);
        }
        return state;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP;
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.checkForDrop(worldIn, pos, state);
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        return SlabChange(downState) || downState.isTopSolid() || downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);
        IBlockState downState = worldIn.getBlockState(pos.down());
        return SlabChange(downState) || downState.isTopSolid() || downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        if (state.getValue(TYPE) == MarkType.FULL && SlabChange(downState)) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, MarkType.SLAB));
        } else if (state.getValue(TYPE) == MarkType.SLAB && !SlabChange(downState) && downState.isTopSolid() && downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, MarkType.FULL));
        } else if (!SlabChange(downState) && !downState.isTopSolid() && downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) != BlockFaceShape.SOLID) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    public boolean SlabChange(IBlockState state){
        return state.getBlock() instanceof BlockAsphaltSlab || (state.getBlock() instanceof BlockSlab && !state.getBlock().isTopSolid(state)) || state.getBlock() instanceof BlockAsphaltSlab1 || state.getBlock() instanceof BlockAsphaltSlabHorizontal || state.getBlock() instanceof BlockAsphaltSlabHorizontal2;
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(FACING))) {
            return true;
        } else {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
            return false;
        }
    }

}
