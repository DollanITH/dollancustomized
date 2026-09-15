package com.jhxiaochen.dollancustomized.client.renderer.station;

import com.jhxiaochen.dollancustomized.common.block.metro.BlockStationNameBoard;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class StationNameBoardRenderer extends TileEntitySpecialRenderer<TileEntityStationNameBoard> {

    @Override
    public void render(TileEntityStationNameBoard te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 基本验证
        if (te == null || (te.getChineseText() == null || te.getChineseText().isEmpty()) &&
            (te.getEnglishText() == null || te.getEnglishText().isEmpty())) {
            return;
        }

        // 区块加载检查
        if (te.getWorld() == null || !te.getWorld().isBlockLoaded(te.getPos(), false)) {
            return;
        }

        GlStateManager.pushMatrix();

        try {
            // 获取方块朝向
            EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockStationNameBoard.FACING);

            // 获取字体设置
            float fontSizeMultiplier = te.getFontSizeMultiplier();
            float fontSize = te.fontSize;
            String fontCN = te.getFontFamilyChinese();
            int r = te.colorRed;
            int g = te.colorGreen;
            int b = te.colorBlue;

            // 移动到方块中心
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

            // 旋转逻辑
            float rotateY = 0.0F;
            switch (facing) {
                case NORTH:
                    rotateY = -180.0F;
                    break;
                case WEST:
                    rotateY = 90.0F;
                    break;
                case EAST:
                    rotateY = -90.0F;
                    break;
                case SOUTH:
                default:
                    break;
            }
            GlStateManager.rotate(-rotateY, 0, 1, 0);

            // 调整位置使其贴在墙上
            GlStateManager.translate(0, 0.3125, 0.5125);

            // 翻转到正面
            GlStateManager.scale(1, -1, 1);
            GlStateManager.translate(-0.99375, 0, -0.99375);

            // 重置渲染状态
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            // 设置基础缩放
            GlStateManager.scale(0.1625, 0.1625, 1);
            GlStateManager.translate(0.0, -5.9375, 0.0);
            GlStateManager.scale(1, -1, 1);
            GlStateManager.translate(6.25, 0, 0.0);
            // 基础字体缩放
            float baseScale = fontSizeMultiplier * 0.25f;

            // 渲染中文文本（强制居中）
            if (te.getChineseText() != null && !te.getChineseText().isEmpty()) {
                renderCenteredChineseText(te, baseScale, fontCN, r, g, b);
            }

            // 渲染英文文本（强制居中，使用更小的字体）
            if (te.getEnglishText() != null && !te.getEnglishText().isEmpty()) {
                renderCenteredEnglishText(te, baseScale, fontCN, fontSize, r, g, b);
            }

        } catch (Exception e) {
            // 静默处理异常
        } finally {
            // 安全的状态恢复
            try {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            } catch (Exception e) {
                // 静默处理恢复异常
            }
        }
    }

    /**
     * 渲染居中的中文文本
     */
    private void renderCenteredChineseText(TileEntityStationNameBoard te, float scale, String fontCN, int r, int g, int b) {
        String chineseText = te.getChineseText();
        float chineseTextWidth = getChineseTextWidth(chineseText, fontCN);

        // 计算中文文本缩放

        // 设置中文文本Y坐标
        float chineseY = getChineseYPosition(fontCN);

        // 计算强制居中的X偏移（居中于方块中心）
        float chineseX = -chineseTextWidth / 2.0F;

        // 字体特定微调
        if (Objects.equals(fontCN, "cn_1") || Objects.equals(fontCN, "cn_2")) {
            chineseX -= 1.5f; // 保持原有的字体特定偏移
        }

        GlStateManager.pushMatrix();
        try {
            GlStateManager.disableCull();
            GlStateManager.scale(scale, scale, 1);

            // 根据字体选择对应的渲染器
            if (Objects.equals(fontCN, "cn_1")) {
                ClientProxy.chineseFontRenderer_1.drawString(chineseText, chineseX, chineseY, r, g, b);
            } else if (Objects.equals(fontCN, "cn_2")) {
                ClientProxy.chineseFontRenderer_2.drawString(chineseText, chineseX, chineseY, r, g, b);
            } else {
                ClientProxy.chineseFontRenderer.drawString(chineseText, chineseX, chineseY, r, g, b);
            }
        } finally {
            GlStateManager.popMatrix();
        }
    }

    /**
     * 渲染居中的英文文本
     */
    private void renderCenteredEnglishText(TileEntityStationNameBoard te, float baseScale, String fontCN, float fontSize, int r, int g, int b) {
        String englishText = te.getEnglishText();
        String chineseText = te.getChineseText();
        float chineseTextWidth = getChineseTextWidth(chineseText, fontCN);
        float englishTextWidth = getEnglishTextWidth(englishText);

        // 英文使用更小的缩放
        float englishScale = baseScale * 0.375f;

        // 计算英文文本Y坐标（在中文文本下方）
        float englishY = getEnglishYPosition(fontCN, fontSize);

        // 英文文本水平居中于碰撞箱的中心点（与中文文本使用同一个中心点）
        float englishX = -englishTextWidth / 2.0F;

        GlStateManager.pushMatrix();
        try {
            GlStateManager.disableCull();
            GlStateManager.scale(englishScale, englishScale, 1);

            ClientProxy.englishFontRenderer.drawString(englishText, englishX, englishY, r, g, b);
        } finally {
            GlStateManager.popMatrix();
        }
    }

    /**
     * 获取中文文本宽度（考虑字体类型）
     */
    private float getChineseTextWidth(String text, String fontCN) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        if (Objects.equals(fontCN, "cn_1")) {
            return ClientProxy.chineseFontRenderer_1.getStringWidth(text);
        } else if (Objects.equals(fontCN, "cn_2")) {
            return ClientProxy.chineseFontRenderer_2.getStringWidth(text);
        } else {
            return ClientProxy.chineseFontRenderer.getStringWidth(text);
        }
    }

    /**
     * 获取英文文本宽度
     */
    private float getEnglishTextWidth(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        return ClientProxy.englishFontRenderer.getStringWidth(text);
    }

    /**
     * 获取中文文本Y坐标
     */
    private float getChineseYPosition(String fontCN) {
        if (Objects.equals(fontCN, "default")) {
            return 5.0f;
        } else if (Objects.equals(fontCN, "cn_1") || Objects.equals(fontCN, "cn_2")) {
            return 3.0f;
        } else if (fontCN == null) {
            return 10.0f;
        }
        return 5.0f; // 默认值
    }

    /**
     * 获取英文文本Y坐标
     */
    private float getEnglishYPosition(String fontCN, float fontSize) {
        float baseY = -42.5f; // 基础Y坐标

        // 根据中文字体类型调整Y坐标
        if (Objects.equals(fontCN, "default")) {
            baseY -= 14;
        } else if (Objects.equals(fontCN, "cn_1") || Objects.equals(fontCN, "cn_2")) {
            baseY -= 8;
        }

        // 根据字体大小调整Y坐标
        if (fontSize == 2) {
            baseY -= 8;
        } else if (fontSize == 3) {
            baseY -= 20;
        }

        // 添加垂直间距，让英文文本位于中文文本下方
        baseY -= 30; // 调整这个值来控制垂直间距

        return baseY;
    }

    @Override
    public boolean isGlobalRenderer(TileEntityStationNameBoard te) {
        return true;
    }
}