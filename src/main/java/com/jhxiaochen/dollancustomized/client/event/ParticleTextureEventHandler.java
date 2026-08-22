package com.jhxiaochen.dollancustomized.client.event;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ParticleTextureEventHandler {

    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        // 注册所有12个蓝花楹粒子纹理
        for (int i = 0; i < 12; i++) {
            ResourceLocation texture = new ResourceLocation("dollancustomized", "particles/jacaranda_" + i);
            event.getMap().registerSprite(texture);
        }
    }
}