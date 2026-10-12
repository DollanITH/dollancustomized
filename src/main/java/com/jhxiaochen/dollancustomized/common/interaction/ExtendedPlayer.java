package com.jhxiaochen.dollancustomized.common.interaction;

import com.jhxiaochen.dollancustomized.network.NetworkManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Method;

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
        openJacarandaSignGui(player, pos, tileentity);
    }

    /**
     * 使用已获取的TileEntity打开告示牌GUI
     */
    public static void openJacarandaSignGui(EntityPlayerMP player, BlockPos pos, TileEntity tileentity) {
        if (tileentity instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign) tileentity;

            // 模仿原版 openEditSign 的两步：设置玩家、设置可编辑。
            // 这里必须用反射调用，不能直接写 sign.setPlayer(player) / sign.setEditable(true)：
            // 这两个方法在部分服务端运行时（例如 SpongeForge）没有对应的 SRG 符号
            // （func_145912_a / func_145913_a），直接调用会在运行时抛出
            // NoSuchMethodError，导致放置告示牌时整个交互崩掉。
            // 反射调用在找不到方法时会安静跳过；TileEntityDollanSign 已经重写了
            // getIsEditable() 恒返回 true，因此跳过也不会影响编辑功能。
            setSignPlayer(sign, player);
            setSignEditable(sign, true);

            // 强制同步TileEntity到客户端
            tileentity.markDirty();
            // 发送我们的自定义网络包
            NetworkManager.openJacarandaSignGui(player, pos);
        }
    }

    /**
     * 尽力把告示牌标记为“可编辑”。
     * 找不到对应方法（不同运行时映射不同）时什么都不做，绝不抛链接错误。
     */
    public static void setSignEditable(TileEntitySign sign, boolean editable) {
        invokeSignSetter(sign, editable, "setEditable", "func_145913_a");
    }

    /**
     * 尽力把告示牌绑定到指定玩家，语义同原版 openEditSign 的第一步。
     */
    public static void setSignPlayer(TileEntitySign sign, EntityPlayerMP player) {
        invokeSignSetter(sign, player, "setPlayer", "func_145912_a");
    }

    /**
     * 按方法名（MCP 名或 SRG 名）调用 TileEntitySign 上的单参数方法。
     * 方法不存在或调用失败时静默跳过。
     */
    private static void invokeSignSetter(TileEntitySign sign, Object value, String... names) {
        for (Class<?> type = sign.getClass(); type != null; type = type.getSuperclass()) {
            for (Method method : type.getDeclaredMethods()) {
                if (method.getParameterCount() != 1 || !isAnyName(method.getName(), names)) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    method.invoke(sign, value);
                } catch (Throwable ignored) {
                    // 该运行时不存在这个方法，忽略即可
                }
                return;
            }
        }
    }

    private static boolean isAnyName(String name, String[] names) {
        for (String candidate : names) {
            if (candidate.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
