package com.jhxiaochen.dollancustomized.world.gen;

import com.jhxiaochen.dollancustomized.common.JacarandaLeaves;
import com.jhxiaochen.dollancustomized.common.JacarandaLog;

/**
 * 蓝花楹普通树生成器
 * 使用蓝花楹木的原木和树叶，支持藤蔓生长
 */
public class JacarandaNormalTree extends net.minecraft.world.gen.feature.WorldGenTrees {

    public JacarandaNormalTree(int minTreeHeight) {
        // 使用5参数构造函数，传入蓝花楹木
        super(false, minTreeHeight,
                JacarandaLog.Registry.jacaranda_log.getDefaultState(),
                JacarandaLeaves.Registry.jacaranda_leaves.getDefaultState()
                        .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, false),
                false);
    }
}
