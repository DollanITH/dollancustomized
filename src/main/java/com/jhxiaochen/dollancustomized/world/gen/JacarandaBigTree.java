package com.jhxiaochen.dollancustomized.world.gen;

import com.jhxiaochen.dollancustomized.common.JacarandaLeaves;
import com.jhxiaochen.dollancustomized.common.JacarandaLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

/**
 * 蓝花楹大树生成器
 * 简化的树生成逻辑，使用蓝花楹木原木和树叶
 */
public class JacarandaBigTree extends WorldGenAbstractTree {

    public JacarandaBigTree(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        // 设置树的高度
        int height = 5 + rand.nextInt(12);
        int trunkSize = rand.nextInt(10) == 0 ? 2 : 1;

        // 检查位置是否有效
        if (!isValidTreeLocation(worldIn, position, height)) {
            return false;
        }

        // 生成树干
        generateTrunk(worldIn, position, height, trunkSize);

        // 生成树叶
        generateLeaves(worldIn, position, height);

        return true;
    }

    private boolean isValidTreeLocation(World worldIn, BlockPos pos, int height) {
        // 检查种植土壤
        if (!canPlaceBlockOnGround(worldIn, pos)) {
            return false;
        }

        // 检查生长空间
        if (pos.getY() >= 1 && pos.getY() + height + 1 <= worldIn.getHeight()) {
            for (int y = pos.getY(); y <= pos.getY() + 1 + height; ++y) {
                int radius = (y == pos.getY()) ? 0 : (y >= pos.getY() + height - 2) ? 2 : 1;
                for (int x = pos.getX() - radius; x <= pos.getX() + radius; ++x) {
                    for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; ++z) {
                        BlockPos checkPos = new BlockPos(x, y, z);
                        if (y >= 0 && y < worldIn.getHeight()) {
                            if (!isReplaceable(worldIn, checkPos)) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean canPlaceBlockOnGround(World worldIn, BlockPos pos) {
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos.down());
        return state.getBlock().canSustainPlant(state, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)net.minecraft.init.Blocks.SAPLING);
    }

    private void generateTrunk(World worldIn, BlockPos base, int height, int trunkSize) {
        // 主干
        for (int y = 0; y < height; ++y) {
            setBlockAndNotifyAdequately(worldIn, base.up(y),
                JacarandaLog.Registry.jacaranda_log.getDefaultState());
        }

        // 双层树干
        if (trunkSize == 2) {
            setBlockAndNotifyAdequately(worldIn, base.east(),
                JacarandaLog.Registry.jacaranda_log.getDefaultState());
            setBlockAndNotifyAdequately(worldIn, base.east().south(),
                JacarandaLog.Registry.jacaranda_log.getDefaultState());
            setBlockAndNotifyAdequately(worldIn, base.south(),
                JacarandaLog.Registry.jacaranda_log.getDefaultState());
        }
    }

    private void generateLeaves(World worldIn, BlockPos base, int height) {
        int leafHeight = height - 4; // 树叶的起始高度

        // 生成树冠
        for (int y = leafHeight; y < height; ++y) {
            float radius = calculateLeafRadius(y, height);

            for (int x = -3; x <= 3; ++x) {
                for (int z = -3; z <= 3; ++z) {
                    float distance = (float)Math.sqrt(x * x + z * z);

                    if (distance <= radius) {
                        BlockPos leafPos = base.add(x, y, z);
                        IBlockState state = worldIn.getBlockState(leafPos);

                        if (state.getBlock().isAir(state, worldIn, leafPos) ||
                            state.getBlock().isLeaves(state, worldIn, leafPos)) {
                            setBlockAndNotifyAdequately(worldIn, leafPos,
                                JacarandaLeaves.Registry.jacaranda_leaves.getDefaultState()
                                    .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, false));
                        }
                    }
                }
            }
        }

        // 生成树叶层
        for (int y = leafHeight - 1; y >= Math.max(0, leafHeight - 3); --y) {
            float radius = calculateLeafRadius(y, height);

            for (int x = -2; x <= 2; ++x) {
                for (int z = -2; z <= 2; ++z) {
                    if (Math.abs(x) != 1 || Math.abs(z) != 1) {
                        BlockPos leafPos = base.add(x, y, z);
                        IBlockState state = worldIn.getBlockState(leafPos);

                        if (state.getBlock().isAir(state, worldIn, leafPos) ||
                            state.getBlock().isLeaves(state, worldIn, leafPos)) {
                            setBlockAndNotifyAdequately(worldIn, leafPos,
                                JacarandaLeaves.Registry.jacaranda_leaves.getDefaultState()
                                    .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, false));
                        }
                    }
                }
            }
        }
    }

    private float calculateLeafRadius(int y, int height) {
        float relativeY = (float)(y - (height - 8));
        if (relativeY <= 0) {
            return 3.0F; // 底层，半径最大
        } else if (relativeY >= 4) {
            return 1.0F; // 顶层，半径最小
        } else {
            return 3.0F - relativeY * 0.5F; // 逐渐缩小
        }
    }
}