package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.road.BlockAsphaltSlab;
import com.jhxiaochen.dollancustomized.common.road.BlockAsphaltSlab1;
import com.jhxiaochen.dollancustomized.common.road.BlockAsphaltSlabHorizontal;
import com.jhxiaochen.dollancustomized.common.road.BlockAsphaltSlabHorizontal2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class OverheadPost extends Block {
    private static final PropertyBool  UP    = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");
    private static final AxisAlignedBB BOX_1 = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 1, 0.625);
    private static final AxisAlignedBB BOX_2 = new AxisAlignedBB(0.625, 0, 0.40625, 0.6875, 1, 0.59375);
    private static final AxisAlignedBB BOX_3 = new AxisAlignedBB(0.3125, 0, 0.40625, 0.375, 1, 0.59375);
    private static final AxisAlignedBB BOX_4 = new AxisAlignedBB(0.40625, 0, 0.625, 0.59375, 1, 0.6875);
    private static final AxisAlignedBB BOX_5 = new AxisAlignedBB(0.40625, 0, 0.3125, 0.59375, 1, 0.375);
    public OverheadPost(String name) {
        super(Material.IRON, MapColor.IRON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(4.0F);
        this.setResistance(6.0F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
    }

    public boolean canConnectToUP(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        Block block = iblockstate.getBlock();
        return block instanceof BlockSlab &&
                blockfaceshape != BlockFaceShape.SOLID && blockfaceshape != BlockFaceShape.MIDDLE_POLE
                && blockfaceshape != BlockFaceShape.MIDDLE_POLE_THICK && blockfaceshape != BlockFaceShape.MIDDLE_POLE_THIN;
    }

    private boolean canConnectToDown(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        return block instanceof BlockSlab || block instanceof BlockAsphaltSlab || block instanceof BlockAsphaltSlab1 || block instanceof BlockAsphaltSlabHorizontal || block instanceof BlockAsphaltSlabHorizontal2 &&
                blockfaceshape != BlockFaceShape.SOLID && blockfaceshape != BlockFaceShape.MIDDLE_POLE
                && blockfaceshape != BlockFaceShape.MIDDLE_POLE_THICK && blockfaceshape != BlockFaceShape.MIDDLE_POLE_THIN;

    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(UP, canFenceConnectTo(worldIn, pos)).withProperty(DOWN, canFenceConnectToDown(worldIn, pos));
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos)
    {
        BlockPos other = pos.offset(EnumFacing.UP);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, EnumFacing.UP.getOpposite()) || canConnectToUP(world, other, EnumFacing.UP.getOpposite());
    }

    private boolean canFenceConnectToDown(IBlockAccess world, BlockPos pos)
    {
        BlockPos other = pos.offset(EnumFacing.DOWN);
        Block block = world.getBlockState(other).getBlock();
        return canConnectToDown(world, other, EnumFacing.DOWN.getOpposite());
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return canConnectToUP(world, pos.offset(facing), facing.getOpposite());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
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

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.3125,0,0.3125,0.6875,1,0.6875);
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_3);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_4);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_5);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {UP, DOWN});
    }
}

