package com.jhxiaochen.dollancustomized.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        // 基础粒子生成 (150分之1概率 - 大幅减少)
        if (rand.nextInt(150) == 0) {
            spawnDollanLeafParticles(world, x, y, z, 1);
        }

        // 微风效果 (80分之1概率 - 减少一半)
        if (rand.nextInt(80) == 0) {
            spawnDollanLeafParticles(world, x, y, z, 1);
        }

        // 强风效果 (60分之1概率 - 减少一半)
        if (rand.nextInt(60) == 0) {
            spawnDollanLeafParticles(world, x, y, z, 1);
            spawnWindBlownParticles(world, x, y, z, 0.5);
        }

        // 自然消散粒子 (300分之1概率 - 减少到三倍)
        if (rand.nextInt(300) == 0) {
            spawnDollanLeafParticles(world, x, y, z, 1);
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
    @SideOnly(Side.CLIENT)
    public static void spawnDollanLeafParticles(World world, double x, double y, double z, int count) {
        for (int i = 0; i < count; i++) {
            double offsetX = x + (RAND.nextDouble() - 0.5) * 0.2; // 减少散布范围
            double offsetY = y + (RAND.nextDouble() - 0.5) * 0.2; // 减少散布范围
            double offsetZ = z + (RAND.nextDouble() - 0.5) * 0.2; // 减少散布范围

            double motionX = (RAND.nextDouble() - 0.5) * 0.01; // 减少水平速度
            double motionY = -0.02 - RAND.nextDouble() * 0.015; // 减少垂直速度
            double motionZ = (RAND.nextDouble() - 0.5) * 0.01; // 减少水平速度

            // 使用新的粒子生成方法
            spawnParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }

    /**
     * 生成风吹效果
     */
    @SideOnly(Side.CLIENT)
    public static void spawnWindBlownParticles(World world, double x, double y, double z, double windStrength) {
        int count = (int)(windStrength * 2); // 减少风吹粒子数量
        if (count < 1) count = 1;

        for (int i = 0; i < count; i++) {
            double offsetX = x + windStrength * 0.5 + (RAND.nextDouble() - 0.5) * 0.3; // 减少风吹范围
            double offsetY = y + RAND.nextDouble() * 0.2; // 减少垂直范围
            double offsetZ = z + (RAND.nextDouble() - 0.5) * 0.3; // 减少水平范围

            double motionX = windStrength * 0.02 + (RAND.nextDouble() - 0.5) * 0.015; // 减少水平速度
            double motionY = -0.025 - RAND.nextDouble() * 0.01; // 减少垂直速度
            double motionZ = (RAND.nextDouble() - 0.5) * 0.015;

            spawnParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }

    /**
     * 生成粒子（优先使用自定义粒子）
     */
    public static void spawnParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        if (!world.isRemote) return;

        // 使用新的蓝花楹花瓣粒子
        JacarandaParticle petalParticle = new JacarandaParticle(world, x, y, z, motionX, motionY, motionZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(petalParticle);
    }

    /**
     * 生成破坏时的粒子效果
     */
    @SideOnly(Side.CLIENT)
    public static void spawnBreakParticles(World world, double x, double y, double z) {
        if (world == null || !world.isRemote) {
            return;
        }

        // 生成4个破碎粒子（减少一半）
        for (int i = 0; i < 4; i++) {
            double offsetX = x + (RAND.nextDouble() - 0.5) * 0.4; // 减少散布范围
            double offsetY = y + (RAND.nextDouble() - 0.5) * 0.4; // 减少散布范围
            double offsetZ = z + (RAND.nextDouble() - 0.5) * 0.4; // 减少散布范围

            double motionX = (RAND.nextDouble() - 0.5) * 0.08; // 减少水平速度
            double motionY = RAND.nextDouble() * 0.08; // 减少垂直速度
            double motionZ = (RAND.nextDouble() - 0.5) * 0.08; // 减少水平速度

            spawnParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }
}