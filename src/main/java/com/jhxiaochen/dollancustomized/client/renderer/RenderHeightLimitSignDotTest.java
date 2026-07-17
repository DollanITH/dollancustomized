package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.common.road.BlockHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;

/**
 * 点测试版限高牌渲染器 - 只绘制一个红点
 */
public class RenderHeightLimitSignDotTest extends TileEntitySpecialRenderer<TileEntityHeightLimitSignCustom> {

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

        System.out.println("[DOT TEST] Rendering at position: " + te.getPos());

        // 保存OpenGL状态
        GlStateManager.pushMatrix();

        try {
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

            EnumFacing facing = state.getValue(BlockHeightLimitSignCustom.FACING);
            switch (facing) {
                case SOUTH: GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F); break;
                case NORTH: break;
                case EAST: GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F); break;
                case WEST: GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F); break;
                default: break;
            }

            // 绘制一个红色的小方块
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.0625F);
            GlStateManager.scale(0.125F, 0.125F, 0.125F);
            drawSolidCube();
            GlStateManager.popMatrix();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GlStateManager.popMatrix();
        }
    }

    /**
     * 绘制一个实心立方体
     */
    private void drawSolidCube() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, DefaultVertexFormats.POSITION);

        // 前面
        buffer.pos(-0.5, -0.5,  0.5).endVertex();
        buffer.pos( 0.5, -0.5,  0.5).endVertex();
        buffer.pos( 0.5,  0.5,  0.5).endVertex();
        buffer.pos(-0.5,  0.5,  0.5).endVertex();

        tessellator.draw();
    }

    @Override
    public boolean isGlobalRenderer(TileEntityHeightLimitSignCustom te) {
        return true;
    }
}