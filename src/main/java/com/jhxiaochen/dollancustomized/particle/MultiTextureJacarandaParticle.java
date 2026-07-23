package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class MultiTextureJacarandaParticle extends Particle {
    private static final ResourceLocation[] TEXTURE_LOCATIONS = new ResourceLocation[12];
    private static TextureAtlasSprite[] textureSprites = new TextureAtlasSprite[12];

    static {
        // 初始化12个纹理位置
        for (int i = 0; i < 12; i++) {
            TEXTURE_LOCATIONS[i] = new ResourceLocation("dollancustomized", "textures/particles/jacaranda_" + i);
        }

        // 预加载纹理
        preloadTextures();
    }

    private final int textureIndex;

    public MultiTextureJacarandaParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        // 随机选择一个纹理
        this.textureIndex = rand.nextInt(12);

        // 设置粒子属性
        this.particleScale = 0.1F + rand.nextFloat() * 0.05F;
        this.particleMaxAge = (int)(60.0D / (rand.nextDouble() * 0.8D + 0.2D));
        this.particleGravity = 0.001F;

        // 设置粒子颜色（蓝花楹的紫色色调）
        this.particleRed = 0.8F + rand.nextFloat() * 0.2F;
        this.particleGreen = 0.7F + rand.nextFloat() * 0.2F;
        this.particleBlue = 0.9F + rand.nextFloat() * 0.1F;

        // 设置粒子纹理
        this.particleTexture = getParticleTexture(textureIndex);
    }

    /**
     * 预加载所有纹理
     */
    private static void preloadTextures() {
        TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
        for (int i = 0; i < 12; i++) {
            textureSprites[i] = textureMap.getAtlasSprite(TEXTURE_LOCATIONS[i].toString());
        }
    }

    /**
     * 获取指定索引的纹理
     */
    private static TextureAtlasSprite getParticleTexture(int index) {
        if (index >= 0 && index < textureSprites.length && textureSprites[index] != null) {
            return textureSprites[index];
        }
        // 如果没有加载成功，返回第一个纹理
        return textureSprites[0];
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Tessellator tessellator = Tessellator.getInstance();

        float f4 = 0.1F * this.particleScale;

        // 将12个纹理排列成4列3行的网格
        int texX = this.textureIndex % 4;  // 列索引 (0-3)
        int texY = this.textureIndex / 4;  // 行索引 (0-2)

        // UV坐标计算
        float uMin = texX * 0.25F;          // 4列，每列0.25
        float uMax = uMin + 0.25F;          // 每列宽度
        float vMin = texY * (1.0F / 3.0F);  // 3行
        float vMax = vMin + (1.0F / 3.0F);  // 每行高度

        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();

        // 绘制粒子
        // Buffer begin is called by ParticleManager, don't call it here
        buffer.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex(uMax, vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex(uMax, vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex(uMin, vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex(uMin, vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.draw();

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    @Override
    public int getFXLayer() {
        return 1; // 使用自定义纹理
    }

    @Override
    public Particle multiplyVelocity(float multiplier) {
        this.motionX *= (double)multiplier;
        this.motionY = ((this.motionY - 0.10000000149011612D) * (double)multiplier) + 0.10000000149011612D;
        this.motionZ *= (double)multiplier;
        return this;
    }
}