package com.jhxiaochen.dollancustomized.common.rail.base;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class TwoBlocksSignWithHang extends TwoBlocksSignBase {

    public static final PropertyEnum<EnumWallHalf>
            WALL   = PropertyEnum.create("wall", EnumWallHalf.class);
    public enum EnumWallHalf implements IStringSerializable
    {
        HANG_WALL,
        NONE;

        public String toString()
        {
            return this.getName();
        }

        public int getMetadata()
        {
            return this == HANG_WALL ? 1 : 0 ;
        }

        public String getName()
        {
            return this == HANG_WALL ? "hang_wall" : "none";
        }

    }


    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF, WALL, FACING);
    }

    @Override
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

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
        items.add(new ItemStack(this, 1, 1));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        super.getPickBlock(state, target, world, pos, player);
        if (state.getValue(WALL) == EnumWallHalf.NONE) return new ItemStack(this, 1 , 0);
        else return new ItemStack(this, 1 , 1);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == EnumSignHalf.UPPER ? Items.AIR : getItem();
    }

    private Item getItem()
    {
        if (getDefaultState().getValue(WALL) == EnumWallHalf.NONE){
            return new ItemStack(this, 1 , EnumWallHalf.NONE.getMetadata( )).getItem( );
        } else {
            return new ItemStack(this, 1 , EnumWallHalf.HANG_WALL.getMetadata( )).getItem( );
        }
    }

    public int damageDropped(IBlockState state)
    {
        return ((state.getValue(WALL)).getMetadata());
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumSignHalf varient = EnumSignHalf.LOWER;
        EnumWallHalf wall = EnumWallHalf.NONE;
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        switch (meta) {
            case 0:
            case 1:
            case 2:
            case 3:
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                varient = EnumSignHalf.UPPER;
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                wall = EnumWallHalf.HANG_WALL;
                break;
            case 12:
            case 13:
            case 14:
            case 15:
                varient = EnumSignHalf.UPPER;
                wall = EnumWallHalf.HANG_WALL;
            break;
            default:
            break;
             }
        return getDefaultState().withProperty(FACING, facing).withProperty(HALF, varient).withProperty(WALL, wall);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | (state.getValue(FACING)).getHorizontalIndex();

        if (state.getValue(WALL) == EnumWallHalf.HANG_WALL)
        {
            i |= 8;
            if (state.getValue(HALF) == EnumSignHalf.UPPER)
            {
                i = 12 + (state.getValue(FACING)).getHorizontalIndex();
            }
        }
        else
        {
            if (state.getValue(HALF) == EnumSignHalf.UPPER)
            {
                i |= 4;
            }
        }

        return i;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(WALL) == EnumWallHalf.NONE ? getBoxXZ(state,2.0 / 16.0,5.0 / 16.0,12.0 / 16.0) : getBoxXZ(state,0.01 / 16.0,5.0 / 16.0,12.0 / 16.0);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing enumfacing = state.getValue(FACING);
        if (state.getValue(HALF) == EnumSignHalf.UPPER)
        {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (state.getValue(WALL) == EnumWallHalf.HANG_WALL && !worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid())
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

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
}
