package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.client.font.CustomTTFFontRenderer;
import com.jhxiaochen.dollancustomized.common.road.BlockRoadText;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class RenderRoadText extends TileEntitySpecialRenderer<TileEntityRoadText> {

    @Override
    public void render(TileEntityRoadText te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te == null || te.text == null || te.text.isEmpty()) {
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

        // 获取字体宽度，并限制最小值，防止单字计算缩放时出Bug
        float textWidth = fontRenderer.getStringWidth(te.text);
        if (textWidth <= 0) {
            textWidth = 10;
        }

        // 计算自适应缩放
        float scale = 4.0F / textWidth;

        // 字体像素高度
        float textHeight = fontRenderer.FONT_HEIGHT > 0 ? fontRenderer.FONT_HEIGHT : 8.0F;

        // 渲染后的实际尺寸
        float renderWidth = textWidth * scale;
        float renderHeight = textHeight * scale;

        // 计算将字体自身几何中心平移到原点的偏移量
        float offsetX = -renderWidth / 2.0F;
        float offsetY = -renderHeight / 2.0F;

        GlStateManager.pushMatrix();
        BlockPos pos = te.getPos();

        // 根据方块类型调整文字高度 - 紧贴下方方块
        float topY; // 从方块底部开始渲染
        if (state.getValue(BlockRoadText.TYPE) == BlockRoadText.MarkType.SLAB) {
            topY = -0.5F; // 下半砖时紧贴地面
        } else {
            topY = 0.0F; // 全砖时从方块底部开始
        }

        // 首先平移到【碰撞箱的中心点】(因为方块本身是从 -0.25 到 1.25，中心即 x+0.5, z+0.5)
        GlStateManager.translate((float) x - 0.5F, (float) y + topY, (float) z - 0.5F);
        // 旋转方向处理
        float rotateY = 0.0F;
        switch (facing) {
            case SOUTH:
                rotateY = 180.0F;
                break;
            case NORTH:
                GlStateManager.translate((float) x - 0.5F, (float) 0, (float) z - 0.5F);
                rotateY = 0.0F;
                break;
            case EAST:
                GlStateManager.translate((float) x - 0.5F, (float) 0, (float) z - 0.5F);
                rotateY = 90.0F;
                break;
            case WEST:
                GlStateManager.translate((float) x - 0.5F, (float) 0, (float) z - 0.5F);
                rotateY = -90.0F;
                break;
            default:
                break;
        }

        // 绕Y轴旋转朝向
        GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
        // 绕X轴旋转90度，让文字平铺在地面
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);

        // 应用居中偏移（因为此时已经绕中心旋转完毕，直接偏移即可保证严丝合缝）
        GlStateManager.translate(offsetX, offsetY, 0.0F);

        // 应用缩放
        GlStateManager.scale(scale, scale, scale);

        // GL 状态设置
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 绘制文字
        fontRenderer.drawString(te.text, 0, 0, 0xFFFFFFFF, false);

        // 恢复 GL 状态
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
