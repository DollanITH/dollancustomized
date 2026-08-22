package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.block.rail.base.TwoBlocksSignWithHang;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRailwaySign2 extends ItemMultiTexture
{
    private final Block block;

    public ItemRailwaySign2(Block block)
    {
        super(block,block,new String[] {"none","hang_wall"});
        this.block = block;
        this.setMaxDamage(0);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
            int i = stack.getMetadata();
            return i > 0 ? this.block.getTranslationKey() + "." + this.nameFunction.apply(stack) : this.block.getTranslationKey();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos))
            {
                EnumFacing enumfacing = EnumFacing.fromAngle(player.rotationYaw);
                if (itemstack.getMetadata( ) == 0 ) {
                    placeSign(worldIn, pos, enumfacing, this.block);
                    SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else if (facing != EnumFacing.DOWN && facing != EnumFacing.UP){
                    placeSignWall(worldIn, pos, facing, this.block);
                    SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else return EnumActionResult.PASS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
    }

    public static void placeSign(World worldIn, BlockPos pos, EnumFacing facing, Block sign)
    {
        BlockPos blockpos2 = pos.up();
        IBlockState iblockstate = sign.getDefaultState().withProperty(TwoBlocksSignWithHang.FACING, facing).withProperty(TwoBlocksSignWithHang.WALL, TwoBlocksSignWithHang.EnumWallHalf.NONE);
        worldIn.setBlockState(pos, iblockstate.withProperty(TwoBlocksSignWithHang.HALF, TwoBlocksSignWithHang.EnumSignHalf.LOWER), 2);
        worldIn.setBlockState(blockpos2, iblockstate.withProperty(TwoBlocksSignWithHang.HALF, TwoBlocksSignWithHang.EnumSignHalf.UPPER), 2);
    }

    public static void placeSignWall(World worldIn, BlockPos pos, EnumFacing facing, Block sign)
    {
        BlockPos blockpos2 = pos.up();
        IBlockState iblockstate = sign.getDefaultState().withProperty(TwoBlocksSignWithHang.FACING, facing).withProperty(TwoBlocksSignWithHang.WALL, TwoBlocksSignWithHang.EnumWallHalf.HANG_WALL);
        worldIn.setBlockState(pos, iblockstate.withProperty(TwoBlocksSignWithHang.HALF, TwoBlocksSignWithHang.EnumSignHalf.LOWER), 2);
        worldIn.setBlockState(blockpos2, iblockstate.withProperty(TwoBlocksSignWithHang.HALF, TwoBlocksSignWithHang.EnumSignHalf.UPPER), 2);
    }

}
