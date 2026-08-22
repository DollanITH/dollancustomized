package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanSign;
import com.jhxiaochen.dollancustomized.common.interaction.ExtendedPlayer;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemDollanSign extends Item {
    public Block wallSign;
    public Block standingSign;
    public ItemDollanSign(DollanSign wallSign, DollanSign standingSign) {
        super();
        this.wallSign = wallSign;
        this.standingSign = standingSign;
        wallSign.setItem(this);
        standingSign.setItem(this);
        this.maxStackSize = 16;
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        boolean flag = iblockstate.getBlock().isReplaceable(worldIn, pos);

        if (facing != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP)) {
            pos = pos.offset(facing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack)) {
                if (worldIn.isRemote) {
                    // 客户端：直接返回成功
                    return EnumActionResult.SUCCESS;
                } else {
                    // 服务端：放置方块
                    pos = flag ? pos.down() : pos;

                    if (facing == EnumFacing.UP) {
                        int i = MathHelper.floor((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                        worldIn.setBlockState(pos, standingSign.getDefaultState().withProperty(BlockStandingSign.ROTATION, Integer.valueOf(i)), 11);
                    } else {
                        worldIn.setBlockState(pos, wallSign.getDefaultState().withProperty(BlockWallSign.FACING, facing), 11);
                    }

                    TileEntity tileentity = worldIn.getTileEntity(pos);
                    if (tileentity instanceof TileEntityDollanSign && !ItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack )) {
                        // 模仿原版openEditSign方法，但使用自定义网络包
                        if (player instanceof EntityPlayerMP) {
                            ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, pos, tileentity);
                        }
                    }

                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        } else {
            return EnumActionResult.FAIL;
        }
    }
}