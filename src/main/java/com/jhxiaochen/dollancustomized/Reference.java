package com.jhxiaochen.dollancustomized;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;

public class Reference {

    public static final String MOD_ID = "dollancustomized";
    public static final String VERSION = "3.1";

    public static final String CLIENT_PROXY_CLASS = "com.jhxiaochen.dollancustomized.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.jhxiaochen.dollancustomized.proxy.ServerProxy";

    public static final AxisAlignedBB LIGHT_LAMP_WEST_AABB =      new AxisAlignedBB(0.74999D, 1.0D, 0.74999D, 1.0D,  1.0625D, 1.0D);
    public static final AxisAlignedBB LIGHT_LAMP_EAST_AABB =      new AxisAlignedBB(0.0D,  1.0D, 0.0D,  0.25D, 1.0625D, 0.25D);
    public static final AxisAlignedBB LIGHT_LAMP_SOUTH_AABB =     new AxisAlignedBB(0.74999D, 1.0D, 0.0D,  1.0D,  1.0625D, 0.25D);
    public static final AxisAlignedBB LIGHT_LAMP_NORTH_AABB =     new AxisAlignedBB(0.0D,  1.0D, 0.74999D, 0.25D, 1.0625D, 1.0D);

}