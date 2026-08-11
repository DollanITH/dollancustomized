package com.jhxiaochen.dollancustomized.common.block.sign;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class RoadSignpostPoleDoubleBoundingBox {

    // 双电杆碰撞箱（西向基础）
    protected static final AxisAlignedBB WEST_AABB_MAIN_POLE = new AxisAlignedBB(1.0/16.0, 0, 7.0/16.0, 3.0/16.0, 24.0/16.0, 9.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_1 = new AxisAlignedBB(1.25/16.0, 3.25/16.0, 0, 2.75/16.0, 4.75/16.0, 16.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_2 = new AxisAlignedBB(1.25/16.0, 11.25/16.0, 0, 2.75/16.0, 12.75/16.0, 16.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_3 = new AxisAlignedBB(1.25/16.0, 19.25/16.0, 0, 2.75/16.0, 20.75/16.0, 16.0/16.0);

    // 标志牌（小部件）
    protected static final AxisAlignedBB WEST_ABB_SIGN_1_RIGHT = new AxisAlignedBB(1.0/16.0, 3.0/16.0, 10.0/16.0, 3.0/16.0, 5.0/16.0, 11.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_1_LEFT = new AxisAlignedBB(1.0/16.0, 3.0/16.0, 5.0/16.0, 3.0/16.0, 5.0/16.0, 6.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_2_RIGHT = new AxisAlignedBB(1.0/16.0, 11.0/16.0, 10.0/16.0, 3.0/16.0, 13.0/16.0, 11.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_2_LEFT = new AxisAlignedBB(1.0/16.0, 11.0/16.0, 5.0/16.0, 3.0/16.0, 13.0/16.0, 6.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_3_RIGHT = new AxisAlignedBB(1.0/16.0, 19.0/16.0, 10.0/16.0, 3.0/16.0, 21.0/16.0, 11.0/16.0);
    protected static final AxisAlignedBB WEST_ABB_SIGN_3_LEFT = new AxisAlignedBB(1.0/16.0, 19.0/16.0, 5.0/16.0, 3.0/16.0, 21.0/16.0, 6.0/16.0);

    // 总体选择碰撞箱
    protected static final AxisAlignedBB SELECTING_AABB = new AxisAlignedBB(1.0/16.0, 0, 7.0/16.0, 3.0/16.0, 24.0/16.0, 9.0/16.0);

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
        AxisAlignedBB[] boxes = new AxisAlignedBB[9];

        AxisAlignedBB rotatedMainPole = rotateAABB(WEST_AABB_MAIN_POLE, facing);
        AxisAlignedBB rotatedSign1 = rotateAABB(WEST_ABB_SIGN_1, facing);
        AxisAlignedBB rotatedSign2 = rotateAABB(WEST_ABB_SIGN_2, facing);
        AxisAlignedBB rotatedSign3 = rotateAABB(WEST_ABB_SIGN_3, facing);
        AxisAlignedBB rotatedSign1Right = rotateAABB(WEST_ABB_SIGN_1_RIGHT, facing);
        AxisAlignedBB rotatedSign1Left = rotateAABB(WEST_ABB_SIGN_1_LEFT, facing);
        AxisAlignedBB rotatedSign2Right = rotateAABB(WEST_ABB_SIGN_2_RIGHT, facing);
        AxisAlignedBB rotatedSign2Left = rotateAABB(WEST_ABB_SIGN_2_LEFT, facing);
        AxisAlignedBB rotatedSign3Right = rotateAABB(WEST_ABB_SIGN_3_RIGHT, facing);

        boxes[0] = rotatedMainPole;
        boxes[1] = rotatedSign1;
        boxes[2] = rotatedSign2;
        boxes[3] = rotatedSign3;
        boxes[4] = rotatedSign1Right;
        boxes[5] = rotatedSign1Left;
        boxes[6] = rotatedSign2Right;
        boxes[7] = rotatedSign2Left;
        boxes[8] = rotatedSign3Right;

        return boxes;
    }

    // 根据方向获取显示边界框
    public static AxisAlignedBB getDisplayBoundingBox(EnumFacing facing) {
        return rotateAABB(SELECTING_AABB, facing);
    }
}