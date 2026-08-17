package com.jhxiaochen.dollancustomized.client.renderer.road;

import com.jhxiaochen.dollancustomized.client.font.SignFontRenderer;
import com.jhxiaochen.dollancustomized.common.block.road.BlockHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

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
            return;
        }

        
        // 保存OpenGL状态
        GlStateManager.pushMatrix();

        try {
            // 使用原始坐标
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

            // 旋转处理（让文字始终朝向玩家）
            float rotateY = 0.0F;

            switch (facing) {
                case NORTH: rotateY = 180.0F; break;   // 朝北时，文字朝南（玩家）
                case SOUTH: rotateY = 0.0F; break;     // 朝南时，文字朝北（玩家）
                case WEST: rotateY = 270.0F; break;     // 朝西时，文字朝东（玩家）
                case EAST: rotateY = 90.0F; break;    // 朝东时，文字朝西（玩家）
                default: break;
            }
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);

            // 移动到牌子表面（基于模型中的立方体位置）
            // 立方体从 [7,7,6.9] 到 [9,9,8.9]，中心在 [8,8,7.9]
            GlStateManager.translate(-0.2F, 0.25F, 0.0725F);

            // 应用缩放（参考原版木牌的缩放）
            float scale = 0.6666667F;
            GlStateManager.scale(scale, scale, scale);

            // 使用整数常量避免枚举错误
            //GlStateManager.translate(0.0F, -1.875F, 0.0F);
            GlStateManager.scale(0.02125F, -0.02125F, 0.02125F);  // 调整字体大小
            setupCorrectCulling();
            this.renderText(te, signFontRenderer);
            restoreCorrectCulling();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 只需要一个 popMatrix，避免栈下溢
            GlStateManager.popMatrix();
        }
    }

    /**
     * 渲染文字
     */
    private void renderText(TileEntityHeightLimitSignCustom te, SignFontRenderer fontRenderer) {
        String integerPart = String.valueOf(te.getIntegerPart());
        int decimal = te.getDecimalPart();

        float startX = 0.0F;
        float totalWidth = fontRenderer.getStringWidth(integerPart) * 0.008F;
        float scale = 1.0F; // 默认缩放比例（无小数时保持原大小）

        if (decimal != 0) {
            GlStateManager.translate(-3F, 1.625F, 0.0F);
            GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);

            // 计算整数和小数部分的当前宽度（缩放后）
            float integerWidth = fontRenderer.getStringWidth(integerPart) * 0.008F * 0.9375F;
            float decimalWidth = fontRenderer.getStringWidth(String.valueOf(decimal)) * 0.008F * 0.9375F;

            // 动态缩放整数部分，使其宽度与小数部分一致（限制缩放范围：0.8~1.2）
            scale = decimalWidth / integerWidth;
            scale = Math.max(0.9375F, Math.min(1.0F, scale)); // 防止过度放大
            GlStateManager.scale(scale, scale, scale);

            // 重新计算总宽度（缩放后的）
            totalWidth = fontRenderer.getStringWidth(integerPart) * 0.008F * 1.1625F * scale;
        }

        // 调整 startX，使整数部分更远离小数点（增加负向偏移）
        if (decimal == 0) {
            startX = -totalWidth / 2;
        } else {
            // 计算整数和小数部分的最终宽度（缩放后）
            float integerWidth = fontRenderer.getStringWidth(integerPart) * 0.008F * 0.9375F * scale;
            float decimalWidth = fontRenderer.getStringWidth(String.valueOf(decimal)) * 0.008F * 0.9375F;
            // 增加偏移量，使整数部分更远离小数点
            float extraOffset = (integerWidth - decimalWidth) / 2; // 原 /8，现改为 /2（增加偏移）
            startX = -totalWidth / 4 - 4.5F - extraOffset; // 原 2.0F，现改为 4.5F（增加负向偏移）
        }

        // 渲染整数部分（带轮廓）
        fontRenderer.drawStringWithOutline(integerPart, startX, 0, 0xFF000000);

        // 渲染小数部分
        if (decimal != 0) {
            // 调整 translate，使小数点更靠近整数部分（可选，根据效果调整）
            GlStateManager.translate(2.0F, 2.8F, 0.0F); // 原 2.0F，现保持不变
            float currentX = -totalWidth / 4 - 2.8125F + (fontRenderer.getStringWidth(".") / 2.0F) + 22.0F;
            currentX = Math.max(currentX, 0.1F); // 限制小数点坐标不小于 0

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            fontRenderer.drawStringWithOutline(".", currentX, 22, 0xFF000000);
            GlStateManager.translate(1.8125F, 2.625F, 0.0F);

            // **关键优化：调整小数部分的缩放比例，使其大小与整数部分一致**
            float decimalScale = 1.0F / scale * 1.375F; // 小数部分的缩放比例（与整数部分相反）
            decimalScale = Math.max(0.9375F, Math.max(1.1625F, decimalScale)); // 限制缩放范围（0.8~1.2）
            GlStateManager.scale(decimalScale, decimalScale, decimalScale);

            float decimalX = currentX / 2.0F + (fontRenderer.getStringWidth(".") * 1.25F);
            decimalX = Math.max(decimalX, 0.05F); // 限制小数部分坐标不小于 0
            float startY = 6;
            if (String.valueOf(decimal).equals("1")) {
                startY = 7;
            }
            fontRenderer.drawStringWithOutline(String.valueOf(decimal), decimalX, startY, 0xFF000000);
            GlStateManager.translate(1.375F, 0.0F, 0.0F);
            GlStateManager.popMatrix();
        }
    }
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

        // 【关键修复】修正面剔除方向
        // 对于躺着的文字，需要剔除正面来剔除朝向下的面
        GlStateManager.enableCull();
        GL11.glCullFace(GL11.GL_FRONT);
    }


    @Override
    public boolean isGlobalRenderer(TileEntityHeightLimitSignCustom te) {
        return true;
    }
}
