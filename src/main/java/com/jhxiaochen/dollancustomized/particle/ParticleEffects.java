package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.EnumParticleTypes;

public class ParticleEffects {

    /**
     * 生成单片蓝花楹花瓣粒子
     * @param world 世界对象
     * @param x X坐标
     * @param y Y坐标
     * @param z Z坐标
     */
    public static void spawnJacarandaLeafParticle(World world, double x, double y, double z) {
        if (world == null || !world.isRemote) {
            return;
        }

        // 粒子运动参数
        double motionX = (Math.random() - 0.5) * 0.02;
        double motionY = -0.01 - Math.random() * 0.02;
        double motionZ = (Math.random() - 0.5) * 0.02;

        // 使用温柔的粒子类型
        EnumParticleTypes particleType = Math.random() > 0.5 ?
            EnumParticleTypes.VILLAGER_HAPPY :
            EnumParticleTypes.TOWN_AURA;

        world.spawnParticle(particleType, x, y, z, motionX, motionY, motionZ);
    }

    /**
     * 生成多片花瓣粒子
     * @param world 世界对象
     * @param x X坐标
     * @param y Y坐标
     * @param z Z坐标
     * @param count 粒子数量
     */
    public static void spawnJacarandaLeafParticles(World world, double x, double y, double z, int count) {
        for (int i = 0; i < count; i++) {
            // 使用配置常量添加随机偏移
            double offsetX = x + (Math.random() - 0.5) * 0.3;
            double offsetY = y + (Math.random() - 0.5) * 0.3;
            double offsetZ = z + (Math.random() - 0.5) * 0.3;

            spawnJacarandaLeafParticle(world, offsetX, offsetY, offsetZ);
        }
    }

    /**
     * 生成风吹效果的花瓣
     * @param world 世界对象
     * @param x X坐标
     * @param y Y坐标
     * @param z Z坐标
     * @param windStrength 风力强度
     */
    public static void spawnWindBlownParticles(World world, double x, double y, double z, double windStrength) {
        if (world == null || !world.isRemote) {
            return;
        }

        // 根据风力决定生成数量
        int count = (int)(windStrength * 3);
        if (count < 1) count = 1;

        for (int i = 0; i < count; i++) {
            // 水平风力效果
            double offsetX = x + windStrength + (Math.random() - 0.5) * 0.5;
            double offsetY = y + Math.random() * 0.3;
            double offsetZ = z + (Math.random() - 0.5) * 0.3;

            // 水平运动更强
            double motionX = windStrength * 0.02 + (Math.random() - 0.5) * 0.01;
            double motionY = -0.01 - Math.random() * 0.02;
            double motionZ = (Math.random() - 0.5) * 0.01;

            EnumParticleTypes particleType = Math.random() > 0.5 ?
                EnumParticleTypes.VILLAGER_HAPPY :
                EnumParticleTypes.TOWN_AURA;

            world.spawnParticle(particleType, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
        }
    }
}