package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.rail.StationStopWallSign;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
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

public class ItemStationStopWallSign extends ItemMultiTexture {
    public ItemStationStopWallSign() {
        super(DollanBlocks.station_stop_wall_sign, DollanBlocks.station_stop_wall_sign, new String[]{"wall", "wall", "wall", "wall"});
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.block.getUnlocalizedName() + "." + this.nameFunction.apply(stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block2 = iblockstate.getBlock();
        if (!itemstack.isEmpty()) {
            int i = this.getMetadata(itemstack.getMetadata( ));
            IBlockState iblockstate1 =
                    this.block.getStateForPlacement(worldIn, pos.offset(facing), facing, hitX, hitY, hitZ, i, player, hand);
                if (block2 == block && i == StationStopWallSign.EnumVarient.SINGLE_WALL_RIGHT.getMetadata() && iblockstate.getValue(StationStopWallSign.WALL) == StationStopWallSign.EnumVarient.SINGLE_WALL_RIGHT) {
                    iblockstate =
                            iblockstate.withProperty(StationStopWallSign.WALL, StationStopWallSign.EnumVarient.DOUBLE_WALL_RIGHT);
                    worldIn.setBlockState(pos, iblockstate, 11);
                    SoundType soundtype = iblockstate.getBlock( ).getSoundType(iblockstate, worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound( ), SoundCategory.BLOCKS, (soundtype.getVolume( ) + 1.0F) / 2.0F, soundtype.getPitch( ) * 0.8F);
                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else if (block2 == block && i == StationStopWallSign.EnumVarient.SINGLE_WALL_LEFT.getMetadata() && iblockstate.getValue(StationStopWallSign.WALL) == StationStopWallSign.EnumVarient.SINGLE_WALL_LEFT) {
                    iblockstate =
                            iblockstate.withProperty(StationStopWallSign.WALL, StationStopWallSign.EnumVarient.DOUBLE_WALL_LEFT);
                    worldIn.setBlockState(pos, iblockstate, 11);
                    SoundType soundtype = iblockstate.getBlock( ).getSoundType(iblockstate, worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound( ), SoundCategory.BLOCKS, (soundtype.getVolume( ) + 1.0F) / 2.0F, soundtype.getPitch( ) * 0.8F);
                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else if (worldIn.mayPlace(block, pos.offset(facing), false, facing, player) && player.canPlayerEdit(pos.offset(facing), facing, itemstack) ) {
                    if (placeBlockAt(itemstack, player, worldIn, pos.offset(facing), facing, hitX, hitY, hitZ, iblockstate1)) {
                        iblockstate1 = worldIn.getBlockState(pos.offset(facing));
                        SoundType soundtype = iblockstate1.getBlock( ).getSoundType(iblockstate1, worldIn, pos.offset(facing), player);
                        worldIn.playSound(player, pos.offset(facing), soundtype.getPlaceSound( ), SoundCategory.BLOCKS, (soundtype.getVolume( ) + 1.0F) / 2.0F, soundtype.getPitch( ) * 0.8F);
                        itemstack.shrink(1);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        else
        {
            return EnumActionResult.FAIL;
        }
        return EnumActionResult.FAIL;
    }
}
