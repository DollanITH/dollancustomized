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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class SignalPost extends Block {
    private static final PropertyBool UP   = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");

    public SignalPost(String name) {
        super(Material.ROCK, MapColor.STONE);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(3.0F);
        this.setResistance(6.0F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {

        return BlockFaceShape.UNDEFINED;

    }


    public boolean canConnectToUP(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        Block block = iblockstate.getBlock();
        return block instanceof BlockSlab && blockfaceshape != BlockFaceShape.SOLID;
    }

    private boolean canConnectToDown(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        return block instanceof BlockSlab || block instanceof BlockAsphaltSlab || block instanceof BlockAsphaltSlab1 || block instanceof BlockAsphaltSlabHorizontal || block instanceof BlockAsphaltSlabHorizontal2 && blockfaceshape == BlockFaceShape.UNDEFINED;

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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.375,0,0.375,0.625,1,0.625);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {UP, DOWN});
    }
}

