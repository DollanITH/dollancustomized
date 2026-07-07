package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

public class CustomTTFFontRenderer {
    private final TTFFontLoader ttfLoader;
    public int FONT_HEIGHT = 8;

    public CustomTTFFontRenderer(TTFFontLoader loader) {
        this.ttfLoader = loader;
        if (loader != null && loader.getRawFont() != null) {
            this.FONT_HEIGHT = (int) (loader.getRawFont().getSize() / loader.getRenderScale());
        }
    }

    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) return 0;
        int width = 0;
        for (char c : text.toCharArray()) {
            float[] data = ttfLoader.getCharData(c);
            if (data != null) {
                width += (data[2] * ttfLoader.textureWidth) / ttfLoader.getRenderScale();
            } else {
                width += (int) (ttfLoader.getRawFont().getSize() / ttfLoader.getRenderScale());
            }
        }
        return width;
    }

    public void drawWhiteString(String text, float x, float y, boolean dropShadow) {
        drawString(text, x, y, 0xFFFFFFFF, dropShadow);
    }

    public void drawYellowString(String text, float x, float y, boolean dropShadow) {
        drawString(text, x, y, 0xFFEBE84E, dropShadow);
    }

    public void drawString(String text, float x, float y, int color, boolean dropShadow) {
        if (ttfLoader == null || ttfLoader.getTextureLocation() == null || text == null || text.isEmpty()) return;
        if (color == 0) color = 0xFFFFFFFF;

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        if (a <= 0) a = 1.0F;

        Minecraft.getMinecraft().getTextureManager().bindTexture(ttfLoader.getTextureLocation());

        // 启用混合以支持透明度
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        // 使用最近邻过滤，实现像素风格
        GlStateManager.glTexParameteri(3553, 10241, 9728); // GL_TEXTURE_MIN_FILTER = GL_NEAREST
        GlStateManager.glTexParameteri(3553, 10240, 9728); // GL_TEXTURE_MAG_FILTER = GL_NEAREST

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, -0.015F);

        float scale = ttfLoader.getRenderScale();
        float currentX = 0;

        for (char c : text.toCharArray()) {
            float[] data = ttfLoader.getCharData(c);
            if (data == null) {
                ttfLoader.drawCharToTexture(c);
                data = ttfLoader.getCharData(c);
            }
            if (data == null) continue;

            int texX = (int) (data[0] * ttfLoader.textureWidth);
            int texY = (int) (data[1] * ttfLoader.textureHeight);
            int texW = (int) (data[2] * ttfLoader.textureWidth);
            int texH = (int) (data[3] * ttfLoader.textureHeight);

            if (texW <= 0) texW = (int) ttfLoader.getRawFont().getSize();
            if (texH <= 0) texH = (int) ttfLoader.getRawFont().getSize();

            int renderW = MathHelper.floor(texW / scale);
            int renderH = MathHelper.floor(texH / scale);

            float u1 = MathHelper.clamp((float) texX / (float) ttfLoader.textureWidth, 0.0F, 1.0F);
            float v1 = MathHelper.clamp((float) texY / (float) ttfLoader.textureHeight, 0.0F, 1.0F);
            float u2 = MathHelper.clamp((float) (texX + texW) / (float) ttfLoader.textureWidth, 0.0F, 1.0F);
            float v2 = MathHelper.clamp((float) (texY + texH) / (float) ttfLoader.textureHeight, 0.0F, 1.0F);

            int drawX = MathHelper.floor(currentX);
            int drawY = 0;

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(drawX, drawY, 0).tex(u1, v1).color(r, g, b, a).endVertex();
            buffer.pos(drawX + renderW, drawY, 0).tex(u2, v1).color(r, g, b, a).endVertex();
            buffer.pos(drawX + renderW, drawY + renderH, 0).tex(u2, v2).color(r, g, b, a).endVertex();
            buffer.pos(drawX, drawY + renderH, 0).tex(u1, v2).color(r, g, b, a).endVertex();
            tessellator.draw();

            currentX += renderW;
        }

        GlStateManager.enableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
