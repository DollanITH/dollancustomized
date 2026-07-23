package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JacarandaPetalParticle extends Particle {
    private static final ResourceLocation PETAL_TEXTURES = new ResourceLocation("dollancustomized", "particles/jacaranda");
    private final TextureAtlasSprite particleSprite;
    private final float particleSize;

    public JacarandaPetalParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        // 随机选择花瓣大小
        this.particleSize = 0.1F + rand.nextFloat() * 0.05F;
        this.particleScale = this.particleSize;

        // 设置生命周期
        this.particleMaxAge = (int)(40.0D + rand.nextDouble() * 20.0D);

        // 设置重力 - 让花瓣向下飘落
        this.particleGravity = 0.003F;

        // 设置颜色 - 蓝花楹的紫色
        this.particleRed = 0.85F;
        this.particleGreen = 0.75F;
        this.particleBlue = 0.95F;

        // 设置初始速度 - 主要向下，略有飘动
        this.motionX = (rand.nextDouble() - 0.5D) * 0.02D;
        this.motionY = -0.04D - rand.nextDouble() * 0.02D; // 向下运动
        this.motionZ = (rand.nextDouble() - 0.5D) * 0.02D;

        // 获取纹理 - 使用第一个花瓣纹理
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        ResourceLocation petalTexture = new ResourceLocation("dollancustomized", "particles/jacaranda_0");
        this.particleSprite = texturemap.getAtlasSprite(petalTexture.toString());

        // 确保粒子有透明度
        this.particleAlpha = 0.9F;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                              float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        // 计算粒子位置
        float f = this.particleScale;
        float f1 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
        float f2 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
        float f3 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);

        // 设置粒子大小
        float f4 = 0.1F * f;

        // 获取纹理尺寸
        float uMin = this.particleSprite.getMinU();
        float uMax = this.particleSprite.getMaxU();
        float vMin = this.particleSprite.getMinV();
        float vMax = this.particleSprite.getMaxV();

        // 获取光照
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        // 渲染粒子 - 使用标准的花瓣形状
        buffer.pos(f1 - rotationX * f4 - rotationXY * f4, f2 - rotationZ * f4, f3 - rotationYZ * f4 - rotationXZ * f4)
              .tex(uMax, vMax)
              .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
              .lightmap(j, k)
              .endVertex();

        buffer.pos(f1 - rotationX * f4 + rotationXY * f4, f2 + rotationZ * f4, f3 - rotationYZ * f4 + rotationXZ * f4)
              .tex(uMax, vMin)
              .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
              .lightmap(j, k)
              .endVertex();

        buffer.pos(f1 + rotationX * f4 + rotationXY * f4, f2 + rotationZ * f4, f3 + rotationYZ * f4 + rotationXZ * f4)
              .tex(uMin, vMin)
              .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
              .lightmap(j, k)
              .endVertex();

        buffer.pos(f1 + rotationX * f4 - rotationXY * f4, f2 - rotationZ * f4, f3 + rotationYZ * f4 - rotationXZ * f4)
              .tex(uMin, vMax)
              .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
              .lightmap(j, k)
              .endVertex();
    }

    @Override
    public int getFXLayer() {
        return 1; // 使用自定义纹理层
    }

    @Override
    public void onUpdate() {
        // 更新粒子位置
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        // 应用重力
        this.motionY -= (double)this.particleGravity * 0.5D;

        // 添加一些随机飘动
        this.motionX += (rand.nextDouble() - 0.5D) * 0.001D;
        this.motionZ += (rand.nextDouble() - 0.5D) * 0.001D;

        // 空气阻力
        this.motionX *= 0.99D;
        this.motionY *= 0.99D;
        this.motionZ *= 0.99D;

        // 更新位置
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        // 减少透明度
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }

        // 根据年龄调整透明度
        this.particleAlpha = 1.0F - (float)this.particleAge / (float)this.particleMaxAge;
    }
}