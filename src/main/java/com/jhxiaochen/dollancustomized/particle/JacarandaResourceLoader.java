package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class JacarandaResourceLoader {

    /**
     * 预加载所有蓝花楹粒子纹理
     */
    public static void preloadTextures() {
        TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();

        // 确保所有纹理都已注册到材质图集中
        List<ResourceLocation> textures = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            textures.add(new ResourceLocation("dollancustomized", "particles/jacaranda_" + i));
        }

        // 强制加载纹理
        for (ResourceLocation loc : textures) {
            textureMap.getAtlasSprite(loc.toString());
        }
    }
}