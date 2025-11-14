package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class DollanSounds {
    public static SoundEvent
            BLOCK_DEEPSLATE_STEP = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.deepslate.step"));

    public static SoundEvent
            BLOCK_DEEPSLATE_PLACE = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.deepslate.place"));
    public static SoundEvent
            BLOCK_DEEPSLATE_FALL = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.deepslate.fall"));
    public static SoundEvent
            BLOCK_DEEPSLATE_BREAK = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.deepslate.break"));

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(BLOCK_DEEPSLATE_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.step")));
        event.getRegistry().register(BLOCK_DEEPSLATE_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.place")));
        event.getRegistry().register(BLOCK_DEEPSLATE_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.fall")));
        event.getRegistry().register(BLOCK_DEEPSLATE_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.break")));
    }

}

