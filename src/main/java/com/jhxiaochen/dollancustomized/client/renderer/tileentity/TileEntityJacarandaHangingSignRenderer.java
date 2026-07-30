package com.jhxiaochen.dollancustomized.client.renderer.tileentity;

import com.jhxiaochen.dollancustomized.client.model.HangingSignModel;
import com.jhxiaochen.dollancustomized.client.model.HangingSignTopFullModel;
import com.jhxiaochen.dollancustomized.common.JacarandaHangingSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaHangingSign;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
    private HangingSignModel        model;
    private HangingSignTopFullModel model_full;

    // 物品渲染器
    private ItemRenderer itemRenderer;

    private static final int[] TOTAL_SLOTS = new int[]{0, 1};
    private static final float MODEL_RENDER_SCALE = 1.0F;
    private static final float TEXT_RENDER_SCALE = 0.9F;
    private static final float TEXT_OFFSET_Y = -0.32F;
    private static final float TEXT_OFFSET_Z = 0.073F;

    @Override
    public void render(TileEntityJacarandaHangingSign tileEntity, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        if (tileEntity == null) return;

        Block block = tileEntity.getBlockType();

        GlStateManager.pushMatrix();

        // 获取方块类型和旋转角度
        boolean isSideModel = block instanceof JacarandaHangingSign;
        int rotation = 0;

        if (isSideModel) {
            int k = tileEntity.getBlockMetadata();
            if (k == 2) rotation = 180;
            else if (k == 4) rotation = 90;
            else if (k == 5) rotation = -90;
        } else {
            rotation = (tileEntity.getBlockMetadata() * 360) / 16;
        }

        // 平移到中心点
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        GlStateManager.translate(0, 0.125, 0);
        // 旋转
        GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);
        // 垂直平移
        //GlStateManager.translate(0.0F, -0.3125F, 0.0F);

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

        // 渲染模型
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        // 设置模型缩放
        GlStateManager.scale(MODEL_RENDER_SCALE, -MODEL_RENDER_SCALE, -MODEL_RENDER_SCALE);

        if (model == null) {
            model = new HangingSignModel();
        }
        if (model_full == null) {
            model_full = new HangingSignTopFullModel();
        }

        if (isSideModel)
        {
            this.model.renderSign();
            this.model.renderStick();
        }
        else
        {
            this.model_full.renderSign();
            this.model_full.renderConnector();
        }

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
        if (destroyStage >= 0) return;

        FontRenderer fontrenderer = this.getFontRenderer();
        int textSides = tileEntity.getTextSides();

        // 渲染双面文本
        if (textSides != -1) {
            for (int slot : TOTAL_SLOTS) {
                if (!shouldRenderSide(textSides, slot)) continue;

                ITextComponent[] lines = tileEntity.getTextForSlot(slot);

                GlStateManager.pushMatrix();

                // Slot 1 需要额外的旋转
                if (slot == 1) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                // 应用文本变换
                applyTextTransforms(lines);

                // 绘制文本
                for (int i = 0; i < lines.length; i++) {
                    if (lines[i] == null) continue;

                    String text = formatText(lines[i], fontrenderer);
                    int yPos = i * 10 - lines.length * 5;
                    fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, yPos, 0);
                }

                GlStateManager.popMatrix();
            }
        }
    }

    private boolean shouldRenderSide(int textSides, int slot) {
        return textSides == 2 || textSides == slot;
    }

    private void applyTextTransforms(ITextComponent[] lines) {
        // 应用文本缩放
        GlStateManager.scale(TEXT_RENDER_SCALE, -TEXT_RENDER_SCALE, TEXT_RENDER_SCALE);
        GlStateManager.glNormal3f(0.0F, 0.0F, -TEXT_RENDER_SCALE);

        // 应用文本偏移
        GlStateManager.translate(0.0F, TEXT_OFFSET_Y, TEXT_OFFSET_Z);
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

            if (slot == 1) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

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
            itemRenderer.renderItem(null, stack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GUI);

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
