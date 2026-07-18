package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

/**
 * RoadText专用字体渲染器
 * 使用像素风格渲染，无边缘抗锯齿，高性能
 */
public class RoadFontRenderer {
    private final RoadFontLoader roadFontLoader;
    public int FONT_HEIGHT = 8;

    public RoadFontRenderer(RoadFontLoader loader) {
        this.roadFontLoader = loader;
        if (loader != null && loader.getRawFont() != null) {
            this.FONT_HEIGHT = (int) (loader.getRawFont().getSize() / loader.getRenderScale());
        }
    }

    /**
     * 绘制像素风格文字
     * 使用最近邻过滤，实现清晰的像素边缘
     *
     * @param text     要绘制的文字
     * @param x        起始X坐标
     * @param y        起始Y坐标
     * @param color    文字颜色（ARGB格式）
     */
    public void drawString(String text, float x, float y, int color) {
        if (roadFontLoader == null || roadFontLoader.getTextureLocation() == null || text == null || text.isEmpty()) return;
        if (color == 0) color = 0xFFFFFFFF;

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        if (a <= 0) a = 1.0F;

        Minecraft.getMinecraft().getTextureManager().bindTexture(roadFontLoader.getTextureLocation());

        // 启用混合以支持透明度
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // 使用最近邻过滤，实现像素风格
        GlStateManager.glTexParameteri(3553, 10241, 9728); // GL_TEXTURE_MIN_FILTER = GL_NEAREST
        GlStateManager.glTexParameteri(3553, 10240, 9728); // GL_TEXTURE_MAG_FILTER = GL_NEAREST

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, -0.015F);

        float scale = roadFontLoader.getRenderScale();
        float currentX = 0;

        for (char c : text.toCharArray()) {
            float[] data = roadFontLoader.getCharData(c);
            if (data == null) {
                roadFontLoader.drawCharToTexture(c);
                data = roadFontLoader.getCharData(c);
            }
            if (data == null) continue;

            int texX = (int) (data[0] * roadFontLoader.textureWidth);
            int texY = (int) (data[1] * roadFontLoader.textureHeight);
            int texW = (int) (data[2] * roadFontLoader.textureWidth);
            int texH = (int) (data[3] * roadFontLoader.textureHeight);

            if (texW <= 0) texW = (int) roadFontLoader.getRawFont().getSize();
            if (texH <= 0) texH = (int) roadFontLoader.getRawFont().getSize();

            int renderW = MathHelper.floor(texW / scale);
            int renderH = MathHelper.floor(texH / scale);

            float u1 = MathHelper.clamp((float) texX / (float) roadFontLoader.textureWidth, 0.0F, 1.0F);
            float v1 = MathHelper.clamp((float) texY / (float) roadFontLoader.textureHeight, 0.0F, 1.0F);
            float u2 = MathHelper.clamp((float) (texX + texW) / (float) roadFontLoader.textureWidth, 0.0F, 1.0F);
            float v2 = MathHelper.clamp((float) (texY + texH) / (float) roadFontLoader.textureHeight, 0.0F, 1.0F);

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

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    /**
     * 绘制像素风格白色文字（带阴影）
     */
    public void drawWhiteString(String text, float x, float y, boolean dropShadow) {
        drawString(text, x, y, 0xFFFFFFFF);
    }

    /**
     * 绘制像素风格黄色文字（带阴影）
     */
    public void drawYellowString(String text, float x, float y, boolean dropShadow) {
        drawString(text, x, y, 0xFFEBE84E);
    }

    /**
     * 获取字符串宽度
     */
    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) return 0;
        int width = 0;
        for (char c : text.toCharArray()) {
            float[] data = roadFontLoader.getCharData(c);
            if (data != null) {
                width += (data[2] * roadFontLoader.textureWidth) / roadFontLoader.getRenderScale();
            } else {
                width += (int) (roadFontLoader.getRawFont().getSize() / roadFontLoader.getRenderScale());
            }
        }
        return width;
    }
}
