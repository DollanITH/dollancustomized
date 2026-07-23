package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FixedJacarandaParticle extends Particle {
    // private static final ResourceLocation JACARANDA_TEXTURE = new ResourceLocation("dollancustomized", "particles/jacaranda");
    private final int textureIndex;

    public FixedJacarandaParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        // 随机选择一个纹理索引
        this.textureIndex = rand.nextInt(12);

        // 设置粒子属性
        this.particleScale = 0.1F + rand.nextFloat() * 0.05F;
        this.particleMaxAge = (int)(60.0D / (rand.nextDouble() * 0.8D + 0.2D));
        this.particleGravity = 0.01F; // 增加重力让粒子更快下落

        // 设置粒子颜色（蓝花楹的紫色色调）
        this.particleRed = 0.8F + rand.nextFloat() * 0.2F;
        this.particleGreen = 0.7F + rand.nextFloat() * 0.2F;
        this.particleBlue = 0.9F + rand.nextFloat() * 0.1F;

        // 设置粒子纹理 - 使用纹理注册器确保正确加载
        ResourceLocation specificTexture = ParticleTextureRegister.getTexture(this.textureIndex);
        this.particleTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(specificTexture.toString());
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f4 = 0.1F * this.particleScale;

        // 简单的UV映射
        float uMin = 0.0F;
        float uMax = 1.0F;
        float vMin = 0.0F;
        float vMax = 1.0F;

        // 对于多纹理粒子，可以根据textureIndex计算UV
        if (textureIndex > 0) {
            int texX = textureIndex % 4;
            int texY = textureIndex / 4;
            uMin = texX * 0.25F;
            uMax = uMin + 0.25F;
            vMin = texY * (1.0F / 3.0F);
            vMax = vMin + (1.0F / 3.0F);
        }

        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        // 不调用buffer.begin()，让ParticleManager管理
        buffer.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex(uMax, vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex(uMax, vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex(uMin, vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex(uMin, vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
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