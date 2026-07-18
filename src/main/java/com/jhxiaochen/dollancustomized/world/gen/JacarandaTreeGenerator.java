package com.jhxiaochen.dollancustomized.world.gen;

import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * 蓝花楹木树生成器
 * 使用自定义的原木和树叶来生成蓝花楹树
 */
public class JacarandaTreeGenerator {

    public static WorldGenerator createGenerator(boolean isBigTree, Random rand) {
        if (isBigTree) {
            return new JacarandaBigTree(false);
        } else {
            int minHeight = 4 + rand.nextInt(3);
            return new JacarandaNormalTree(minHeight);
        }
    }
}
