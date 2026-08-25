package com.jhxiaochen.dollancustomized.common.interaction;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignInteractionHandler {

    public static boolean handleSignClick(World world, BlockPos pos, EntityPlayer player, boolean isSneaking) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityDollanSign) {
            TileEntityDollanSign sign = (TileEntityDollanSign) te;

            if (world.isRemote) {
                // 客户端：处理视觉效果
                if (sign.isUsing) {
                    // 编辑状态添加视觉效果
                    addEditEffect(world, pos, sign.getEditedSlot());
                }
                return true;
            }

            if (!world.isRemote && isSneaking) {
                // 潜行右键切换编辑面
                int currentSlot = sign.getEditedSlot();
                int newSlot = currentSlot == 0 ? 1 : 0;
                sign.setEditedSlot(newSlot);

                // 发送更新到客户端
                if (player instanceof net.minecraft.entity.player.EntityPlayerMP) {
                    com.jhxiaochen.dollancustomized.network.NetworkManager.updateSignData(
                        (net.minecraft.entity.player.EntityPlayerMP) player, pos,
                        com.jhxiaochen.dollancustomized.network.NetworkManager.createSignNBT(sign));

                    // 打开GUI显示当前编辑的面
                    com.jhxiaochen.dollancustomized.network.NetworkManager.openJacarandaSignGui(
                        (net.minecraft.entity.player.EntityPlayerMP) player, pos, newSlot);
                }

                // 发送消息给玩家
                player.sendMessage(new net.minecraft.util.text.TextComponentString(
                    "已切换到" + (newSlot == 0 ? "正面" : "背面") + "编辑模式"));

                return true;
            }
        }
        return false;
    }

    // 添加编辑效果
    private static void addEditEffect(World world, BlockPos pos, int slot) {
        // 可以在这里添加粒子效果或声音效果
        // 例如：当告示牌被编辑时，播放一个轻微的音效或显示粒子

        // 未来可以扩展：添加切换面的视觉提示
        // 例如：显示一个箭头指示当前编辑的面
    }

    // 检查玩家是否可以编辑告示牌
    public static boolean canEditSign(World world, BlockPos pos, EntityPlayer player) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityDollanSign) {
            TileEntityDollanSign sign = (TileEntityDollanSign) te;

            // 检查是否为服主或OP（可选功能）
            // boolean canEdit = player.canCommandSenderUseCommand(2, "sign");

            // 简单的权限检查：只有方块的拥有者才能编辑
            return !sign.isWaxed(0) && !sign.isWaxed(1);
        }
        return false;
    }
}
