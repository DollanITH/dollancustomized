package com.jhxiaochen.dollancustomized.client.renderer.station;

import com.jhxiaochen.dollancustomized.common.block.metro.BlockStationNameBoard;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityStationNameBoard;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class StationNameBoardRenderer extends TileEntitySpecialRenderer<TileEntityStationNameBoard> {

    @Override
    public void render(TileEntityStationNameBoard te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te == null || (te.getChineseText() == null || te.getChineseText().isEmpty()) &&
            (te.getEnglishText() == null || te.getEnglishText().isEmpty())) {
            return;
        }

        GlStateManager.pushMatrix();

        // 重置所有渲染状态
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        // 使用简单的混合模式
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // 移动到方块位置
        GlStateManager.translate(x, y, z);

        // 获取方块的朝向
        if (te.getWorld() != null) {
            EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockStationNameBoard.FACING);

            // 根据朝向进行旋转
            switch (facing) {
                case NORTH:
                    GlStateManager.rotate(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.rotate(0, 0, 1, 0);
                    break;
                case WEST:
                    GlStateManager.rotate(-90, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.rotate(90, 0, 1, 0);
                    break;
            }
        }

        GL11.glCullFace(GL11.GL_FRONT);
        // 翻转到正面
        GlStateManager.scale(1, -1, 1);
        GlStateManager.translate(-0.99375, 0, -0.99375);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 设置渲染深度
        GlStateManager.scale(0.1625, 0.1625, 0.1625);
        GlStateManager.translate(0.0,-6.9375,0.0);
        GlStateManager.scale(1, -1, 1);
        // 获取字体大小和颜色
        float fontSizeMultiplier = te.getFontSizeMultiplier();
        int r = te.colorRed;
        int g = te.colorGreen;
        int b = te.colorBlue;

        // 使用新的字体渲染器
        float scale = fontSizeMultiplier * 0.25f; // 根据字体大小调整缩放

        // 渲染中文文本
        if (te.getChineseText() != null && !te.getChineseText().isEmpty()) {
            GlStateManager.pushMatrix();

            // 禁用深度写入，启用混合
            GlStateManager.scale(scale, scale, 1);
            ClientProxy.chineseFontRenderer.drawString(te.getChineseText(), 0, 10, r,g,b);

            GlStateManager.popMatrix();
        }

        // 渲染英文文本（在中文文本下方偏移位置）
        if (te.getEnglishText() != null && !te.getEnglishText().isEmpty()) {
            float engscale = scale * 0.375f;
            GlStateManager.pushMatrix();

            // 禁用深度写入，启用混合
            GlStateManager.scale(engscale, engscale, 1);
            // 英文文本在中文文本下方，偏移字体高度的1.2倍
            ClientProxy.englishFontRenderer.drawString(te.getEnglishText(),
            (float)ClientProxy.englishFontRenderer.getStringWidth(te.getEnglishText( )) / 10,
            -engscale * 1000,
            r,g,b);

            GlStateManager.popMatrix();
        }
        GL11.glCullFace(GL11.GL_BACK);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}