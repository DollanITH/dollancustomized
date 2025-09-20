package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.rail.base.TwoBlocksSignBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRailwaySign extends ItemBlock
{
    private final Block block;

    public ItemRailwaySign(Block block)
    {
        super(block);
        this.block = block;
        this.setCreativeTab(DollanCustomized.METRO_TAB);
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return this.block.getUnlocalizedName();
    }

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
                EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
                placeSign(worldIn, pos, enumfacing, this.block);
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
    }

    public static void placeSign(World worldIn, BlockPos pos, EnumFacing facing, Block door)
    {
        BlockPos blockpos2 = pos.up();
        IBlockState iblockstate = door.getDefaultState().withProperty(TwoBlocksSignBase.FACING, facing);
        worldIn.setBlockState(pos, iblockstate.withProperty(TwoBlocksSignBase.HALF, TwoBlocksSignBase.EnumSignHalf.LOWER), 2);
        worldIn.setBlockState(blockpos2, iblockstate.withProperty(TwoBlocksSignBase.HALF, TwoBlocksSignBase.EnumSignHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door, false);
        worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
    }
}
