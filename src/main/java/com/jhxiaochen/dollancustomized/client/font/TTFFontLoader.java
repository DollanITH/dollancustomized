package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TTFFontLoader {
    private Font rawFont;
    private final ResourceLocation fontLocation;
    private ResourceLocation textureLocation;
    public int textureWidth = 1024;
    public int textureHeight = 1024;
    private final float fontSize;
    private DynamicTexture dynamicTexture;
    private Graphics2D graphics2D;
    private BufferedImage image;
    private final Map<Character, float[]> charDataMap = new HashMap<>();
    private int currentX = 0;
    private int currentY = 0;

    public TTFFontLoader(ResourceLocation fontLocation, float fontSize) {
        this.fontLocation = fontLocation;
        this.fontSize = fontSize;
        loadFont();
        createTexture();
    }

    private void loadFont() {
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream();
            this.rawFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
        } catch (Exception e) {
            System.err.println("[DollanCustomized] 无法加载字体文件: " + fontLocation);
            e.printStackTrace();
        }
    }

    private void createTexture() {
        if (this.rawFont == null) return;

        image = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        dynamicTexture = new DynamicTexture(image);

        this.graphics2D = image.createGraphics();
        this.graphics2D.setFont(this.rawFont);
        this.graphics2D.setColor(new Color(0, 0, 0, 0));
        this.graphics2D.fillRect(0, 0, textureWidth, textureHeight);
        this.graphics2D.setColor(Color.WHITE);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.textureLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("road_text_font_dynamic", dynamicTexture);
    }

    public float[] getCharData(char c) {
        return charDataMap.get(c);
    }

    public void drawCharToTexture(char c) {
        if (this.graphics2D == null || this.rawFont == null) return;

        FontMetrics fm = this.graphics2D.getFontMetrics();
        int charWidth = fm.charWidth(c);
        int charHeight = fm.getHeight();

        if (charWidth <= 0 || charWidth > textureWidth || charHeight <= 0 || charHeight > textureHeight) {
            return;
        }

        if (currentX + charWidth >= textureWidth) {
            currentX = 0;
            currentY += charHeight;
        }

        if (currentY + charHeight >= textureHeight) {
            System.err.println("[DollanCustomized] 字体纹理空间不足！");
            return;
        }

        this.graphics2D.drawString(String.valueOf(c), currentX, currentY + fm.getAscent());

        // 【关键修复3】Minecraft 1.12.2 的 DynamicTexture 使用 ARGB 格式
        // 直接将 BufferedImage 的 ARGB 数据更新到 DynamicTexture 内部数组即可，无需转 ABGR！
        image.getRGB(0, 0, textureWidth, textureHeight, dynamicTexture.getTextureData(), 0, textureWidth);
        dynamicTexture.updateDynamicTexture();

        float u = (float) currentX / textureWidth;
        float v = (float) currentY / textureHeight;
        float widthUV = (float) charWidth / textureWidth;
        float heightUV = (float) charHeight / textureHeight;

        float[] data = new float[]{u, v, widthUV, heightUV};
        charDataMap.put(c, data);

        currentX += charWidth;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public Font getRawFont() {
        return rawFont;
    }
}
