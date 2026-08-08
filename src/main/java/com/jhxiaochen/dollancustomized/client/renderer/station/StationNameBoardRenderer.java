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
        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);

        // 获取方块的朝向
        EnumFacing facing = te.getWorld( ).getBlockState(te.getPos( )).getValue(BlockStationNameBoard.FACING);
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
        GlStateManager.translate(0, 0.3125, 0.5125);
        GL11.glCullFace(GL11.GL_FRONT);
        // 翻转到正面
        GlStateManager.scale(1, -1, 1);
        GlStateManager.translate(-0.99375, 0, -0.99375);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 设置渲染深度
        GlStateManager.scale(0.1625, 0.1625, 1);
        GlStateManager.translate(0.0,-5.9375,0.0);
        GlStateManager.scale(1, -1, 1);
        // 获取字体大小和颜色
        float fontSizeMultiplier = te.getFontSizeMultiplier();
        int r = te.colorRed;
        int g = te.colorGreen;
        int b = te.colorBlue;

        // 使用新的字体渲染器
        float scale = fontSizeMultiplier * 0.25f; // 根据字体大小调整缩放
        GlStateManager.translate(-ClientProxy.chineseFontRenderer.getStringWidth(te.getChineseText()) / 25f, 0.0, 0);
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

            // 强制居中：计算英文文本相对于整个面板的居中位置
            // 中文文本已经在第75行进行了居中偏移
            float chineseTextWidth = ClientProxy.chineseFontRenderer.getStringWidth(te.getChineseText());
            float englishTextWidth = ClientProxy.englishFontRenderer.getStringWidth(te.getEnglishText());

            // 计算偏移量：使英文文本相对于面板中心居中
            // 中文文本的偏移是 -chineseTextWidth / 10.0（第75行）
            // 英文文本需要：(总面板宽度 - 英文文本宽度) / 2 - 当前缩放基准
            float centerXOffset = chineseTextWidth / 20.0f + englishTextWidth / 20.0f; // 除以2是因为之前的缩放

            // 如果英文文本比中文文本长，需要额外偏移
            if (englishTextWidth > chineseTextWidth / 0.5f) {
                float difference = (englishTextWidth - chineseTextWidth) / 10.0F;
                centerXOffset -= difference;
            }

            // 英文文本在中文文本下方，偏移字体高度的1.2倍
            ClientProxy.englishFontRenderer.drawString(te.getEnglishText(),
            centerXOffset,
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