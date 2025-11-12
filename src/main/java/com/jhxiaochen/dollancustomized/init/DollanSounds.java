package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DollanSounds {
    public static SoundEvent
            BLOCK_DEEPSLATE_STEP,
            BLOCK_DEEPSLATE_PLACE,
            BLOCK_DEEPSLATE_FALL,
            BLOCK_DEEPSLATE_BREAK;


    public static void registerSounds() {
        BLOCK_DEEPSLATE_STEP = registerSound("block.deepslate.step");
        BLOCK_DEEPSLATE_PLACE = registerSound("block.deepslate.place");
        BLOCK_DEEPSLATE_FALL = registerSound("block.deepslate.fall");
        BLOCK_DEEPSLATE_BREAK = registerSound("block.deepslate.break");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}

