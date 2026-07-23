package com.jhxiaochen.dollancustomized.particle;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ParticleInit {

    public static void init(FMLInitializationEvent event) {
        // 粒子系统将在 ParticleTextureEventHandler 中注册
        // 这里不需要特别的初始化
    }
}