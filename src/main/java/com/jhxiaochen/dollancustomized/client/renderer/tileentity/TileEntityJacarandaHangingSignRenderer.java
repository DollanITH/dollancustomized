package com.jhxiaochen.dollancustomized.client.renderer.tileentity;

import com.jhxiaochen.dollancustomized.client.model.JacarandaHangingSignModel;
import com.jhxiaochen.dollancustomized.common.JacarandaHangingSign;
import com.jhxiaochen.dollancustomized.common.JacarandaHangingSignAttached;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaHangingSign;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class TileEntityJacarandaHangingSignRenderer extends TileEntitySpecialRenderer<TileEntityJacarandaHangingSign> {

    // 蓝花楹木告示牌材质
    private static final ResourceLocation JACARANDA_SIGN_TEXTURE = new ResourceLocation("dollancustomized", "textures/entity/jacaranda_hanging_sign.png");

    // 模型实例
    private JacarandaHangingSignModel jacarandaSignModel;

    // 物品渲染器
    private ItemRenderer itemRenderer;

    private static final int[] TOTAL_SLOTS = new int[]{0, 1};

    // 文本偏移
    private static final float TEXT_OFFSET_Y = -0.32F;
    private static final float TEXT_OFFSET_Z = 0.073F;

    // 渲染缩放
    private static final float MODEL_RENDER_SCALE = 1.0F;
    private static final float TEXT_SCALE = 0.009216667F;
    @Override
    public void render(TileEntityJacarandaHangingSign tileEntity, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        if (tileEntity == null) return;

        Block block = tileEntity.getBlockType();
        boolean isSideModel = block instanceof JacarandaHangingSign;
        boolean isSideModelWall = block instanceof JacarandaHangingSign && ((JacarandaHangingSign) block).isInWall();

        GlStateManager.pushMatrix();

        // 计算旋转角度
        int rotation = 0;
        if (isSideModel) {
            int metadata = tileEntity.getBlockMetadata();
            if (metadata == 2) rotation = 180;
            else if (metadata == 4) rotation = 90;
            else if (metadata == 5) rotation = -90;
        } else {
            // 顶部悬挂式使用 16 方向
            rotation = (tileEntity.getBlockMetadata() * 360) / 16;
        }

        // 平移到中心点并应用变换
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.9375F, (float) z + 0.5F);
        GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, -0.3125F, 0.0F);

        // 初始化模型
        if (jacarandaSignModel == null) {
            jacarandaSignModel = new JacarandaHangingSignModel();
        }

        // 更新可见部分（1.12.2中没有BlockState，直接判断metadata）
        // 顶部悬挂式使用metadata值来判断
        boolean attached = false;
        if (!isSideModel) {
            // 1.12.2中没有ATTACHED属性，根据metadata判断
            // 假设偶数是attached=true，奇数是attached=false，或者根据具体需求调整
            attached = !(tileEntity.getBlockMetadata() == 0 || tileEntity.getBlockMetadata() == 4 || tileEntity.getBlockMetadata() == 8 || tileEntity.getBlockMetadata() == 12);
        }
        if (isSideModelWall || block instanceof JacarandaHangingSignAttached) {
            jacarandaSignModel.evaluateVisibleParts(isSideModel, attached);
        } else {
            jacarandaSignModel.evaluateVisibleParts(false, false);
        }

        // 设置模型缩放
        GlStateManager.scale(MODEL_RENDER_SCALE, -MODEL_RENDER_SCALE, -MODEL_RENDER_SCALE);

        // 绑定材质
        if (destroyStage >= 0) {
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(JACARANDA_SIGN_TEXTURE);
        }

        // 渲染告示牌模型
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        // 直接调用render方法，模型内部会根据visible属性决定显示什么
        jacarandaSignModel.render();

        GlStateManager.popMatrix();

        // 渲染文本
        renderSignText(tileEntity, destroyStage);

        // 渲染物品
        renderSignItem(tileEntity, isSideModel, rotation);

        // 恢复状态
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

        GlStateManager.popMatrix();
    }

    private void renderSignText(TileEntityJacarandaHangingSign tileEntity, int destroyStage) {
        FontRenderer fontrenderer = this.getFontRenderer();
        int textSides = tileEntity.getTextSides();

        // 渲染双面文本
        if (textSides != -1) {
            for (int slot : TOTAL_SLOTS) {
                if (!shouldRenderSide(textSides, slot)) continue;

                // 获取渲染位置（会自动缓存）
                com.jhxiaochen.dollancustomized.data.AxisPosition renderPos = tileEntity.getTextRenderPosition(slot);
                ITextComponent[] lines = tileEntity.getTextForSlot(slot);

                // 获取当前正在编辑的行
                int lineBeingEdited = tileEntity.lineBeingEdited;
                boolean isHighlighted = (lineBeingEdited != -1);

                GlStateManager.pushMatrix();

                // 应用渲染位置
                GlStateManager.translate((float) renderPos.getX() - 0.3125, 0.2995, (float) renderPos.getZ() - 0.3125);
                // 应用文本变换
                GlStateManager.scale(-TEXT_SCALE, TEXT_SCALE, -TEXT_SCALE);

                // 绘制文本（包含高亮背景和光标）
                drawText(lines, lineBeingEdited, fontrenderer);
                GlStateManager.popMatrix();
            }
        }
    }

    private boolean shouldRenderSide(int textSides, int slot) {
        return textSides == 2 || textSides == slot;
    }

    private void renderHighlightedBackground() {
        // 绘制高亮背景
        int highlightWidth = 100;
        int highlightHeight = 40;

        GlStateManager.color(0.2F, 0.6F, 1.0F, 0.3F); // 蓝色高亮
        drawTexturedQuadFit(-highlightWidth/2, -highlightHeight/2, highlightWidth, highlightHeight);

        // 设置文字颜色（黑色）
        GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
    }

    private void drawText(ITextComponent[] lines, int lineBeingEdited, FontRenderer fr) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) continue;

            String text = this.formatText(lines[i], fr);
            int yPos = i * 10 - lines.length * 5;

            // 如果是当前编辑的行，绘制高亮背景和光标
            if (i == lineBeingEdited) {
                int textWidth = fr.getStringWidth(text);
                int textCenterX = -textWidth / 2;

                // 绘制光标（白色闪烁）
                fr.drawString("|", textCenterX + textWidth, yPos, 0xFFFFFF);
            }

            // 绘制文字（黑色）
            fr.drawString(text, -fr.getStringWidth(text)/2, yPos, 0);

            // 重置颜色
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void drawTexturedQuadFit(double x, double y, double width, double height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0.0).endVertex();
        buffer.pos(x + width, y, 0.0).endVertex();
        buffer.pos(x + width, y + height, 0.0).endVertex();
        buffer.pos(x, y + height, 0.0).endVertex();
        tessellator.draw();
    }

    private String formatText(ITextComponent component, FontRenderer fr) {
        List<ITextComponent> split = GuiUtilRenderComponents.splitText(
            component, 90, fr, false, true
        );
        return split.isEmpty() ? "" : split.get(0).getFormattedText();
    }

    private void renderSignItem(TileEntityJacarandaHangingSign tileEntity, boolean isSideModel, int rotation) {
        if (tileEntity == null) return;

        // 初始化 ItemRenderer
        if (itemRenderer == null) {
            itemRenderer = new ItemRenderer(Minecraft.getMinecraft());
        }

        int hasDisplayedSides = tileEntity.getDisplayedSides();

        if (hasDisplayedSides == -1) return;

        // 渲染双面物品
        for (int slot : TOTAL_SLOTS) {
            if (!shouldRenderSide(hasDisplayedSides, slot)) continue;

            ItemStack stack = tileEntity.getDisplayItem(slot);
            if (stack.isEmpty()) continue;

            GlStateManager.pushMatrix();

            // 获取物品的渲染位置
            float[] renderPos = getItemRenderPosition(tileEntity, slot, isSideModel, rotation);
            GlStateManager.translate(renderPos[0], renderPos[1], renderPos[2]);

            // 渲染物品（使用标准物品渲染）
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            // 使用 RenderItem 渲染物品
            Minecraft.getMinecraft( ).getRenderItem( ).renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    private float[] getItemRenderPosition(TileEntityJacarandaHangingSign tileEntity, int slot, boolean isSideModel, int rotation) {
        // 根据槽位获取渲染位置
        float x = 0, y = 0, z = 0;

        if (slot == 0) {
            // 正面物品位置
            if (isSideModel) {
                // 正方向悬挂式
                if (rotation == 180 || rotation == -90) {
                    x = 0.1F;
                    z = 0.7F;
                    y = 0.55F;
                } else {
                    x = 0.1F;
                    z = 0.6F;
                    y = 0.45F;
                }
            } else {
                // 精确方向悬挂式
                if (rotation == 0 || rotation == 8) {
                    x = 0.15F;
                    z = 0.75F;
                    y = 0.55F;
                } else if (rotation == 180 || rotation == 12) {
                    x = 0.15F;
                    z = 0.6F;
                    y = 0.45F;
                } else if (rotation == 90 || rotation == 4) {
                    x = 0.2F;
                    z = 0.65F;
                    y = 0.5F;
                } else {
                    x = 0.1F;
                    z = 0.7F;
                    y = 0.55F;
                }
            }
        } else {
            // 背面物品位置
            if (isSideModel) {
                if (rotation == 180 || rotation == -90) {
                    x = 0.1F;
                    z = 0.6F;
                    y = 0.45F;
                } else {
                    x = 0.1F;
                    z = 0.7F;
                    y = 0.55F;
                }
            } else {
                if (rotation == 0 || rotation == 8) {
                    x = 0.15F;
                    z = 0.6F;
                    y = 0.45F;
                } else if (rotation == 180 || rotation == 12) {
                    x = 0.15F;
                    z = 0.75F;
                    y = 0.55F;
                } else if (rotation == 90 || rotation == 4) {
                    x = 0.1F;
                    z = 0.65F;
                    y = 0.5F;
                } else {
                    x = 0.2F;
                    z = 0.7F;
                    y = 0.55F;
                }
            }
        }

        return new float[]{x, y, z};
    }
}
