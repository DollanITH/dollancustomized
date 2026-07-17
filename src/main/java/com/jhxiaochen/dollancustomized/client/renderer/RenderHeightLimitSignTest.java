package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.common.road.BlockHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

/**
 * 简单测试版限高牌渲染器 - 只使用坐标系统测试
 */
public class RenderHeightLimitSignTest extends TileEntitySpecialRenderer<TileEntityHeightLimitSignCustom> {

    @Override
    public void render(TileEntityHeightLimitSignCustom te, double x, double y, double z,
                      float partialTicks, int destroyStage, float alpha) {
        // 基本验证
        if (te.getIntegerPart() == 0 && te.getDecimalPart() == 0) {
            return;
        }

        IBlockState state = te.getWorld().getBlockState(te.getPos());
        if (!state.getProperties().containsKey(BlockHeightLimitSignCustom.FACING)) {
            return;
        }

        // 使用原版FontRenderer进行测试
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        if (fontRenderer == null) {
            return;
        }

        // 保存OpenGL状态
        GlStateManager.pushMatrix();

        try {
            // === 方法1: 参考原版木牌的方式 ===
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

            EnumFacing facing = state.getValue(BlockHeightLimitSignCustom.FACING);
            float rotateY = 0.0F;

            switch (facing) {
                case SOUTH: rotateY = 180.0F; break;
                case NORTH: rotateY = 0.0F; break;
                case EAST: rotateY = -90.0F; break;
                case WEST: rotateY = 90.0F; break;
                default: break;
            }
            GlStateManager.rotate(-rotateY, 0.0F, 1.0F, 0.0F);

            // 竖直旋转和偏移（参考原版木牌）
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);

            // 缩放
            float scale = 0.6666667F;
            GlStateManager.scale(scale, scale, scale);

            // 渲染文字（参考原版木牌）
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
            GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
            GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);
            GlStateManager.depthMask(false);

            // 绘制文字
            String text = String.valueOf(te.getIntegerPart());
            if (te.getDecimalPart() > 0) {
                text += "." + te.getDecimalPart();
            }

            // 原版木牌使用居中绘制
            fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0, 0xFFFFFFFF);

            System.out.println("[TEST] Method 1 - Text: " + text + " at pos: " + te.getPos());

            GlStateManager.depthMask(true);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();

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