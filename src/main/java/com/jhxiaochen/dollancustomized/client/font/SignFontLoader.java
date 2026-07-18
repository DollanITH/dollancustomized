package com.jhxiaochen.dollancustomized.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SignFontLoader {
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

    public SignFontLoader(ResourceLocation fontLocation, float fontSize, float renderScale) {
        this.fontLocation = fontLocation;
        this.fontSize = fontSize;
        this.renderScale = renderScale;
        loadFont();
        createTexture();
    }

    public SignFontLoader(ResourceLocation fontLocation, float fontSize) {
        this(fontLocation, fontSize, 1.0f);
    }

    /**
     * 构造函数：分别设置整数部分和小数部分的缩放比例
     * @param fontLocation 字体资源位置
     * @param fontSize 字体大小
     * @param integerPartScale 整数部分的缩放比例
     * @param fractionalPartScale 小数部分的缩放比例
     */
    public SignFontLoader(ResourceLocation fontLocation, float fontSize,
                         float integerPartScale, float fractionalPartScale) {
        this.fontLocation = fontLocation;
        this.fontSize = fontSize;
        this.renderScale = integerPartScale; // 保持向后兼容
        loadFont();
        createTexture();
    }

    // 用于边缘抗锯齿的临时缓冲区
    private static final int EDGE_SCALE = 4; // 边缘抗锯齿放大倍数

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

        // 启用适度抗锯齿，确保数字可见
        this.graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        this.textureLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("road_text_font_dynamic", dynamicTexture);

        // 预加载数字字符和常见符号
        String preloadChars = "0123456789.";
        for (char c : preloadChars.toCharArray()) {
            drawCharToTexture(c);
        }
    }

    public float[] getCharData(char c) {
        return charDataMap.get(c);
    }

    public void drawCharToTexture(char c) {
        if (this.graphics2D == null || this.rawFont == null) {
            return;
        }

        
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
            return;
        }

        // 创建放大的临时图像
        int scaledWidth = charWidth * EDGE_SCALE;
        int scaledHeight = charHeight * EDGE_SCALE;

        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D scaledGraphics = scaledImage.createGraphics();

        // 设置字体和颜色
        scaledGraphics.setFont(this.rawFont);
        scaledGraphics.setColor(Color.WHITE);

        // 启用抗锯齿以实现平滑过渡
        scaledGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        scaledGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        scaledGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);

        // 在放大后的图像上绘制抗锯齿字符
        FontRenderContext frc = scaledGraphics.getFontRenderContext();
        TextLayout layout = new TextLayout(String.valueOf(c), this.rawFont, frc);
        int baseline = (int) - layout.getAscent() * EDGE_SCALE;
        scaledGraphics.drawString(String.valueOf(c), 0, baseline);

        // 应用边缘羽化效果（Alpha渐变）
        int centerY = scaledHeight / 2;
        for (int y = 0; y < scaledHeight; y++) {
            for (int x = 0; x < scaledWidth; x++) {
                int centerX = scaledWidth / 2;
                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                double maxDistance = Math.sqrt(Math.pow(centerX, 2) + Math.pow(centerY, 2));

                int alpha = (int) (255 * (1 - distance / maxDistance));
                alpha = Math.max(0, Math.min(255, alpha));

                int pixel = scaledImage.getRGB(x, y);
                int currentAlpha = pixel >> 24 & 0xFF;
                int newAlpha = (currentAlpha * alpha) / 255;

                pixel = (pixel & 0x00FFFFFF) | (newAlpha << 24);
                scaledImage.setRGB(x, y, pixel);
            }
        }

        // 将放大的抗锯齿字符缩小到原始尺寸并绘制到主纹理
        this.graphics2D.drawImage(scaledImage, currentX, currentY, charWidth, charHeight, null);

        scaledGraphics.dispose();

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