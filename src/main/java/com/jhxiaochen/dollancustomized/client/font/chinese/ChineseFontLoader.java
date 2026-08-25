package com.jhxiaochen.dollancustomized.client.font.chinese;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 中文字体加载器
 * 生成像素风格的硬边缘文字，用于快速渲染
 */
public class ChineseFontLoader {
    private Font rawFont;
    private final ResourceLocation fontLocation;
    private ResourceLocation textureLocation;
    public int textureWidth = 2048;
    public int textureHeight = 2048;
    private final float fontSize;
    private final float renderScale;
    private DynamicTexture dynamicTexture;
    private Graphics2D graphics2D;
    private BufferedImage image;
    private final Map<Character, float[]> charDataMap = new HashMap<>();
    private int currentX = 0;
    private int currentY = 0;

    public ChineseFontLoader(ResourceLocation fontLocation, float fontSize) {
        this.fontLocation = fontLocation;
        this.fontSize = fontSize;
        this.renderScale = 2.0f;
        loadFont();
        createTexture();
    }

    public ChineseFontLoader(ResourceLocation fontLocation, float fontSize, float renderScale) {
        this.fontLocation = fontLocation;
        this.fontSize = fontSize;
        this.renderScale = renderScale;
        loadFont();
        createTexture();
    }

    private void loadFont() {
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream();
            this.rawFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize * renderScale);
        } catch (Exception e) {
            System.err.println("[DollanCustomized] 无法加载字体文件: " + fontLocation);
            e.printStackTrace();
        }
    }

    private void createTexture() {
        if (this.rawFont == null) return;

        // 使用ARGB图像类型以支持透明度
        image = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        dynamicTexture = new DynamicTexture(image);
        this.graphics2D = image.createGraphics();
        this.graphics2D.setFont(this.rawFont);
        this.graphics2D.setColor(Color.WHITE);

        // 彻底关闭抗锯齿，保持像素风格
        this.graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);

        this.textureLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("chinese_font_dynamic", dynamicTexture);
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

        if (currentX + charWidth > textureWidth) {
            currentX = 0;
            currentY += charHeight;
        }

        if (currentY + charHeight > textureHeight) {
            System.err.println("[DollanCustomized] 中文字体纹理空间不足！");
            return;
        }

        // 直接绘制，不使用抗锯齿
        graphics2D.drawString(String.valueOf(c), currentX, currentY + fm.getAscent());

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

    public float getRenderScale() {
        return renderScale;
    }
}
