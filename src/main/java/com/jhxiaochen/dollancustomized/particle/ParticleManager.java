package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class ParticleManager {
    private static final Random RAND = new Random();

    /**
     * 根据固定参数生成粒子效果
     * @param world 世界对象
     * @param x X坐标
     * @param y Y坐标
     * @param z Z坐标
     * @param rand 随机对象
     */
    public static void spawnJacarandaParticles(World world, double x, double y, double z, Random rand) {
        // 基础粒子生成 (30分之1概率)
        if (rand.nextInt(30) == 0) {
            spawnJacarandaLeafParticles(world, x, y, z, 1);
        }

        // 微风效果 (20分之1概率)
        if (rand.nextInt(20) == 0) {
            spawnJacarandaLeafParticles(world, x, y, z, 2);
        }

        // 强风效果 (15分之1概率)
        if (rand.nextInt(15) == 0) {
            spawnJacarandaLeafParticles(world, x, y, z, 3);
            // 额外的风吹粒子
            spawnWindBlownParticles(world, x, y, z, 1.0);
        }

        // 自然消散粒子
        if (rand.nextInt(100) == 0) {
            spawnJacarandaLeafParticles(world, x, y, z, 1);
        }
    }

    /**
     * 生成蓝花楹花瓣粒子
     * @param world 世界对象
     * @param x X坐标
     * @param y Y坐标
     * @param z Z坐标
     * @param count 粒子数量
     */
    public static void spawnJacarandaLeafParticles(World world, double x, double y, double z, int count) {
        for (int i = 0; i < count; i++) {
            double offsetX = x + (RAND.nextDouble() - 0.5) * 0.3;
            double offsetY = y + (RAND.nextDouble() - 0.5) * 0.3;
            double offsetZ = z + (RAND.nextDouble() - 0.5) * 0.3;

            double motionX = (RAND.nextDouble() - 0.5) * 0.015; // 轻微的水平飘动
            double motionY = -0.03 - RAND.nextDouble() * 0.025; // 向下飘落
            double motionZ = (RAND.nextDouble() - 0.5) * 0.015; // 轻微的水平飘动

            // 优先使用自定义粒子，如果没有则回退到通用粒子
            spawnParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }

    /**
     * 生成风吹效果
     */
    public static void spawnWindBlownParticles(World world, double x, double y, double z, double windStrength) {
        int count = (int)(windStrength * 3);
        if (count < 1) count = 1;

        for (int i = 0; i < count; i++) {
            double offsetX = x + windStrength + (RAND.nextDouble() - 0.5) * 0.5;
            double offsetY = y + RAND.nextDouble() * 0.3;
            double offsetZ = z + (RAND.nextDouble() - 0.5) * 0.3;

            double motionX = windStrength * 0.025 + (RAND.nextDouble() - 0.5) * 0.02; // 风的水平影响更大
            double motionY = -0.035 - RAND.nextDouble() * 0.015; // 风吹粒子也向下
            double motionZ = (RAND.nextDouble() - 0.5) * 0.015;

            spawnParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }

    /**
     * 生成粒子（优先使用自定义粒子）
     */
    public static void spawnParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        if (world.isRemote) {
            try {
                // 使用蓝花楹花瓣粒子
                Particle petalParticle = new JacarandaPetalParticle(world, x, y, z, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(petalParticle);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 回退到通用粒子
        EnumParticleTypes particleType = RAND.nextDouble() > 0.5 ?
            EnumParticleTypes.VILLAGER_HAPPY :
            EnumParticleTypes.TOWN_AURA;
        world.spawnParticle(particleType, x, y, z, motionX, motionY, motionZ);
    }

    /**
     * 生成破坏时的粒子效果
     */
    public static void spawnBreakParticles(World world, double x, double y, double z) {
        if (world == null || !world.isRemote) {
            return;
        }

        // 生成8个破碎粒子
        for (int i = 0; i < 8; i++) {
            double offsetX = x + (RAND.nextDouble() - 0.5) * 0.6;
            double offsetY = y + (RAND.nextDouble() - 0.5) * 0.6;
            double offsetZ = z + (RAND.nextDouble() - 0.5) * 0.6;

            double motionX = (RAND.nextDouble() - 0.5) * 0.1;
            double motionY = RAND.nextDouble() * 0.1;
            double motionZ = (RAND.nextDouble() - 0.5) * 0.1;

            spawnParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }
}