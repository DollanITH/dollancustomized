package com.jhxiaochen.dollancustomized.common.block.sign.boundingbox;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class RoadSignpostHalfBoundingBox {


    // 总体选择碰撞箱
    protected static final AxisAlignedBB SELECTING_AABB = new AxisAlignedBB(0.5/16.0, 0, 0/16.0, 1.25/16.0, 24.0/16.0, 16.0/16.0);

    private static AxisAlignedBB rotateAABB(AxisAlignedBB aabb, EnumFacing facing) {
        double minX = aabb.minX;
        double minY = aabb.minY;
        double minZ = aabb.minZ;
        double maxX = aabb.maxX;
        double maxY = aabb.maxY;
        double maxZ = aabb.maxZ;

        switch (facing) {
            case NORTH:
                // 西 → 北：围绕Y轴旋转90度
                return new AxisAlignedBB(minZ, minY, 1.0 - maxX, maxZ, maxY, 1.0 - minX);
            case EAST:
                // 西 → 东：围绕Y轴旋转180度
                return new AxisAlignedBB(1 - maxX, minY, 1.0 - minZ, 1.0 - minX, maxY, 1.0 - maxZ);
            case SOUTH:
                // 西 → 南：围绕Y轴旋转270度
                return new AxisAlignedBB(1 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX);
            default: // WEST
                return aabb;
        }
    }

    // 根据方向获取显示边界框
    public static AxisAlignedBB getDisplayBoundingBox(EnumFacing facing) {
        return rotateAABB(SELECTING_AABB, facing);
    }
}