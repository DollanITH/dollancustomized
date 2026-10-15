package com.jhxiaochen.dollancustomized.common.block.rail.base;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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

public class RailwaySignBase extends BlockHorizontal {
    public RailwaySignBase() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setLightOpacity(0);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
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
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing());
        return state;
    }

    public AxisAlignedBB getBoxXZ(IBlockState state, double x, double y, double z)
    {
        switch (state.getValue(FACING)){
            case WEST:
                return new AxisAlignedBB(1 - x, 0, z, 1, y, 1 - z);
            case SOUTH:
            default:
                return new AxisAlignedBB(z, 0, 0, 1 - z, y, x);
            case NORTH:
                return new AxisAlignedBB(z, 0, 1 - x, 1 - z, y, 1);
            case EAST:
                return new AxisAlignedBB(0, 0, z, x, y, 1 - z);
        }
    }

    public AxisAlignedBB getBoxXZ(IBlockState state, double x, double z)
    {
        return getBoxXZ(state, x ,1, z);
    }

    /**
     * 以模型空间（facing = SOUTH，即不旋转）给出碰撞箱在 x、z 轴上的区间，
     * 再套用该朝向对应的 90/180/270 度旋转。
     * 与 {@link #getBoxXZ(IBlockState, double, double, double)} 不同，区间不要求贴边，
     * 因此可以描述向内偏移的部分（例如牌子背后 z: 2 -> 4 的立柱）。
     * 变换：south: x'=x, z'=z;  east: x'=z, z'=1-x;  north: x'=1-x, z'=1-z;  west: x'=1-z, z'=x
     */
    public AxisAlignedBB getBoxXZRange(IBlockState state, double x0, double x1, double y, double z0, double z1)
    {
        switch (state.getValue(FACING)){
            case EAST: // 90 度
                return new AxisAlignedBB(z0, 0, 1 - x1, z1, y, 1 - x0);
            case NORTH: // 180 度
                return new AxisAlignedBB(1 - x1, 0, 1 - z1, 1 - x0, y, 1 - z0);
            case WEST: // 270 度
                return new AxisAlignedBB(1 - z1, 0, x0, 1 - z0, y, x1);
            case SOUTH:
            default: // 0 度
                return new AxisAlignedBB(x0, 0, z0, x1, y, z1);
        }
    }

}
