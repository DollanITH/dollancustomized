package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.client.font.SignFontRenderer;
import com.jhxiaochen.dollancustomized.common.road.BlockHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class RenderHeightLimitSignCustom extends TileEntitySpecialRenderer<TileEntityHeightLimitSignCustom> {

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

        EnumFacing facing = state.getValue(BlockHeightLimitSignCustom.FACING);
        SignFontRenderer signFontRenderer = ClientProxy.signFontRenderer;
        if (signFontRenderer == null) {
            System.out.println("[RENDER ERROR] SignFontRenderer is null!");
            return;
        }

        System.out.println("[RENDER] Rendering sign: " + te.getIntegerPart() + "." + te.getDecimalPart() + " at " + te.getPos());

        // 保存OpenGL状态
        GlStateManager.pushMatrix();

        try {
            // 使用原始坐标
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

            // 旋转处理（参考原版木牌）
            float rotateY = 0.0F;
            int meta = 0; // 需要从state获取metadata

            switch (facing) {
                case SOUTH: rotateY = 180.0F; break;
                case NORTH: rotateY = 0.0F; break;
                case EAST: rotateY = -90.0F; break;
                case WEST: rotateY = 90.0F; break;
                default: break;
            }
            GlStateManager.rotate(-rotateY, 0.0F, 1.0F, 0.0F);

            // 竖直旋转 - 悬挂牌需要调整
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);

            // 应用缩放（参考原版木牌的缩放）
            float scale = 0.6666667F;
            GlStateManager.scale(scale, scale, scale);

            // 渲染文字（参考原版木牌的文字渲染方式）
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            // 文字位置和缩放（参考原版）
            GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
            GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
            GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);

            // 禁用深度掩码（参考原版）
            GlStateManager.depthMask(false);

            // 渲染文字
            this.renderText(te, signFontRenderer);

            // 恢复深度掩码（参考原版）
            GlStateManager.depthMask(true);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GlStateManager.popMatrix();
        }
    }

    /**
     * 渲染文字
     */
    private void renderText(TileEntityHeightLimitSignCustom te, SignFontRenderer fontRenderer) {
        String integerPart = String.valueOf(te.getIntegerPart());
        int decimal = te.getDecimalPart();

        // 渲染整数部分
        fontRenderer.drawWhiteString(integerPart, 0, 0);

        // 渲染小数部分
        if (decimal != 0) {
            float currentX = fontRenderer.getStringWidth(integerPart) + 0.5F;

            // 渲染小数点
            fontRenderer.drawWhiteString(".", currentX, 0);
            currentX += fontRenderer.getStringWidth(".") + 0.5F;

            // 渲染小数部分
            fontRenderer.drawWhiteString(String.valueOf(decimal), currentX, 0);
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityHeightLimitSignCustom te) {
        return true;
    }
}
