package com.jhxiaochen.dollancustomized.client.renderer.tileentity;

import com.jhxiaochen.dollancustomized.common.JacarandaSign;
import com.jhxiaochen.dollancustomized.common.JacarandaStandingSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityJacarandaSignRenderer extends TileEntitySpecialRenderer<TileEntityJacarandaSign> {

    // 蓝花楹木告示牌的木纹纹理
    private static final ResourceLocation JACARANDA_SIGN_TEXTURE = new ResourceLocation("dollancustomized:textures/entity/jacaranda_sign.png");
    private final ModelSign model = new ModelSign();

    @Override
    public void render(TileEntityJacarandaSign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        BlockPos blockpos = te.getPos();
        JacarandaSign block = (JacarandaSign) te.getBlockType();

        GlStateManager.pushMatrix();

        // 设置渲染缩放（与原版告示牌保持一致）
        float scale = 0.6666667F;

        if (block == JacarandaStandingSign.Registry.jacaranda_standing_sign) {
            // 处理竖直告示牌
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

            // 根据方块朝向旋转
            int meta = te.getBlockMetadata();
            float rotation = (float)(meta * 360) / 16.0F;
            GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);

            this.model.signStick.showModel = true;
        } else {
            // 处理墙挂告示牌
            int meta = te.getBlockMetadata();
            float rotation = 0.0F;

            if (meta == 2) {
                rotation = 180.0F;
            } else if (meta == 4) {
                rotation = 90.0F;
            } else if (meta == 5) {
                rotation = -90.0F;
            }

            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
            GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            this.model.signStick.showModel = false;
        }

        // 处理破坏阶段的纹理
        if (destroyStage >= 0) {
            this.bindTexture(net.minecraft.client.renderer.tileentity.TileEntitySignRenderer.DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890); // GL_TEXTURE_MATRIX
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888); // GL_MODELVIEW
        } else {
            // 绑定蓝花楹木告示牌纹理
            this.bindTexture(JACARANDA_SIGN_TEXTURE);
        }

        // 渲染告示牌板子
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, -scale, -scale);
        this.model.renderSign();
        GlStateManager.popMatrix();

        // 渲染文本
        this.renderSignText(te, scale, destroyStage, alpha);

        // 恢复渲染状态
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();

        // 处理破坏阶段的矩阵恢复
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890); // GL_TEXTURE_MATRIX
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888); // GL_MODELVIEW
        }
    }

    /**
     * 渲染告示牌文本
     */
    private void renderSignText(TileEntityJacarandaSign te, float scale, int destroyStage, float alpha) {
        // 获取字体渲染器
        net.minecraft.client.gui.FontRenderer fontrenderer = this.getFontRenderer();

        // 检查是否在编辑模式
        boolean isEditing = te.lineBeingEdited != -1;

        // 设置文本缩放
        float textScale = 0.010416667F;
        GlStateManager.pushMatrix();

        // 移动到文本渲染位置（使用已经变换好的坐标系）
        GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
        GlStateManager.scale(textScale, -textScale, textScale);
        GlStateManager.glNormal3f(0.0F, 0.0F, -textScale);
        GlStateManager.depthMask(false);

        // 渲染每一行文本
        for (int line = 0; line < te.signText.length; line++) {
            if (te.signText[line] != null) {
                String text = te.signText[line].getFormattedText();

                // 如果在编辑模式，添加编辑标记
                if (isEditing && line == te.lineBeingEdited) {
                    text = "> " + text + " <";
                }

                // 计算文本宽度
                int textWidth = fontrenderer.getStringWidth(text);

                // 居中显示文本
                int posX = -textWidth / 2;
                int posY = line * 10 - te.signText.length * 5;

                // 绘制文本
                fontrenderer.drawString(text, posX, posY, 0);
            }
        }

        // 恢复渲染状态
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    /**
     * 获取蓝花楹木告示牌纹理
     */
    public static ResourceLocation getJacarandaSignTexture() {
        return JACARANDA_SIGN_TEXTURE;
    }
}