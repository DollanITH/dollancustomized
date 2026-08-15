package com.jhxiaochen.dollancustomized.world.gen;

import com.jhxiaochen.dollancustomized.init.blocks.WoodBlocks;

/**
 * 枫木普通树生成器
 * 使用枫木的原木和树叶，支持藤蔓生长
 */
public class JacarandaNormalTree extends net.minecraft.world.gen.feature.WorldGenTrees {

    public JacarandaNormalTree(int minTreeHeight) {
        // 使用5参数构造函数，传入枫木
        super(false, minTreeHeight,
              WoodBlocks.jacaranda_log.getDefaultState(),
                WoodBlocks.jacaranda_leaves.getDefaultState()
                        .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, false),
              false);
    }
}
