package com.jhxiaochen.dollancustomized.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JacarandaParticleHelper {

    public static void spawnJacarandaParticle(World world, double x, double y, double z,
                                             double xSpeed, double ySpeed, double zSpeed) {
        // 创建自定义粒子
        JacarandaParticle particle = new JacarandaParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);

        // 添加到粒子渲染器
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    // 批量生成粒子
    public static void spawnJacarandaParticleBurst(World world, BlockPos pos, int count) {
        for (int i = 0; i < count; i++) {
            double x = pos.getX() + 0.5 + (world.rand.nextDouble() - 0.5) * 0.5;
            double y = pos.getY() + 0.5 + world.rand.nextDouble() * 0.5;
            double z = pos.getZ() + 0.5 + (world.rand.nextDouble() - 0.5) * 0.5;

            double xSpeed = (world.rand.nextDouble() - 0.5) * 0.02;
            double ySpeed = (world.rand.nextDouble() - 0.5) * 0.01;
            double zSpeed = (world.rand.nextDouble() - 0.5) * 0.02;

            spawnJacarandaParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}