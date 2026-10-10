package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

/**
 * 限高牌专用字体渲染器
 * 使用精细的抗锯齿渲染，视觉效果更好
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

        // 启用混合以支持透明度
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // 使用最近邻过滤保持像素清晰度
        GlStateManager.glTexParameteri(3553, 10241, 9729); // GL_TEXTURE_MIN_FILTER = GL_NEAREST
        GlStateManager.glTexParameteri(3553, 10240, 9729); // GL_TEXTURE_MAG_FILTER = GL_NEAREST

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, -0.015F);

        float scale = signFontLoader.getRenderScale();
        float currentX = 0;

        for (char c : text.toCharArray()) {
            float[] data = signFontLoader.getCharData(c);
            if (data == null) {
                signFontLoader.drawCharToTexture(c);
                data = signFontLoader.getCharData(c);
            }
            if (data == null) continue;

            int texX = (int) (data[0] * signFontLoader.textureWidth);
            int texY = (int) (data[1] * signFontLoader.textureHeight);
            int texW = (int) (data[2] * signFontLoader.textureWidth);
            int texH = (int) (data[3] * signFontLoader.textureHeight);

            if (texW <= 0) texW = (int) signFontLoader.getRawFont().getSize();
            if (texH <= 0) texH = (int) signFontLoader.getRawFont().getSize();

            int renderW = MathHelper.floor(texW / scale);
            int renderH = MathHelper.floor(texH / scale);

            float u1 = MathHelper.clamp((float) texX / (float) signFontLoader.textureWidth, 0.0F, 1.0F);
            float v1 = MathHelper.clamp((float) texY / (float) signFontLoader.textureHeight, 0.0F, 1.0F);
            float u2 = MathHelper.clamp((float) (texX + texW) / (float) signFontLoader.textureWidth, 0.0F, 1.0F);
            float v2 = MathHelper.clamp((float) (texY + texH) / (float) signFontLoader.textureHeight, 0.0F, 1.0F);

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
     * 绘制带极精细抗锯齿的文字
     * 使用最小范围的边缘处理，避免文字过粗
     */
    public void drawStringWithOutline(String text, float x, float y, int color) {
        if (signFontLoader == null || signFontLoader.getTextureLocation() == null || text == null || text.isEmpty()) {
            return;
        }

        if (color == 0) {
            color = 0xFF000000; // 默认黑色轮廓
        }

        float a = ((color >> 24) & 0xFF) / 255.0F;
        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;

        if (a <= 0) {
            a = 1.0F;
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(signFontLoader.getTextureLocation());

        // 启用混合以支持透明度
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // 使用线性过滤以获得平滑的抗锯齿效果
        GlStateManager.glTexParameteri(3553, 10241, 9728); // GL_TEXTURE_MIN_FILTER = GL_LINEAR
        GlStateManager.glTexParameteri(3553, 10240, 9728); // GL_TEXTURE_MAG_FILTER = GL_LINEAR

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, -0.015F);

        float scale = signFontLoader.getRenderScale();
        float currentX = 0;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        // 单次渲染：绘制主体文字
        for (char c : text.toCharArray()) {
            float[] data = signFontLoader.getCharData(c);
            if (data == null) {
                signFontLoader.drawCharToTexture(c);
                data = signFontLoader.getCharData(c);
            }
            if (data == null) continue;

            int texX = (int) (data[0] * signFontLoader.textureWidth);
            int texY = (int) (data[1] * signFontLoader.textureHeight);
            int texW = (int) (data[2] * signFontLoader.textureWidth);
            int texH = (int) (data[3] * signFontLoader.textureHeight);

            if (texW <= 0) texW = (int) signFontLoader.getRawFont().getSize();
            if (texH <= 0) texH = (int) signFontLoader.getRawFont().getSize();

            int renderW = MathHelper.floor(texW / scale);
            int renderH = MathHelper.floor(texH / scale);

            float u1 = MathHelper.clamp((float) texX / (float) signFontLoader.textureWidth, 0.0F, 1.0F);
            float v1 = MathHelper.clamp((float) texY / (float) signFontLoader.textureHeight, 0.0F, 1.0F);
            float u2 = MathHelper.clamp((float) (texX + texW) / (float) signFontLoader.textureWidth, 0.0F, 1.0F);
            float v2 = MathHelper.clamp((float) (texY + texH) / (float) signFontLoader.textureHeight, 0.0F, 1.0F);

            int drawX = MathHelper.floor(currentX);
            int drawY = 0;

            // 先绘制主体文字
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(drawX, drawY, 0).tex(u1, v1).color(r, g, b, a).endVertex();
            buffer.pos(drawX + renderW, drawY, 0).tex(u2, v1).color(r, g, b, a).endVertex();
            buffer.pos(drawX + renderW, drawY + renderH, 0).tex(u2, v2).color(r, g, b, a).endVertex();
            buffer.pos(drawX, drawY + renderH, 0).tex(u1, v2).color(r, g, b, a).endVertex();
            tessellator.draw();

            // 再绘制极精细的抗锯齿边缘（范围缩小到1/3）
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            // 仅下边缘微小抗锯齿（最小范围）
            buffer.pos(drawX + 0.1f, drawY + renderH - 0.1f, 0).tex(u1, v2).color(r * 0.4f, g * 0.4f, b * 0.4f, 0.04f).endVertex();
            buffer.pos(drawX + renderW - 0.1f, drawY + renderH - 0.1f, 0).tex(u2, v2).color(r * 0.4f, g * 0.4f, b * 0.4f, 0.04f).endVertex();

            // 四个角落的微弱抗锯齿（极小范围）
            buffer.pos(drawX - 0.05f, drawY - 0.05f, 0).tex(u1, v1).color(r * 0.1f, g * 0.1f, b * 0.1f, 0.025f).endVertex();
            buffer.pos(drawX + renderW + 0.05f, drawY - 0.05f, 0).tex(u2, v1).color(r * 0.1f, g * 0.1f, b * 0.1f, 0.025f).endVertex();
            buffer.pos(drawX - 0.05f, drawY + renderH + 0.05f, 0).tex(u1, v2).color(r * 0.1f, g * 0.1f, b * 0.1f, 0.025f).endVertex();
            buffer.pos(drawX + renderW + 0.05f, drawY + renderH + 0.05f, 0).tex(u2, v2).color(r * 0.1f, g * 0.1f, b * 0.1f, 0.025f).endVertex();

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
     * 绘制平滑文字（带抗锯齿）
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
