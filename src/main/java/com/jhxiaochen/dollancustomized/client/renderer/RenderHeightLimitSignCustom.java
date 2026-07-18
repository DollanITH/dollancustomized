package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.client.font.SignFontRenderer;
import com.jhxiaochen.dollancustomized.common.road.BlockHeightLimitSignCustom;
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

            GlStateManager.pushMatrix();
            GlStateManager.enableCull();
            GL11.glCullFace(GL11.GL_FRONT);
            // 移动到立方体中心并调整字体大小（更精确的位置）
            //GlStateManager.translate(0.0F, -1.875F, 0.0F);
            GlStateManager.scale(0.02125F, -0.02125F, 0.02125F);  // 调整字体大小

            this.renderText(te, signFontRenderer);
            GL11.glCullFace(GL11.GL_BACK);
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
        float startX = 0.0F;
        // 计算字符串总宽度并居中
        float totalWidth = fontRenderer.getStringWidth(integerPart) * 0.016F;  // 转换为Minecraft单位
        if (decimal != 0) {
            GlStateManager.translate(-3F, 2.75F, 0.0F);
            GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
        }
        if (decimal == 0) startX = -totalWidth / 2;
        else startX = -totalWidth / 4 - 2.5F;  // 居中起始位置

        // 渲染整数部分（带轮廓）
        fontRenderer.drawStringWithOutline(integerPart, startX, 0, 0xFF000000);
        // 渲染小数部分
        if (decimal != 0) {
            float currentX = startX + fontRenderer.getStringWidth(integerPart) * 0.75F;  // 转换为Minecraft单位
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            // 渲染小数点（带轮廓）
            fontRenderer.drawStringWithOutline(".", currentX, 0, 0xFF000000);
            GlStateManager.translate(-1.0F, -1.625F, 0.0F);

            // 渲染小数部分
            float decimalX = currentX + (fontRenderer.getStringWidth(".") * 1.2F) + 1.875F;
            GlStateManager.scale(1.5F, 1.5F, 1.5F);
            fontRenderer.drawStringWithOutline(String.valueOf(decimal), decimalX, 0, 0xFF000000);
            GlStateManager.translate(0.125F, -1.625F, 0.0F);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityHeightLimitSignCustom te) {
        return true;
    }
}
