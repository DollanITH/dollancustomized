package com.jhxiaochen.dollancustomized.client.renderer.sign;

import com.jhxiaochen.dollancustomized.common.block.wood.DollanSign;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanStandingSign;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanWallSign;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class SignRenderer extends TileEntitySpecialRenderer<TileEntityDollanSign> {

    private static final double FIXED_Y = 0.8343;
    private static final double IN_WALL_FIXED_Y = 0.5218;
    private static final float TEXT_SCALE = 0.010416667F;
    private static final int[] TOTAL_SLOTS = new int[]{0, 1};

    // 蓝花楹木告示牌纹理
    private static ResourceLocation SIGN_TEXTURE;
    private static final float SIGN_SCALE = 0.6666667F;

    // 模型实例
    private static ModelSign signModel;

    @Override
    public void render(TileEntityDollanSign tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (tile == null) return;

        // 获取世界和方块状态
        World world = tile.getWorld();
        BlockPos blockpos = tile.getPos();
        if (world == null) return;

        IBlockState blockState = world.getBlockState(blockpos);
        Block block = blockState.getBlock();

        // 检查是否是我们的告示牌
        if (!(block instanceof DollanSign) && !(block instanceof DollanStandingSign)) {
            return;
        }

        // 检查是否为空气方块
        if (world.isAirBlock(blockpos)) {
            return;
        }

        if (block.getRegistryName().toString().contains("jacaranda")) {
             SIGN_TEXTURE = new ResourceLocation("dollancustomized", "textures/entity/jacaranda_sign.png");
        }
        if (block == WoodBlocks.mangrove_wall_sign || block == WoodBlocks.mangrove_standing_sign) {
             SIGN_TEXTURE = new ResourceLocation("dollancustomized", "textures/entity/mangrove_sign.png");
        }

        GlStateManager.pushMatrix();
        int textSides = tile.getTextSides();
        if (block instanceof DollanStandingSign) {
            // 竖直告示牌

            GlStateManager.translate((float) x + 0.5F,(float) y + 0.5F,(float) z + 0.5F);
            // 根据方块朝向旋转
            int meta = blockState.getValue(DollanStandingSign.ROTATION);
            float rotation = (float)(meta * 360) / 16.0F;
            GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);

            // 渲染告示牌板子
            this.getSignModel().signStick.showModel = true;
        } else if (block instanceof DollanWallSign) {

            // 墙上告示牌
            int meta = block.getMetaFromState(blockState);
            float rotation = 0.0F;

            if (meta == 2) rotation = 180.0F;  // SOUTH
            else if (meta == 4) rotation = 90.0F;   // EAST
            else if (meta == 5) rotation = -90.0F;  // WEST

            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);

            this.getSignModel().signStick.showModel = false;
        }

        // 渲染告示牌板子
        if (destroyStage >= 0) {
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.matrixMode(5890); // GL_TEXTURE_MATRIX
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888); // GL_MODELVIEW
        } else {
            // 绑定蓝花楹木告示牌纹理
            this.bindTexture(SIGN_TEXTURE);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(SIGN_SCALE, -SIGN_SCALE, -SIGN_SCALE);
        this.getSignModel().renderSign();
        GlStateManager.popMatrix();


        // 先渲染文本，避免矩阵状态影响
        if (textSides != -1) {
            for (int slot : TOTAL_SLOTS) {
                // 跳过不需要渲染的面
                if (!this.shouldRenderSide(textSides, slot)) continue;

                //tile.getTextRenderPosition(slot);

                // 判断是否高亮：原版使用 lineBeingEdited 来显示光标
                boolean isHighlighted = (tile.lineBeingEdited != -1);
                // 判断是否 slot 1（需要旋转180°）
                boolean isSlot1 = (slot == 1);
                this.renderText(tile, isSlot1, isHighlighted);
            }
        }

        GlStateManager.popMatrix();

        // 恢复纹理矩阵
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890); // GL_TEXTURE_MATRIX
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888); // GL_MODELVIEW
        }
    }

    // 获取或创建模型实例
    private ModelSign getSignModel() {
        if (signModel == null) {
            signModel = new ModelSign();
        }
        return signModel;
    }

    private boolean shouldRenderSide(int textSides, int slot) {
        return textSides == 2 || textSides == slot;
    }

    // 渲染（统一 slot 0 和 slot 1 的逻辑）
    private void renderText(TileEntityDollanSign tile, boolean isSlot1, boolean isHighlighted) {
        ITextComponent[] lines = tile.getTextForSlot(isSlot1 ? 1 : 0);

        GlStateManager.pushMatrix();

        this.applyTransforms(tile, isSlot1);

        this.drawText(lines, tile.lineBeingEdited);
        GlStateManager.popMatrix();

    }

    private void applyTransforms(TileEntityDollanSign tile, boolean isSlot1) {
        // slot 1 需要额外的 180° 旋转，y 1.0F
        if (isSlot1) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }

        GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
        GlStateManager.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);
    }

    private void setupRenderingState() {
        GlStateManager.glNormal3f(0, 0, -1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }

    private void drawText(ITextComponent[] lines, int lineBeingEdited) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) continue;

            String text = this.formatText(lines[i], fr);
            int yPos = i * 10 - lines.length * 5;

            // 如果是当前编辑的行，绘制高亮背景
            if (i == lineBeingEdited) {
                int textWidth = fr.getStringWidth(text);
                int textCenterX = -textWidth / 2;

                // 绘制光标（白色闪烁）
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                fr.drawString("|", textCenterX + textWidth, yPos, 0xFFFFFF);
            }

            // 绘制文字（黑色）
            fr.drawString(text, -fr.getStringWidth(text)/2, yPos, 0);
        }
    }

    private void renderHighlightedBackground() {
        // 绘制高亮背景
        int highlightWidth = 40;
        int highlightHeight = 40;

        GlStateManager.color(0.2F, 0.6F, 1.0F, 0.3F); // 蓝色高亮
        this.drawTexturedQuadFit(-highlightWidth/2, -highlightHeight/2, highlightWidth, highlightHeight);

        // 设置文字颜色（黑色）
        GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
    }

    private void renderGlowEffect(TileEntityDollanSign tile) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.enableLighting();

        // 添加呼吸灯效果
        float pulse = (float)(Math.sin(System.currentTimeMillis() * 0.002) + 1) * 0.1F;
        GlStateManager.color(0.8F, 0.4F, 1.0F, 0.1F + pulse); // 紫色呼吸效果

        IBlockState state = tile.getWorld().getBlockState(tile.getPos());
        if (state.getBlock() instanceof DollanStandingSign) {
            // 竖直告示牌的光晕
            this.renderGlowQuad(0.45, 0.7, 0.1, 0.2);
        } else if (state.getBlock() instanceof DollanWallSign) {
            // 墙上告示牌的光晕
            this.renderGlowQuad(0.4, 0.5, 0.05, 0.15);
        }

        GlStateManager.popMatrix();
    }

    private void renderGlowQuad(double x, double y, double width, double height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(x - width/2, y - height/2, 0.0).endVertex();
        buffer.pos(x + width/2, y - height/2, 0.0).endVertex();
        buffer.pos(x + width/2, y + height/2, 0.0).endVertex();
        buffer.pos(x - width/2, y + height/2, 0.0).endVertex();
        tessellator.draw();
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
        List<ITextComponent> split = net.minecraft.client.gui.GuiUtilRenderComponents.splitText(
            component, 90, fr, false, true
        );
        return split.isEmpty() ? "" : split.get(0).getFormattedText();
    }

}