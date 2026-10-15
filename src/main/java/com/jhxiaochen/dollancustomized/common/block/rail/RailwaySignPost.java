package com.jhxiaochen.dollancustomized.common.block.rail;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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

public class RailwaySignPost extends BlockHorizontal {
    private static final PropertyBool DOWN = PropertyBool.create("down");

    public RailwaySignPost() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setLightOpacity(0);
        this.setRegistryName("railway_sign_post");
        this.setTranslationKey("RailwaySignPost");
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {

        return BlockFaceShape.UNDEFINED;

    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        Block block = iblockstate.getBlock();
        return block instanceof BlockSlab && !block.isTopSolid(iblockstate) && blockfaceshape != BlockFaceShape.SOLID;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(DOWN, canFenceConnectTo(worldIn, pos));
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos)
    {
        BlockPos other = pos.offset(EnumFacing.DOWN);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, EnumFacing.DOWN.getOpposite()) || canConnectTo(world, other, EnumFacing.DOWN.getOpposite());
    }

    /** 立柱在模型 x 轴上的区间（x: 7 -> 9，居中 2px 厚）。 */
    private static final double MODEL_X_MIN = 7.0 / 16.0;
    private static final double MODEL_X_MAX = 9.0 / 16.0;
    /** 立柱在模型 z 轴上的区间（z: 2 -> 4，2px 厚且整体向内偏移 2px）。 */
    private static final double MODEL_Z_MIN = 2.0 / 16.0;
    private static final double MODEL_Z_MAX = 4.0 / 16.0;

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return getBoxXZ(state, MODEL_X_MIN, MODEL_X_MAX, MODEL_Z_MIN, MODEL_Z_MAX);
    }

    /**
     * 以模型空间（facing = SOUTH，即不旋转）给出立柱在 x、z 轴上的区间，
     * 再套用该朝向对应的 90/180/270 度旋转。区间不要求贴边，
     * 因此可以描述向内偏移的立柱（如 z: 2 -> 4）。
     */
    public AxisAlignedBB getBoxXZ(IBlockState state, double x0, double x1, double z0, double z1)
    {
        return switch (state.getValue(FACING)) {
            case EAST -> // 90 度
                    new AxisAlignedBB(z0, 0, 1 - x1, z1, 1, 1 - x0);
            case NORTH -> // 180 度
                    new AxisAlignedBB(1 - x1, 0, 1 - z1, 1 - x0, 1, 1 - z0);
            case WEST -> // 270 度
                    new AxisAlignedBB(1 - z1, 0, x0, 1 - z0, 1, x1);
            default -> // 0 度
                    new AxisAlignedBB(x0, 0, z0, x1, 1, z1);
        };
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return canConnectTo(world, pos.offset(facing), facing.getOpposite());
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
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, DOWN);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing());
        return state;
    }

}
