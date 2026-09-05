package com.jhxiaochen.dollancustomized.helper;

import net.minecraft.util.EnumFacing;

public final class PlacementContextHolder {

    private static final ThreadLocal<EnumFacing> PLAYER_FACING = new ThreadLocal<>();

    private PlacementContextHolder() {}

    public static void set(EnumFacing f) { PLAYER_FACING.set(f); }
    public static EnumFacing get() { return PLAYER_FACING.get(); }
    public static void clear() { PLAYER_FACING.remove(); }
}
