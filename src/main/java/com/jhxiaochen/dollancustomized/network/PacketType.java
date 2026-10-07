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
    UPDATE_SIGN_TO_SERVER(4, Side.SERVER),
    STATION_NAME_BOARD(5, Side.SERVER),

    // 服务端 -> 客户端
    UPDATE_SIGN_DATA(11, Side.CLIENT),
    OPEN_JACARANDA_SIGN_GUI(12, Side.CLIENT),
    ITEM_UPDATE(13, Side.CLIENT),
    OPEN_TEXT_GUI(14, Side.CLIENT);

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

    public static void validateIds() {
        for (PacketType t1 : values()) {
            for (PacketType t2 : values()) {
                if (t1 != t2 && t1.id == t2.id)
                    throw new IllegalStateException("Duplicate ID: " + t1.id);
            }
        }
    }
}
