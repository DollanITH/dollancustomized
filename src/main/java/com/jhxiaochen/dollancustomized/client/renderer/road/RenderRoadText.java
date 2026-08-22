package com.jhxiaochen.dollancustomized.client.renderer.road;

import com.jhxiaochen.dollancustomized.client.font.RoadFontRenderer;
import com.jhxiaochen.dollancustomized.common.block.road.BlockRoadText;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityRoadText;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderRoadText extends TileEntitySpecialRenderer<TileEntityRoadText> {

    @Override
    public void render(TileEntityRoadText te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 基本验证
        if (te.text == null || te.text.isEmpty()) {
            return;
        }

        // 区块加载检查
        if (te.getWorld() == null || !te.getWorld().isBlockLoaded(te.getPos(), false)) {
            return;
        }

        IBlockState state = te.getWorld().getBlockState(te.getPos());
        if (!state.getProperties().containsKey(BlockRoadText.FACING)) {
            return;
        }

        EnumFacing facing = state.getValue(BlockRoadText.FACING);
        RoadFontRenderer fontRenderer = ClientProxy.roadFontRenderer;
        if (fontRenderer == null) {
            return;
        }

        // 获取字体原始宽高
        float textWidth = fontRenderer.getStringWidth(te.text);
        if (textWidth <= 0) textWidth = 10;
        float textHeight = fontRenderer.FONT_HEIGHT > 0 ? fontRenderer.FONT_HEIGHT : 8.0F;

        // 设定文字在游戏中期望的最终宽高
        float desiredHeight = (float) (47.0 / 16.0);
        float desiredWidth = (float) (39.0 / 16.0);

        // 计算宽度和高度的独立缩放比例（分别控制宽高比）
        float scaleX = desiredWidth / textWidth;
        float scaleY = desiredHeight / textHeight;

        // 计算渲染后的实际尺寸（应用不同的缩放）
        float renderWidth = textWidth * scaleX;
        float renderHeight = textHeight * scaleY;

        // 计算将字体几何中心平移到原点的偏移量
        float offsetX = -renderWidth / 2.0F;
        float offsetY = -renderHeight / 2.0F;

        // 使用基于位置的微小深度偏移
        float microDepthOffset = getMicroDepthOffset(te.getPos());

        GlStateManager.pushMatrix();

        try {
            // 设定文字垂直位置
            float topY;
            if (state.getValue(BlockRoadText.TYPE) == BlockRoadText.MarkType.SLAB) {
                topY = -0.498F;
            } else {
                topY = 0.002F;
            }

            // 使用原始坐标，添加微小深度偏移
            GlStateManager.translate((float) x + 0.5F, (float) y + topY + 0.002F + microDepthOffset, (float) z + 0.5F);

            // 旋转逻辑
            float rotateY = 0.0F;
            switch (facing) {
                case SOUTH: rotateY = 180.0F; break;
                case WEST: rotateY = 90.0F; break;
                case EAST: rotateY = -90.0F; break;
                case NORTH: default: break;
            }
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);

            // 平移到字体中心
            GlStateManager.translate(offsetX, offsetY, 0.0F);

            // 应用扁平缩放（宽高比不同）
            GlStateManager.scale(scaleX, scaleY, 1.0F);

            // 【关键修复】修正面剔除方向，剔除朝向下的面
            setupCorrectCulling();

            // 渲染文字
            if (te.colorMode == 1) {
                fontRenderer.drawYellowString(te.text, 0, 0, true);
            } else {
                fontRenderer.drawWhiteString(te.text, 0, 0, true);
            }

        } catch (Exception e) {
            // 静默处理异常
        } finally {
            // 安全的状态恢复
            try {
                restoreCorrectCulling();
                GlStateManager.popMatrix();
            } catch (Exception e) {
                // 静默处理恢复异常
            }
        }
    }

    /**
     * 获取微小的深度偏移
     */
    private float getMicroDepthOffset(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int hash = Math.abs((x * 31 + y) * 31 + z);
        float offset = (hash % 10) / 100000.0f + 0.0001f;

        return Math.max(0.0001f, Math.min(offset, 0.0002f));
    }

    /**
     * 【关键修复】修正面剔除方向，剔除朝向下的面
     */
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

    /**
     * 【关键修复】恢复正确的面剔除状态
     */
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

    @Override
    public boolean isGlobalRenderer(TileEntityRoadText te) {
        return true;
    }
}
