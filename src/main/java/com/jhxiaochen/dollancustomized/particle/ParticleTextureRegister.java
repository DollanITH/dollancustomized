package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ParticleTextureRegister {
    private static final List<ResourceLocation> PARTICLE_TEXTURES = new ArrayList<>();

    static {
        // 注册所有粒子纹理
        for (int i = 0; i < 12; i++) {
            ResourceLocation texture = new ResourceLocation("dollancustomized", "particles/jacaranda_" + i);
            PARTICLE_TEXTURES.add(texture);
        }
    }

    /**
     * 预加载所有粒子纹理
     */
    public static void preloadTextures() {
        TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();

        for (ResourceLocation texture : PARTICLE_TEXTURES) {
            // 强制纹理加载
            textureMap.getAtlasSprite(texture.toString());
        }

        System.out.println("Loaded " + PARTICLE_TEXTURES.size() + " jacaranda particle textures");
    }

    /**
     * 获取指定索引的纹理
     */
    public static ResourceLocation getTexture(int index) {
        if (index >= 0 && index < PARTICLE_TEXTURES.size()) {
            return PARTICLE_TEXTURES.get(index);
        }
        return PARTICLE_TEXTURES.get(0); // 默认返回第一个纹理
    }
}