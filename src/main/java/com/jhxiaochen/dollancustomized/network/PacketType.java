package com.jhxiaochen.dollancustomized.network;

import net.minecraftforge.fml.relauncher.Side;

/**
 * 网络包类型枚举
 * 统一管理所有网络包的ID和方向
 */
public enum PacketType {
    // 客户端 -> 服务端
    ROAD_TEXT(0, Side.SERVER),
    HEIGHT_LIMIT_SIGN(1, Side.SERVER),
    JACARANDA_SIGN_TEXT(2, Side.SERVER),
    EDIT_STATE(3, Side.SERVER),

    // 服务端 -> 客户端
    OPEN_GUI(10, Side.CLIENT),
    UPDATE_SIGN_DATA(11, Side.CLIENT),
    GUI_OPEN(12, Side.CLIENT);

    private final int id;
    private final Side targetSide;

    PacketType(int id, Side targetSide) {
        this.id = id;
        this.targetSide = targetSide;
    }

    public int getId() {
        return id;
    }

    public Side getTargetSide() {
        return targetSide;
    }
}