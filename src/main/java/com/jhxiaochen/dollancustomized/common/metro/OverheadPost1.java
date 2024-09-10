package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class OverheadPost1 extends Block {
    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE =
            PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, BlockRailBase.EnumRailDirection.NORTH_SOUTH, BlockRailBase.EnumRailDirection.EAST_WEST);
    public OverheadPost1(String name) {
        super(Material.IRON, MapColor.IRON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(4.0F);
        this.setResistance(6.0F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
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
        return new AxisAlignedBB(0.4,0,0.4,0.6,1,0.6);
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        EnumFacing direction = placer.getHorizontalFacing().getOpposite();
        boolean flag1 = direction == EnumFacing.EAST || direction == EnumFacing.WEST;
        return state.withProperty(SHAPE, flag1 ? BlockRailBase.EnumRailDirection.EAST_WEST : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }


    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE)).getMetadata();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE))
                {
                    case EAST_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                    case NORTH_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {SHAPE});
    }

}

