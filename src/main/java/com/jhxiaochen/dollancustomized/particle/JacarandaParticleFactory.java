package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JacarandaParticleFactory {

    /**
     * Spawns a jacaranda particle using the Minecraft particle system
     */
    public static void spawnParticle(World world, double x, double y, double z,
                                   double motionX, double motionY, double motionZ) {
        if (world.isRemote) {
            try {
                Particle particle = new FixedJacarandaParticle(world, x, y, z, motionX, motionY, motionZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(particle);
            } catch (Exception e) {
                // Fallback to vanilla particle if custom particle fails
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.VILLAGER_HAPPY,
                                  x, y, z, motionX, motionY, motionZ);
            }
        }
    }
}