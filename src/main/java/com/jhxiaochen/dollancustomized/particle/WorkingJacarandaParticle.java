package com.jhxiaochen.dollancustomized.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WorkingJacarandaParticle extends Particle {
    public WorkingJacarandaParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        // 设置粒子属性
        this.particleScale = 0.1F + rand.nextFloat() * 0.05F;
        this.particleMaxAge = 60 + rand.nextInt(20);
        this.particleGravity = 0.001F;

        // 设置粒子颜色（蓝花楹的紫色色调）
        this.particleRed = 0.8F + rand.nextFloat() * 0.2F;
        this.particleGreen = 0.7F + rand.nextFloat() * 0.2F;
        this.particleBlue = 0.9F + rand.nextFloat() * 0.1F;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Tessellator tessellator = Tessellator.getInstance();
        float f = 0.1F * this.particleScale;

        // 计算粒子位置
        float f1 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f2 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f3 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);

        // 获取光照
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        // 计算旋转后的位置
        float f4 = -rotationX * f;
        float f5 = rotationZ * f;
        float f6 = -rotationYZ * f;
        float f7 = rotationXY * f;
        float f8 = -rotationXZ * f;

        // 使用 quad 模式
        tessellator.getBuffer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        tessellator.getBuffer().pos(f1 + f4, f2 + f5, f3 + f6).tex(1.0F, 1.0F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.getBuffer().pos(f1 + f4, f2 - f5, f3 - f6).tex(1.0F, 0.0F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.getBuffer().pos(f1 - f4, f2 - f5, f3 + f6).tex(0.0F, 0.0F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.getBuffer().pos(f1 - f4, f2 + f5, f3 - f6).tex(0.0F, 1.0F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.draw();
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}