package com.jhxiaochen.dollancustomized.common.block.sign.boundingbox;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class RoadSignpostPoleRightBoundingBox {

    // 右向电线杆碰撞箱（西向基础）
    protected static final AxisAlignedBB WEST_AABB_VERTICAL = new AxisAlignedBB(1.0/16.0, 0, 7.0/16.0, 3.0/16.0, 24.0/16.0, 9.0/16.0);
    protected static final AxisAlignedBB WEST_AABB_HORIZONTAL = new AxisAlignedBB(1.25/16.0, 11.25/16.0, 4.0/16.0, 2.75/16.0, 12.75/16.0, 16.0/16.0);

    // 总体选择碰撞箱
    protected static final AxisAlignedBB SELECTING_AABB = new AxisAlignedBB(1.0/16.0, 0, 4.0/16.0, 3.0/16.0, 24.0/16.0, 16.0/16.0);

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

    // 根据方向获取所有碰撞箱
    public static AxisAlignedBB[] getAllCollisionBoxes(EnumFacing facing) {
        AxisAlignedBB[] boxes = new AxisAlignedBB[2];

        AxisAlignedBB rotatedVertical = rotateAABB(WEST_AABB_VERTICAL, facing);
        AxisAlignedBB rotatedHorizontal = rotateAABB(WEST_AABB_HORIZONTAL, facing);

        boxes[0] = rotatedVertical;
        boxes[1] = rotatedHorizontal;

        return boxes;
    }

    // 根据方向获取显示边界框
    public static AxisAlignedBB getDisplayBoundingBox(EnumFacing facing) {
        return rotateAABB(SELECTING_AABB, facing);
    }
}