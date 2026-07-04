package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class CustomTTFFontRenderer {
    private final TTFFontLoader ttfLoader;
    public int FONT_HEIGHT = 8;

    public CustomTTFFontRenderer(TTFFontLoader loader) {
        this.ttfLoader = loader;
        if (loader != null && loader.getRawFont() != null) {
            this.FONT_HEIGHT = (int) loader.getRawFont().getSize();
        }
    }

    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) return 0;
        int width = 0;
        for (char c : text.toCharArray()) {
            float[] data = ttfLoader.getCharData(c);
            width += (data != null) ? data[2] * ttfLoader.textureWidth : ttfLoader.getRawFont().getSize();
        }
        return width;
    }

    public void drawString(String text, float x, float y, int color, boolean dropShadow) {
        if (ttfLoader == null || ttfLoader.getTextureLocation() == null || text == null || text.isEmpty()) return;

        if (color == 0) color = 0xFFFFFFFF;

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        if (a <= 0) a = 1.0F;

        GlStateManager.pushMatrix();

        // 外部平移，内部不再干涉
        GlStateManager.translate(x, y, 0.001F); // 轻微抬高防止 Z-fighting
        GlStateManager.scale(0.5F, 0.5F, 0.5F);

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );

        GlStateManager.color(r, g, b, a);

        ResourceLocation texture = ttfLoader.getTextureLocation();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        float currentX = 0;
        for (char c : text.toCharArray()) {
            float[] data = ttfLoader.getCharData(c);
            if (data == null) {
                ttfLoader.drawCharToTexture(c);
                data = ttfLoader.getCharData(c);
            }
            if (data == null) continue;

            float width = data[2] * ttfLoader.textureWidth;
            float height = data[3] * ttfLoader.textureHeight;
            if (width <= 0) width = ttfLoader.getRawFont().getSize();

            float u = data[0];
            float v = data[1];
            float u2 = data[0] + data[2];
            float v2 = data[1] + data[3];

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            buffer.pos(currentX, 0, 0).tex(u, v).color(r, g, b, a).endVertex();
            buffer.pos(currentX + width, 0, 0).tex(u2, v).color(r, g, b, a).endVertex();
            buffer.pos(currentX + width, height, 0).tex(u2, v2).color(r, g, b, a).endVertex();
            buffer.pos(currentX, height, 0).tex(u, v2).color(r, g, b, a).endVertex();

            tessellator.draw();

            currentX += width;
        }

        GlStateManager.enableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
    }
}
