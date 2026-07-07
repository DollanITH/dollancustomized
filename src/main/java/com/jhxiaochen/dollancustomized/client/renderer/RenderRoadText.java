package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.client.font.CustomTTFFontRenderer;
import com.jhxiaochen.dollancustomized.common.road.BlockRoadText;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class RenderRoadText extends TileEntitySpecialRenderer<TileEntityRoadText> {
    @Override
    public void render(TileEntityRoadText te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te.text == null || te.text.isEmpty()) {
            return;
        }
        IBlockState state = te.getWorld().getBlockState(te.getPos());
        if (!state.getProperties().containsKey(BlockRoadText.FACING)) {
            return;
        }
        EnumFacing facing = state.getValue(BlockRoadText.FACING);
        CustomTTFFontRenderer fontRenderer = ClientProxy.customFontRenderer;
        if (fontRenderer == null) {
            return;
        }

        // 获取字体原始宽高（此时已经是除以缩放倍率后的视觉像素宽高）
        float textWidth = fontRenderer.getStringWidth(te.text);
        if (textWidth <= 0) {
            textWidth = 10;
        }
        float textHeight = fontRenderer.FONT_HEIGHT > 0 ? fontRenderer.FONT_HEIGHT : 8.0F;

        // 设定文字在游戏中期望的最终宽度
        float desiredWidth = 2.2F;

        // 【新增：设定文字在游戏中期望的最终高度】
        // 注意：这个值需要根据游戏中"="的实际视觉高度进行微调，这里暂设为 2.0F
        float desiredHeight = 3.2F;

        // 【修改：同时考虑宽度和高度，取较小的缩放比例，防止偏窄或偏高的字符过大】
        float scaleByWidth = desiredWidth / textWidth;
        float scaleByHeight = desiredHeight / textHeight;
        float externalScale = Math.min(scaleByWidth, scaleByHeight);


        // 计算渲染后的实际尺寸
        float renderWidth = textWidth * externalScale;
        float renderHeight = textHeight * externalScale;

        // 计算将字体几何中心平移到原点的偏移量
        float offsetX = -renderWidth / 2.0F;
        float offsetY = -renderHeight / 2.0F;

        GlStateManager.pushMatrix();

        float topY;
        if (state.getValue(BlockRoadText.TYPE) == BlockRoadText.MarkType.SLAB) {
            topY = -0.498F;
        } else {
            topY = 0.002F;
        }

        GlStateManager.translate((float) x + 0.5F, (float) y + topY, (float) z + 0.5F);

        float rotateY = 0.0F;
        switch (facing) {
            case SOUTH: rotateY = 180.0F; break;
            case WEST:  rotateY = 90.0F;  break;
            case EAST:  rotateY = -90.0F; break;
            case NORTH: default: break;
        }

        GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(offsetX, offsetY, 0.0F);

        // 应用缩放
        GlStateManager.scale(externalScale, externalScale, externalScale);

        // GL 状态设置
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 调用渲染
        if (te.colorMode == 1) {
            fontRenderer.drawYellowString(te.text, 0, 0, false);
        } else {
            fontRenderer.drawWhiteString(te.text, 0, 0, false);
        }

        // 恢复 GL 状态
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
