package com.jhxiaochen.dollancustomized.common.block.rail.base;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class TwoBlocksSignBase extends Block {

    public static final     PropertyEnum<EnumSignHalf>
                                              HALF   = PropertyEnum.create("half", EnumSignHalf.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public TwoBlocksSignBase() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(HALF, EnumSignHalf.LOWER));
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

    public AxisAlignedBB getBoxXZ(IBlockState state, double x, double y, double z)
    {
        switch (state.getValue(FACING)){
            case WEST:
                switch (state.getValue(HALF))
                {
                    case LOWER:
                        return new AxisAlignedBB(1 - x, 0, z, 1, 1, 1 - z);
                    case UPPER:
                        return new AxisAlignedBB(1 - x, 0, z, 1, y, 1 - z);
                }
                break;
            case SOUTH:
                switch (state.getValue(HALF))
                {
                    case LOWER:
                        return new AxisAlignedBB(z, 0, 0, 1 - z, 1, x);
                    case UPPER:
                        return new AxisAlignedBB(z, 0, 0, 1 - z, y, x);
                }
                break;
            case NORTH:
                switch (state.getValue(HALF))
                {
                    case LOWER:
                        return new AxisAlignedBB(z, 0, 1 - x, 1 - z, 1, 1);
                    case UPPER:
                        return new AxisAlignedBB(z, 0, 1 - x, 1 - z, y, 1);
                }
                break;
            case EAST:
                switch (state.getValue(HALF))
                {
                    case LOWER:
                        return new AxisAlignedBB(0, 0, z, x, 1, 1 - z);
                    case UPPER:
                        return new AxisAlignedBB(0, 0, z, x, y, 1 - z);
                }
        }
        return Block.NULL_AABB;
    }

    /**
     * 以模型空间（facing = SOUTH，即不旋转）给出碰撞箱在 x、z 轴上的区间，
     * 再套用该朝向对应的 90/180/270 度旋转。区间不要求贴边，
     * 因此可以描述牌子背后向内偏移的立柱（模型 z: 2 -> 4）。
     * 下半部分（LOWER）占满整格高度；上半部分（UPPER）用 upperHeight。
     * 变换：south: x'=x, z'=z;  east: x'=z, z'=1-x;  north: x'=1-x, z'=1-z;  west: x'=1-z, z'=x
     */
    public AxisAlignedBB getBoxXZRange(IBlockState state, double x0, double x1, double y, double z0, double z1)
    {
        double upperHeight = (state.getValue(HALF) == EnumSignHalf.UPPER) ? y : 1.0;
        switch (state.getValue(FACING)){
            case EAST: // 90 度
                return new AxisAlignedBB(z0, 0, 1 - x1, z1, upperHeight, 1 - x0);
            case NORTH: // 180 度
                return new AxisAlignedBB(1 - x1, 0, 1 - z1, 1 - x0, upperHeight, 1 - z0);
            case WEST: // 270 度
                return new AxisAlignedBB(1 - z1, 0, x0, 1 - z0, upperHeight, x1);
            case SOUTH:
            default: // 0 度
                return new AxisAlignedBB(x0, 0, z0, x1, upperHeight, z1);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        // 牌子后面还带一根 brushed_aluminum 立柱（模型 z: 2 -> 4），碰撞箱取两者并集。
        // 上半部分的牌面与立柱同为 5px 高（立柱不再高出牌面），故并集高度取 5px。
        return getBoxXZRange(state, 4.0 / 16.0, 12.0 / 16.0, 5.0 / 16.0, 0.0, 4.0 / 16.0);
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getValue(HALF) != EnumSignHalf.LOWER ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumSignHalf varient = EnumSignHalf.LOWER;
        if ((meta & 0x4) != 0) varient = EnumSignHalf.UPPER;
        return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3)).withProperty(HALF, varient);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumSignHalf varient = state.getValue(HALF);
        int v = 0x0;
        if (varient == EnumSignHalf.UPPER) v = 0x4;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        BlockPos blockpos = pos.down();
        BlockPos blockpos1 = pos.up();

        if (player.capabilities.isCreativeMode && state.getValue(HALF) == EnumSignHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this)
        {
            worldIn.setBlockToAir(blockpos);
        }

        if (state.getValue(HALF) == EnumSignHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this)
        {
            if (player.capabilities.isCreativeMode)
            {
                worldIn.setBlockToAir(pos);
            }

            worldIn.setBlockToAir(blockpos1);
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == EnumSignHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        if (pos.getY() >= worldIn.getHeight() - 1)
        {
            return false;
        }
        else
        {
            return super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
        }
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (state.getValue(HALF) == EnumSignHalf.UPPER)
        {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
            else if (blockIn != this)
            {
                iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (flag1)
            {
                if (!worldIn.isRemote)
                {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF, FACING);
    }
    
    public enum EnumSignHalf implements IStringSerializable
    {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
