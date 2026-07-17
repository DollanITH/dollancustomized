package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

/**
 * 限高牌专用字体渲染器
 * 使用边缘抗锯齿渲染，视觉效果更好
 */
public class SignFontRenderer {
    private final SignFontLoader signFontLoader;
    public int FONT_HEIGHT;

    public SignFontRenderer(SignFontLoader loader) {
        this.signFontLoader = loader;
        this.FONT_HEIGHT = (int) (loader.getRawFont().getSize() / loader.getRenderScale());
    }

    /**
     * 绘制文字（支持颜色和透明度）
     *
     * @param text  要绘制的文字
     * @param x     起始X坐标
     * @param y     起始Y坐标
     * @param color 文字颜色（ARGB格式）
     */
    public void drawString(String text, float x, float y, int color) {
        if (signFontLoader == null || signFontLoader.getTextureLocation() == null || text == null || text.isEmpty()) {
            System.out.println("[FONT RENDER] Can't render text: " + text + " (loader=" + (signFontLoader != null) + ", texture=" + (signFontLoader != null ? signFontLoader.getTextureLocation() : null) + ")");
            return;
        }

        if (color == 0) {
            color = 0xFFFFFFFF; // 默认白色
        }

        float a = ((color >> 24) & 0xFF) / 255.0F;
        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;

        if (a <= 0) {
            a = 1.0F;
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(signFontLoader.getTextureLocation());

        // 设置混合模式（参考原版FontRenderer）
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
        );

        // 禁用深度测试和写入（参考原版）
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);

        // 禁用纹理过滤（参考原版）
        GlStateManager.glTexParameteri(3553, 10241, 9728);
        GlStateManager.glTexParameteri(3553, 10240, 9728);

        float scale = signFontLoader.getRenderScale();
        float currentX = x;
        float currentY = y;

        for (char c : text.toCharArray()) {
            float[] data = signFontLoader.getCharData(c);
            if (data == null) {
                System.err.println("[SignFontRenderer] No char data for: " + c);
                continue;
            }

            float u1 = data[0];
            float v1 = data[1];
            float widthUV = data[2];
            float heightUV = data[3];

            int renderW = MathHelper.floor(widthUV * signFontLoader.textureWidth / scale);
            int renderH = MathHelper.floor(heightUV * signFontLoader.textureHeight / scale);

            // UV坐标
            u1 = MathHelper.clamp(u1, 0.0F, 1.0F);
            v1 = MathHelper.clamp(v1, 0.0F, 1.0F);
            float u2 = MathHelper.clamp(u1 + widthUV, 0.0F, 1.0F);
            float v2 = MathHelper.clamp(v1 + heightUV, 0.0F, 1.0F);

            int drawX = MathHelper.floor(currentX);
            int drawY = MathHelper.floor(currentY);

            // 绘制四边形
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

        // 恢复GL状态（参考原版）
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
    }

    /**
     * 绘制白色文字
     */
    public void drawWhiteString(String text, float x, float y) {
        drawString(text, x, y, 0xFFFFFFFF);
    }

    /**
     * 绘制黄色文字
     */
    public void drawYellowString(String text, float x, float y) {
        drawString(text, x, y, 0xFFEBE84E);
    }

    /**
     * 绘制文字（带透明度）
     */
    public void drawWithAlpha(String text, float x, float y, int color, float alpha) {
        int originalColor = color;
        color = (color & 0x00FFFFFF) | ((int) (alpha * 255) << 24);
        drawString(text, x, y, color);
    }

    /**
     * 绘制文字（别名，用于平滑边缘效果）
     */
    public void drawSmoothString(String text, float x, float y, int color) {
        drawString(text, x, y, color);
    }

    /**
     * 绑定字体纹理
     */
    public void bindTexture() {
        if (signFontLoader == null || signFontLoader.getTextureLocation() == null) {
            return;
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(signFontLoader.getTextureLocation());
    }

    /**
     * 获取字符串宽度
     */
    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        int width = 0;
        float scale = signFontLoader.getRenderScale();
        for (char c : text.toCharArray()) {
            float[] data = signFontLoader.getCharData(c);
            if (data != null) {
                float texW = data[2] * signFontLoader.textureWidth;
                width += (int) (texW / scale);
            } else {
                width += (int) (signFontLoader.getRawFont().getSize() / scale);
            }
        }
        return width;
    }
}