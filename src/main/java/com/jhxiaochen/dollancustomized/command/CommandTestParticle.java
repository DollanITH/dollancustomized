package com.jhxiaochen.dollancustomized.command;

import com.jhxiaochen.dollancustomized.particle.JacarandaPetalParticle;
import com.jhxiaochen.dollancustomized.particle.MultiTextureJacarandaParticle;
import com.jhxiaochen.dollancustomized.particle.WorkingJacarandaParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandTestParticle extends CommandBase {

    @Override
    public String getName() {
        return "testparticle";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/testparticle [petal|multitex|working] - 在玩家位置生成粒子效果";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            World world = player.world;

            if (!world.isRemote) {
                sender.sendMessage(new TextComponentString("请在客户端运行此命令来查看粒子效果"));
                return;
            }

            // 根据参数选择粒子类型
            if (args.length > 0 && "petal".equals(args[0])) {
                // 生成花瓣粒子 - 向下飘落
                for (int i = 0; i < 15; i++) {
                    double offsetX = player.posX + (Math.random() - 0.5) * 3;
                    double offsetY = player.posY + 2;
                    double offsetZ = player.posZ + (Math.random() - 0.5) * 3;

                    double motionX = (Math.random() - 0.5) * 0.02;
                    double motionY = -0.04 - Math.random() * 0.02; // 向下飘落
                    double motionZ = (Math.random() - 0.5) * 0.02;

                    JacarandaPetalParticle particle = new JacarandaPetalParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                }
                sender.sendMessage(new TextComponentString("已生成15个向下飘落的花瓣粒子！"));
            } else if (args.length > 0 && "multitex".equals(args[0])) {
                // 生成多纹理粒子 - 可选
                for (int i = 0; i < 5; i++) {
                    double offsetX = player.posX + (Math.random() - 0.5) * 2;
                    double offsetY = player.posY + 1 + Math.random();
                    double offsetZ = player.posZ + (Math.random() - 0.5) * 2;

                    double motionX = (Math.random() - 0.5) * 0.1;
                    double motionY = 0.05 + Math.random() * 0.1;
                    double motionZ = (Math.random() - 0.5) * 0.1;

                    // 注意：如果MultiTextureJacarandaParticle文件不存在，这会导致错误
                    try {
                        MultiTextureJacarandaParticle particle = new MultiTextureJacarandaParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
                        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                        sender.sendMessage(new TextComponentString("已生成多纹理粒子效果！"));
                    } catch (Exception e) {
                        sender.sendMessage(new TextComponentString("多纹理粒子不可用，请使用/particle petal"));
                    }
                }
            } else if (args.length > 0 && "working".equals(args[0])) {
                // 生成工作粒子 - 可选
                for (int i = 0; i < 5; i++) {
                    double offsetX = player.posX + (Math.random() - 0.5) * 2;
                    double offsetY = player.posY + 1 + Math.random();
                    double offsetZ = player.posZ + (Math.random() - 0.5) * 2;

                    double motionX = (Math.random() - 0.5) * 0.1;
                    double motionY = 0.05 + Math.random() * 0.1;
                    double motionZ = (Math.random() - 0.5) * 0.1;

                    // 注意：如果WorkingJacarandaParticle文件不存在，这会导致错误
                    try {
                        WorkingJacarandaParticle
                                particle = new WorkingJacarandaParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
                        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                        sender.sendMessage(new TextComponentString("已生成工作粒子效果！"));
                    } catch (Exception e) {
                        sender.sendMessage(new TextComponentString("工作粒子不可用，请使用/particle petal"));
                    }
                }
            } else {
                // 默认生成花瓣粒子
                for (int i = 0; i < 10; i++) {
                    double offsetX = player.posX + (Math.random() - 0.5) * 2;
                    double offsetY = player.posY + 2;
                    double offsetZ = player.posZ + (Math.random() - 0.5) * 2;

                    double motionX = (Math.random() - 0.5) * 0.02;
                    double motionY = -0.04 - Math.random() * 0.02; // 向下飘落
                    double motionZ = (Math.random() - 0.5) * 0.02;

                    JacarandaPetalParticle particle = new JacarandaPetalParticle(world, offsetX, offsetY, offsetZ, motionX, motionY, motionZ);
                    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                }
                sender.sendMessage(new TextComponentString("已生成10个花瓣粒子效果！"));
            }
        } else {
            sender.sendMessage(new TextComponentString("此命令只能由玩家使用"));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}