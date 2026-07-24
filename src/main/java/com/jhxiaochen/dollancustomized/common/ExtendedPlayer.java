package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.network.NetworkManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;

/**
 * 扩展的玩家类，用于添加自定义功能
 */
public class ExtendedPlayer {

    /**
     * 打开蓝花楹木告示牌的编辑GUI
     * 模仿原版的openEditSign方法，但使用自定义网络包
     */
    public static void openJacarandaSignGui(EntityPlayerMP player, BlockPos pos) {
        TileEntity tileentity = player.getServerWorld().getTileEntity(pos);

        if (tileentity instanceof TileEntitySign) {
            // 设置告示牌可编辑
            ((TileEntitySign) tileentity).setEditable(true);
            // 发送我们的自定义网络包
            NetworkManager.openJacarandaSignGui(player, pos);
        }
    }

    /**
     * 使用已获取的TileEntity打开告示牌GUI
     */
    public static void openJacarandaSignGui(EntityPlayerMP player, BlockPos pos, TileEntity tileentity) {
        if (tileentity instanceof TileEntitySign) {
            // 模仿原版openEditSign的所有步骤
            ((TileEntitySign) tileentity).setPlayer(player);
            ((TileEntitySign) tileentity).setEditable(true);
            // 强制同步TileEntity到客户端
            tileentity.markDirty();
            // 发送我们的自定义网络包
            NetworkManager.openJacarandaSignGui(player, pos);
        }
    }
}