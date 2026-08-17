package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.block.wood.jacaranda.JacarandaHangingSign;
import com.jhxiaochen.dollancustomized.common.block.wood.jacaranda.JacarandaHangingSignAttached;
import com.jhxiaochen.dollancustomized.common.interaction.ExtendedPlayer;
import com.jhxiaochen.dollancustomized.registry.DollanSounds;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaHangingSign;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
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

import java.util.List;

public class ItemJacarandaHangingSign extends Item {
    private Block block;
    private Block blockInWall;
    private JacarandaHangingSignAttached blockAttached;

    public ItemJacarandaHangingSign(Block block, Block blockInWall, JacarandaHangingSignAttached blockAttached) {
        this.maxStackSize = 16;
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setUnlocalizedName("jacaranda_hanging_sign");
        this.setRegistryName("jacaranda_hanging_sign");
        this.block = block;
        this.blockInWall = blockInWall;
        this.blockAttached = blockAttached;
        this.setBlock(block, blockInWall, blockAttached);
    }

    private void setBlock(Block block, Block blockInWall, JacarandaHangingSignAttached blockAttached) {
        this.block = block;
        this.blockInWall = blockInWall;
        this.blockAttached = blockAttached;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos clickPos, EnumHand hand, EnumFacing clickFacing, float hitX, float hitY, float hitZ) {
        /*获取放置坐标*/BlockPos placePos = clickPos.offset(clickFacing);

        /*获取点击的方块状态*/IBlockState clickBlockState = world.getBlockState(clickPos);
        /*获取点击的方块*/Block clickBlock = clickBlockState.getBlock();

        // 吞方块
        boolean replaceBlock = clickBlock.isReplaceable(world, clickPos);
        if (replaceBlock) placePos = clickPos;

        // 不能放液体里
        if (world.isOutsideBuildHeight(placePos)) return EnumActionResult.FAIL;

        if (replaceBlock || world.isAirBlock(placePos)) {
            EnumFacing playerFacing = player.getHorizontalFacing();

            if (clickFacing == EnumFacing.DOWN) {
                BlockPos upBlockPos = placePos.up();
                IBlockState upBlockState = world.getBlockState(upBlockPos);
                Block upBlock = upBlockState.getBlock();
                String upBlockName = upBlock.getRegistryName().toString();

                // 通过 玩家精确朝向 获取元数据
                int meta = getPlayerFacing(player.rotationYaw);

                if (!player.isSneaking() && upBlockName.contains("hanging") && this.canPlaceHangingSign(playerFacing, upBlock, upBlockState, meta)) {
                    // 双锁链悬挂告示牌 放置

                    // 获取新的元数据放置
                    placeBlock(player, world, placePos, hand, this.block, playerFacing.getIndex());            TileEntity tileentity = world.getTileEntity(placePos);
                    if (tileentity instanceof TileEntityJacarandaHangingSign && !ItemBlock.setTileEntityNBT(world, player, placePos, player.getHeldItem(hand))) {
                        // 模仿原版openEditSign方法，但使用自定义网络包（只在服务端打开GUI）
                        if (!world.isRemote && player instanceof EntityPlayerMP) {
                            ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, placePos, tileentity);
                        }
                    }
                    return EnumActionResult.SUCCESS;
                }

                if (
                    upBlockName.contains("wall") ||
                    upBlockName.contains("fence") ||
                    upBlockName.contains("chain") ||
                    upBlockName.contains("hanging") ||
                    upBlock instanceof net.minecraft.block.BlockPane ||
                    upBlock == Blocks.END_ROD &&
                        (
                            upBlock.getMetaFromState(upBlockState) == 0 ||
                            upBlock.getMetaFromState(upBlockState) == 1
                        )
                   )
                {
                    // 锁链汇聚于一点 悬挂告示牌
                    placeBlock(player, world, placePos, hand, this.blockAttached, meta);            TileEntity tileentity = world.getTileEntity(placePos);
                    if (tileentity instanceof TileEntityJacarandaHangingSign && !ItemBlock.setTileEntityNBT(world, player, placePos, player.getHeldItem(hand))) {
                        // 模仿原版openEditSign方法，但使用自定义网络包（只在服务端打开GUI）
                        if (!world.isRemote && player instanceof EntityPlayerMP) {
                            ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, placePos, tileentity);
                        }
                    }
                    return EnumActionResult.SUCCESS;
                }

                // 上方方块完整
                if (upBlockState.isSideSolid(world, upBlockPos, EnumFacing.DOWN)) {
                    // 不在精确方向列表
                    if (!getValidOrientations().contains(meta)) {
                        // 非正东南西北, 放置 旋转的 锁链汇聚于一点 悬挂告示牌
                        placeBlock(player, world, placePos, hand, this.blockAttached, meta);            TileEntity tileentity = world.getTileEntity(placePos);
                        if (tileentity instanceof TileEntityJacarandaHangingSign && !ItemBlock.setTileEntityNBT(world, player, placePos, player.getHeldItem(hand))) {
                            // 模仿原版openEditSign方法，但使用自定义网络包（只在服务端打开GUI）
                            if (!world.isRemote && player instanceof EntityPlayerMP) {
                                ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, placePos, tileentity);
                            }
                        }
                        return EnumActionResult.SUCCESS;
                    }
                    // 双锁链悬挂 告示牌放置

                    // 获取新的元数据放置
                    placeBlock(player, world, placePos, hand, this.block, playerFacing.getIndex());            TileEntity tileentity = world.getTileEntity(placePos);
                    if (tileentity instanceof TileEntityJacarandaHangingSign && !ItemBlock.setTileEntityNBT(world, player, placePos, player.getHeldItem(hand))) {
                        // 模仿原版openEditSign方法，但使用自定义网络包（只在服务端打开GUI）
                        if (!world.isRemote && player instanceof EntityPlayerMP) {
                            ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, placePos, tileentity);
                        }
                    }
                    return EnumActionResult.SUCCESS;
                }
            }

            IBlockState signState = null;
            String clickBlockName = clickBlock.getRegistryName().toString();

            if (
                ( // 排除无连接功能的非完整方块
                    !clickBlock.isFullCube(clickBlockState) &&
                    !clickBlockName.contains("wall") &&
                    !clickBlockName.contains("fence") &&
                    !clickBlockName.contains("chain")
                ) ||
                (clickFacing == EnumFacing.UP || clickFacing == EnumFacing.DOWN)
            ) {
                // 通过玩家朝向获取方块元数据
                signState = this.blockInWall.getStateFromMeta(getPlayerFacing(player.rotationYaw));
            }
            else {
                // 旋转 90度 放置
                signState = this.blockInWall.getDefaultState().withProperty(JacarandaHangingSign.FACING, clickFacing.rotateY());
            }

            // 墙上的悬挂告示牌
            placeBlock(player, world, placePos, hand, signState);
            TileEntity tileentity = world.getTileEntity(placePos);
            if (tileentity instanceof TileEntityJacarandaHangingSign && !ItemBlock.setTileEntityNBT(world, player, placePos, player.getHeldItem(hand))) {
                // 模仿原版openEditSign方法，但使用自定义网络包（只在服务端打开GUI）
                if (!world.isRemote && player instanceof EntityPlayerMP) {
                    ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, placePos, tileentity);
                }
            }
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    // 放置方块
    public void placeBlock(EntityPlayer player, World world, BlockPos pos, EnumHand hand, Block block, int meta) {
        this.placeBlock(player, world, pos, hand, block.getStateFromMeta(meta));
    }

    public void placeBlock(EntityPlayer player, World world, BlockPos pos, EnumHand hand, IBlockState signState) {
        /*放置方块*/world.setBlockState(pos, signState);

        /*消耗一个物品*/player.getHeldItem(hand).shrink(1);

        //播放音效
        player.playSound(DollanSounds.BLOCK_CHERRY_WOOD_PLACE, 1.0F, 1.0F);
    }

    // 是否能放置双锁链告示牌
    public boolean canPlaceHangingSign(EnumFacing playerFacing, Block upBlock, IBlockState upBlockState, int playerAccurateFacing) {
        if (upBlock instanceof JacarandaHangingSign) {
            if (getValidOrientations().contains(playerAccurateFacing)) {
                EnumFacing blockFacing = upBlockState.getValue(JacarandaHangingSign.FACING);
                return playerFacing.getAxis() == blockFacing.getAxis();
            }
        }
        else if (upBlock instanceof JacarandaHangingSignAttached) {
            int blockFacing = upBlock.getMetaFromState(upBlockState);

            if (blockFacing == 0 || blockFacing == 8) {
                return playerAccurateFacing == 0 || playerAccurateFacing == 8;
            }
            if (blockFacing == 4 || blockFacing == 12) {
                return playerAccurateFacing == 4 || playerAccurateFacing == 12;
            }
        }
        return false;
    }

    // 获取玩家朝向
    private static int getPlayerFacing(float yaw) {
        return MathHelper.floor((double)((yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
    }

    // 获取有效朝向
    private static List<Integer> getValidOrientations() {
        return java.util.Arrays.asList(0, 4, 8, 12);
    }

    // 燃料功能
    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 800;
    }

}