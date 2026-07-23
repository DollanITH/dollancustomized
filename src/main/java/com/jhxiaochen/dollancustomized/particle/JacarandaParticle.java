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
public class JacarandaParticle extends Particle {
    private static final ResourceLocation JACARANDA_TEXTURE = new ResourceLocation("dollancustomized", "particles/jacaranda");
    private final int textureIndex;

    public JacarandaParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        // 随机选择一个纹理索引
        this.textureIndex = rand.nextInt(12);

        // 设置粒子属性
        this.particleScale = 0.1F + rand.nextFloat() * 0.05F;
        this.particleMaxAge = (int)(60.0D / (rand.nextDouble() * 0.8D + 0.2D));
        this.particleGravity = 0.001F;

        // 设置粒子颜色（蓝花楹的紫色色调）
        this.particleRed = 0.8F + rand.nextFloat() * 0.2F;
        this.particleGreen = 0.7F + rand.nextFloat() * 0.2F;
        this.particleBlue = 0.9F + rand.nextFloat() * 0.1F;

        // 设置粒子纹理 - 使用第一个纹理作为默认
        ResourceLocation defaultTexture = new ResourceLocation("dollancustomized", "particles/jacaranda_0");
        this.particleTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(defaultTexture.toString());
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f4 = 0.1F * this.particleScale;

        // 将12个纹理排列成4列3行的网格
        int texX = this.textureIndex % 4;  // 列索引 (0-3)
        int texY = this.textureIndex / 4;  // 行索引 (0-2)

        // UV坐标计算
        float f = texX * 0.25F;          // 4列，每列0.25
        float f1 = f + 0.25F;            // 每列宽度
        float f2 = texY * (1.0F / 3.0F); // 3行
        float f3 = f2 + (1.0F / 3.0F);   // 每行高度

        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        // 直接使用已经开始的buffer，不要重新begin
        buffer.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
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