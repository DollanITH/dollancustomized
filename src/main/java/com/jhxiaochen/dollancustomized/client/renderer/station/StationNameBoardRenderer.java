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

import java.util.Objects;

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
        String fontCN = te.getFontFamilyChinese();
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

            // 计算中文文本在碰撞箱中心的X坐标
            float chineseTextWidth = ClientProxy.chineseFontRenderer.getStringWidth(te.getChineseText());
            float chineseX = -chineseTextWidth * scale / 2.0f + 1.5f; // 添加0.5像素微调
            if (Objects.equals(fontCN, "default")) {
                ClientProxy.chineseFontRenderer.drawString(te.getChineseText(), chineseX, 10, r, g, b);
            }
            if (Objects.equals(fontCN, "cn_1")) {
                ClientProxy.chineseFontRenderer_1.drawString(te.getChineseText(), chineseX - 1.5f, 3, r, g, b);
            }
            if (Objects.equals(fontCN, "cn_2")) {
                ClientProxy.chineseFontRenderer_2.drawString(te.getChineseText(), chineseX - 1.5f, 2, r, g, b);
            }
            if (fontCN == null) {
                ClientProxy.chineseFontRenderer.drawString(te.getChineseText(), chineseX, 10, r, g, b);
            }
            GlStateManager.popMatrix();
        }

        // 渲染英文文本（在中文文本下方偏移位置）
        if (te.getEnglishText() != null && !te.getEnglishText().isEmpty()) {
            float engscale = scale * 0.375f;
            GlStateManager.pushMatrix();

            // 禁用深度写入，启用混合
            GlStateManager.scale(engscale, engscale, 1);

            // 计算英文文本在碰撞箱中心的X坐标
            float englishTextWidth = ClientProxy.englishFontRenderer.getStringWidth(te.getEnglishText());
            float englishX = -englishTextWidth * engscale / 2.0f + 1.5f; // 添加0.5像素微调

            // 英文文本在中文文本下方，偏移字体高度的1.2倍
            ClientProxy.englishFontRenderer.drawString(te.getEnglishText(),
            englishX,
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