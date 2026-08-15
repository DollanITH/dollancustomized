package com.jhxiaochen.dollancustomized.common.block.sign.boundingbox;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class RoadSignpostPoleHorizontalBoundingBox {

    // 水平电线杆碰撞箱（西向基础）
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(1.25/16.0, 3.25/16.0, 0, 2.75/16.0, 4.75/16.0, 16.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_1 = new AxisAlignedBB(1.25/16.0, 3.25/16.0, 0, 2.75/16.0, 4.75/16.0, 16.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_2 = new AxisAlignedBB(1.25/16.0, 11.25/16.0, 0, 2.75/16.0, 12.75/16.0, 16.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_3 = new AxisAlignedBB(1.25/16.0, 19.25/16.0, 0, 2.75/16.0, 20.75/16.0, 16.0/16.0);

    // 不同高度的变体（根据模型文件中的多个元素）
    protected static final AxisAlignedBB WEST_AABB_HIGH = new AxisAlignedBB(1.25/16.0, 11.25/16.0, 0, 2.75/16.0, 12.75/16.0, 16.0/16.0);
    protected static final AxisAlignedBB WEST_AABB_HIGHER = new AxisAlignedBB(1.25/16.0, 19.25/16.0, 0, 2.75/16.0, 20.75/16.0, 16.0/16.0);

    // 总体选择碰撞箱
    protected static final AxisAlignedBB SELECTING_AABB = new AxisAlignedBB(1.25/16.0, 3.25/16.0, 0, 2.75/16.0, 20.75/16.0, 16.0/16.0);

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
        AxisAlignedBB[] boxes = new AxisAlignedBB[3];

        AxisAlignedBB rotatedMain = rotateAABB(WEST_ABB_SIGN_1, facing);
        AxisAlignedBB rotatedHigh = rotateAABB(WEST_ABB_SIGN_2, facing);
        AxisAlignedBB rotatedHigher = rotateAABB(WEST_ABB_SIGN_3, facing);

        boxes[0] = rotatedMain;
        boxes[1] = rotatedHigh;
        boxes[2] = rotatedHigher;

        return boxes;
    }

    // 根据方向获取显示边界框
    public static AxisAlignedBB getDisplayBoundingBox(EnumFacing facing) {
        return rotateAABB(SELECTING_AABB, facing);
    }
}