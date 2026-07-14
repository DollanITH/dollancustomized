package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.client.font.CustomTTFFontRenderer;
import com.jhxiaochen.dollancustomized.common.road.BlockHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class TestRenderHeightLimitSignCustom extends TileEntitySpecialRenderer<TileEntityHeightLimitSignCustom> {

    @Override
    public void render(TileEntityHeightLimitSignCustom te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 基本验证
        if (te == null || te.heightValue == null || te.heightValue.isEmpty()) {
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

        // 构造显示文本：添加"m"单位
        String displayText = te.heightValue + "m";

        // 获取字体原始宽高
        float textWidth = fontRenderer.getStringWidth(displayText);
        float textHeight = fontRenderer.FONT_HEIGHT > 0 ? fontRenderer.FONT_HEIGHT : 8.0F;

        // 设定文字在游戏中期望的最终宽高
        float desiredWidth = 2.2F;
        float desiredHeight = 3.2F;

        // 计算缩放比例
        float scaleByWidth = desiredWidth / textWidth;
        float scaleByHeight = desiredHeight / textHeight;
        float externalScale = Math.min(scaleByWidth, scaleByHeight);

        // 计算渲染后的实际尺寸
        float renderWidth = textWidth * externalScale;
        float renderHeight = textHeight * externalScale;

        // 计算偏移量
        float offsetX = -renderWidth / 2.0F;
        float offsetY = -renderHeight / 2.0F;

        GlStateManager.pushMatrix();

        try {
            // 文字垂直位置
            float topY = 0.002F;

            // 移动到方块中心
            GlStateManager.translate((float) x + 0.5F, (float) y + topY, (float) z + 0.5F);

            // 旋转到面向玩家
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);

            // 平移到文字中心
            GlStateManager.translate(offsetX, offsetY, 0.0F);

            // 应用缩放
            GlStateManager.scale(externalScale, externalScale, externalScale);

            // 渲染文字（不使用面剔除，看看是否能显示）
            fontRenderer.drawWhiteString(displayText, 0, 0, false);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityHeightLimitSignCustom te) {
        return true;
    }
}
