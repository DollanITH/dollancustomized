package com.jhxiaochen.dollancustomized.client.font.chinese;

import com.jhxiaochen.dollancustomized.fixes.ShaderTextLightmap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

/**
 * 中文字体渲染器
 * 使用像素风格渲染，无边缘抗锯齿，高性能
 */
public class ChineseFontRenderer {
    private final ChineseFontLoader chineseFontLoader;
    public int FONT_HEIGHT = 8;

    public ChineseFontRenderer(ChineseFontLoader loader) {
        this.chineseFontLoader = loader;
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

     */
    public void drawString(String text, float x, float y, int red, int green, int blue) {
        if (chineseFontLoader == null || chineseFontLoader.getTextureLocation() == null || text == null || text.isEmpty()) return;

        float r = (float) red / 255.0F;
        float g = (float) green / 255.0F;
        float b = (float) blue / 255.0F;
        float a = 1.0F;

        Minecraft.getMinecraft().getTextureManager().bindTexture(chineseFontLoader.getTextureLocation());
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, -0.015F);
        GlStateManager.scale(1,-1,-1);

        float scale = chineseFontLoader.getRenderScale();
        float currentX = 0;

        // OptiFine 光影下着色器需要逐顶点 lightmap，否则文字会被延迟光照算成全黑
        int lightmapU = ShaderTextLightmap.u();
        int lightmapV = ShaderTextLightmap.v();

        for (char c : text.toCharArray()) {
            // 特殊处理空格字符
            if (c == ' ') {
                currentX += 4; // 空格宽度设为4像素
                continue;
            }

            float[] data = chineseFontLoader.getCharData(c);
            if (data == null) {
                chineseFontLoader.drawCharToTexture(c);
                data = chineseFontLoader.getCharData(c);
            }
            if (data == null) continue;

            int texX = (int) (data[0] * chineseFontLoader.textureWidth);
            int texY = (int) (data[1] * chineseFontLoader.textureHeight);
            int texW = (int) (data[2] * chineseFontLoader.textureWidth);
            int texH = (int) (data[3] * chineseFontLoader.textureHeight);

            if (texW <= 0) texW = (int) chineseFontLoader.getRawFont().getSize();
            if (texH <= 0) texH = (int) chineseFontLoader.getRawFont().getSize();

            int renderW = MathHelper.floor(texW / scale);
            int renderH = MathHelper.floor(texH / scale);

            float u1 = MathHelper.clamp((float) texX / (float) chineseFontLoader.textureWidth, 0.0F, 1.0F);
            float v1 = MathHelper.clamp((float) texY / (float) chineseFontLoader.textureHeight, 0.0F, 1.0F);
            float u2 = MathHelper.clamp((float) (texX + texW) / (float) chineseFontLoader.textureWidth, 0.0F, 1.0F);
            float v2 = MathHelper.clamp((float) (texY + texH) / (float) chineseFontLoader.textureHeight, 0.0F, 1.0F);

            int drawX = MathHelper.floor(currentX);
            int drawY = 0;

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
            buffer.pos(drawX, drawY, 0).tex(u1, v1).lightmap(lightmapU, lightmapV).color(r, g, b, a).endVertex();
            buffer.pos(drawX + renderW, drawY, 0).tex(u2, v1).lightmap(lightmapU, lightmapV).color(r, g, b, a).endVertex();
            buffer.pos(drawX + renderW, drawY + renderH, 0).tex(u2, v2).lightmap(lightmapU, lightmapV).color(r, g, b, a).endVertex();
            buffer.pos(drawX, drawY + renderH, 0).tex(u1, v2).lightmap(lightmapU, lightmapV).color(r, g, b, a).endVertex();
            tessellator.draw();

            currentX += renderW;
        }
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    /**
     * 获取字符串宽度
     */
    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) return 0;
        int width = 0;
        for (char c : text.toCharArray()) {
            // 特殊处理空格字符
            if (c == ' ') {
                width += 4; // 空格宽度设为4像素
                continue;
            }

            float[] data = chineseFontLoader.getCharData(c);
            if (data != null) {
                width += (data[2] * chineseFontLoader.textureWidth) / chineseFontLoader.getRenderScale();
            } else {
                width += (int) (chineseFontLoader.getRawFont().getSize() / chineseFontLoader.getRenderScale());
            }
        }
        return width;
    }
}
