package com.jhxiaochen.dollancustomized.particles;

import net.minecraft.client.particle.ParticleSuspend;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JacarandaParticle extends ParticleSuspend {

    public JacarandaParticle(World world, double x, double y, double z,
                            double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        // 设置粒子大小
        this.particleScale = 0.15F + rand.nextFloat() * 0.1F;

        // 设置粒子生命周期
        this.particleMaxAge = 40 + rand.nextInt(20);

        // 设置粒子的颜色（淡紫色）
        this.particleRed = 0.9F;
        this.particleGreen = 0.7F;
        this.particleBlue = 0.9F;

        // 设置粒子的透明度
        this.particleAlpha = 0.8F;

        // 设置初始速度
        this.motionX = xSpeed + (rand.nextDouble() - 0.5D) * 0.02D;
        this.motionY = ySpeed;
        this.motionZ = zSpeed + (rand.nextDouble() - 0.5D) * 0.02D;
    }

    @Override
    public void onUpdate() {
        // 向下飘落
        this.motionY -= 0.003D;

        // 添加随机飘动
        this.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.001D;
        this.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.001D;

        // 缓慢减速
        this.motionX *= 0.99D;
        this.motionY *= 0.99D;
        this.motionZ *= 0.99D;

        // 更新位置
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.move(this.motionX, this.motionY, this.motionZ);

        // 淡出效果
        this.particleAlpha = 0.8F * (1.0F - (float)this.particleAge / (float)this.particleMaxAge);

        super.onUpdate();
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 15728880;
    }

}