package com.jhxiaochen.dollancustomized.proxy;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;

public class ClientProxy implements CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init() {
        System.out.println("special renderer bound!");

    }

    @Override
    public void postInit() {

    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Side.CLIENT)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event)
        {
            DollanBlocks.registerModels();
        }
    }
}
