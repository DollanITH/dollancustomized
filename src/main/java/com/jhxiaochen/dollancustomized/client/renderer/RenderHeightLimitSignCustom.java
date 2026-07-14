package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.client.font.CustomTTFFontRenderer;
import com.jhxiaochen.dollancustomized.common.road.BlockHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderHeightLimitSignCustom extends TileEntitySpecialRenderer<TileEntityHeightLimitSignCustom> {

    @Override
    public void render(TileEntityHeightLimitSignCustom te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 基本验证
        if (te.heightValue == null || te.heightValue.isEmpty()) {
            return;
        }

        // 区块加载检查
        if (te.getWorld() == null || !te.getWorld().isBlockLoaded(te.getPos(), false)) {
            return;
        }

        IBlockState state = te.getWorld().getBlockState(te.getPos());
        if (!state.getProperties().containsKey(BlockHeightLimitSignCustom.FACING)) {
            return;
        }

        EnumFacing facing = state.getValue(BlockHeightLimitSignCustom.FACING);
        CustomTTFFontRenderer fontRenderer = ClientProxy.customFontRenderer;
        if (fontRenderer == null) {
            return;
        }

        // 解析输入：拆分为整数部分、小数点和小数部分
        String[] parts = parseHeightValue(te.heightValue);
        if (parts == null || parts.length == 0) {
            return;
        }

        // 获取字体原始宽高
        float textHeight = fontRenderer.FONT_HEIGHT > 0 ? fontRenderer.FONT_HEIGHT : 8.0F;
        float fontSize = 2.8F; // 基础字体大小
        float decimalFontSize = 1.8F; // 小数点字体大小（缩小）

        // 计算各部分的宽度
        float integerWidth = fontRenderer.getStringWidth(parts[0]);
        float decimalWidth = fontRenderer.getStringWidth(parts[1]);
        float decimalPartWidth = fontRenderer.getStringWidth(parts[2]);
        float unitWidth = fontRenderer.getStringWidth("m");

        // 总宽度（整数 + 小数点 + 小数 + 单位）
        float totalWidth = integerWidth + decimalWidth + decimalPartWidth + unitWidth;

        // 计算缩放比例（根据总宽度自适应）
        float scaleByWidth = totalWidth > 0 ? fontSize / totalWidth : 1.0F;
        float scaleByHeight = textHeight > 0 ? fontSize / textHeight : 1.0F;
        float baseScale = Math.min(scaleByWidth, scaleByHeight);

        // 小数点单独缩放
        float decimalScale = baseScale * (decimalFontSize / fontSize);

        // 各部分渲染宽度
        float integerRenderWidth = integerWidth * baseScale;
        float decimalRenderWidth = decimalWidth * decimalScale;
        float decimalPartRenderWidth = decimalPartWidth * baseScale;
        float unitRenderWidth = unitWidth * baseScale;

        // 整数部分渲染宽度
        float integerRenderedWidth = integerRenderWidth;
        // 小数点渲染宽度
        float decimalRenderedWidth = decimalRenderWidth;
        // 小数部分渲染宽度
        float decimalPartRenderedWidth = decimalPartRenderWidth;
        // "m"单位渲染宽度
        float unitRenderedWidth = unitRenderWidth;

        // 计算起始偏移（使整体居中）
        float totalRenderedWidth = integerRenderedWidth + decimalRenderedWidth + decimalPartRenderedWidth + unitRenderedWidth;
        float startX = -totalRenderedWidth / 2.0F;

        // 使用基于位置的微小深度偏移
        float microDepthOffset = getMicroDepthOffset(te.getPos());

        GlStateManager.pushMatrix();

        try {
            // 设定文字垂直位置（路牌中心）
            float topY = 0.002F;

            // 使用原始坐标，添加微小深度偏移
            GlStateManager.translate((float) x + 0.5F, (float) y + topY + 0.002F + microDepthOffset, (float) z + 0.5F);

            // 旋转逻辑
            float rotateY = 0.0F;
            switch (facing) {
                case SOUTH: rotateY = 180.0F; break;
                case WEST: rotateY = 90.0F; break;
                case EAST: rotateY = -90.0F; break;
                case NORTH: default: break;
            }
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);

            // 平移到起始位置
            GlStateManager.translate(startX, 0.0F, 0.0F);

            // 应用整体缩放
            GlStateManager.scale(baseScale, baseScale, baseScale);

            // 渲染整数部分
            float currentX = 0;
            fontRenderer.drawWhiteString(parts[0], currentX, 0, false);
            currentX += integerRenderedWidth;

            // 渲染小数点（使用小缩放）
            GlStateManager.pushMatrix();
            GlStateManager.scale(decimalScale, decimalScale, decimalScale);
            fontRenderer.drawWhiteString(".", 0, 0, false);
            GlStateManager.popMatrix();
            currentX += decimalRenderedWidth;

            // 渲染小数部分
            fontRenderer.drawWhiteString(parts[2], currentX, 0, false);
            currentX += decimalPartRenderedWidth;

            // 渲染"m"单位
            fontRenderer.drawWhiteString("m", currentX, 0, false);

        } catch (Exception e) {
            // 静默处理异常
        } finally {
            // 安全的状态恢复
            try {
                restoreCorrectCulling();
                GlStateManager.popMatrix();
            } catch (Exception e) {
                // 静默处理恢复异常
            }
        }
    }

    /**
     * 解析高度值，拆分为整数、小数点、小数部分
     */
    private String[] parseHeightValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 移除可能的空格
        value = value.trim();

        // 查找小数点位置
        int dotIndex = value.indexOf('.');

        if (dotIndex == -1) {
            // 没有小数点，只有整数部分
            return new String[]{value, ".", "0"};
        }

        if (dotIndex == value.length() - 1) {
            // 小数点是最后一个字符（如 "3."）
            String integerPart = value.substring(0, dotIndex);
            return new String[]{integerPart, ".", ""};
        }

        // 拆分为整数部分、小数点、小数部分
        String integerPart = value.substring(0, dotIndex);
        String decimalPart = value.substring(dotIndex + 1);

        return new String[]{integerPart, ".", decimalPart};
    }

    /**
     * 获取微小的深度偏移
     */
    private float getMicroDepthOffset(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int hash = Math.abs((x * 31 + y) * 31 + z);
        float offset = (hash % 10) / 100000.0f + 0.0001f;

        return Math.max(0.0001f, Math.min(offset, 0.0002f));
    }

    /**
     * 设置正确的面剔除方向，剔除朝向下的面
     */
    private void setupCorrectCulling() {
        // 确保纹理和光照设置正确
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();

        // 使用多边形偏移解决Z-fighting
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(-1.0F, -10.0F);

        // 深度测试设置
        GlStateManager.enableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);

        // 混合设置
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
        );

        // 确保颜色设置正确
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 禁用雾气
        GlStateManager.disableFog();

        // 修正面剔除方向
        // 对于躺着的文字，需要剔除正面来剔除朝向下的面
        GlStateManager.enableCull();
        GL11.glCullFace(GL11.GL_FRONT);
    }

    /**
     * 恢复正确的面剔除状态
     */
    private void restoreCorrectCulling() {
        // 恢复多边形偏移
        GlStateManager.disablePolygonOffset();

        // 恢复深度设置
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();

        // 恢复其他状态
        GlStateManager.enableFog();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();

        // 恢复默认的背面剔除
        GL11.glCullFace(GL11.GL_BACK);

        // 重置颜色
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isGlobalRenderer(TileEntityHeightLimitSignCustom te) {
        return true;
    }
}