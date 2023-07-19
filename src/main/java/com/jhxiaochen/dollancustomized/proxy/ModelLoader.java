package com.jhxiaochen.dollancustomized.proxy;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ModelLoader {

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Side.CLIENT)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            DollanBlocks.registerModels();
            DollanItems.registerModels();
        }


    }

}
