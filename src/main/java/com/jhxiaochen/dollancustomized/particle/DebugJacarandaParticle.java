package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DebugJacarandaParticle extends Particle {
    private static final ResourceLocation DEBUG_TEXTURE = new ResourceLocation("dollancustomized", "particles/jacaranda_0");
    private final int textureIndex;

    public DebugJacarandaParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        this.textureIndex = 0; // 使用第一个纹理进行调试

        // 设置粒子属性
        this.particleScale = 0.2F; // 增大一些便于观察
        this.particleMaxAge = 100;
        this.particleGravity = 0.0F;

        // 设置粒子颜色（亮红色以便于识别）
        this.particleRed = 1.0F;
        this.particleGreen = 0.0F;
        this.particleBlue = 0.0F;
        this.particleAlpha = 1.0F;

        // 设置粒子纹理
        this.particleTexture = getDebugTexture();
    }

    private TextureAtlasSprite getDebugTexture() {
        try {
            TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
            TextureAtlasSprite sprite = textureMap.getAtlasSprite(DEBUG_TEXTURE.toString());

            // 输出调试信息
            System.out.println("DebugJacarandaParticle: Loading texture " + DEBUG_TEXTURE);
            System.out.println("DebugJacarandaParticle: Sprite = " + sprite);

            return sprite;
        } catch (Exception e) {
            System.out.println("DebugJacarandaParticle: Error loading texture - " + e.getMessage());
            return null;
        }
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        if (this.particleTexture == null) {
            System.out.println("DebugJacarandaParticle: particleTexture is null!");
            return;
        }

        Tessellator tessellator = Tessellator.getInstance();
        buffer = tessellator.getBuffer();

        float f4 = 0.1F * this.particleScale;

        // 简单的UV映射
        float f = 0.0F;
        float f1 = 1.0F;
        float f2 = 0.0F;
        float f3 = 1.0F;

        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        // Buffer begin is called by ParticleManager, don't call it here
        buffer.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.draw();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public Particle multiplyVelocity(float multiplier) {
        this.motionX *= (double)multiplier;
        this.motionY = ((this.motionY - 0.10000000149011612D) * (double)multiplier) + 0.10000000149011612D;
        this.motionZ *= (double)multiplier;
        return this;
    }
}