package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.SoundType;
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
    public static SoundEvent
            BLOCK_CHERRY_WOOD_STEP = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.step"));

    public static SoundEvent
            BLOCK_CHERRY_WOOD_PLACE = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.place"));
    public static SoundEvent
            BLOCK_CHERRY_WOOD_FALL = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.fall"));
    public static SoundEvent
            BLOCK_CHERRY_WOOD_BREAK = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.break"));
    public static SoundEvent
            BLOCK_CHERRY_WOOD_HIT = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.hit"));
    public static SoundEvent
            BLOCK_CHERRY_LEAVES_STEP = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.step"));

    public static SoundEvent
            BLOCK_CHERRY_LEAVES_PLACE = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.place"));
    public static SoundEvent
            BLOCK_CHERRY_LEAVES_FALL = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.fall"));
    public static SoundEvent
            BLOCK_CHERRY_LEAVES_BREAK = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.break"));
    public static SoundEvent
            BLOCK_CHERRY_LEAVES_HIT = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.hit"));
    public static SoundEvent
            ITEM_AXE_STRIP = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "item.axe.strip"));
    public static SoundType CHERRY_WOOD = new SoundType(1.0F, 1.0F,
                                          DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                          DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                          DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                          DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                          DollanSounds.BLOCK_CHERRY_WOOD_FALL);
    public static SoundType CHERRY_LEAVES = new SoundType(1.0F, 1.0F,
                                          DollanSounds.BLOCK_CHERRY_LEAVES_BREAK,
                                          DollanSounds.BLOCK_CHERRY_LEAVES_STEP,
                                          DollanSounds.BLOCK_CHERRY_LEAVES_PLACE,
                                          DollanSounds.BLOCK_CHERRY_LEAVES_HIT,
                                          DollanSounds.BLOCK_CHERRY_LEAVES_FALL);

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(BLOCK_DEEPSLATE_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.step")));
        event.getRegistry().register(BLOCK_DEEPSLATE_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.place")));
        event.getRegistry().register(BLOCK_DEEPSLATE_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.fall")));
        event.getRegistry().register(BLOCK_DEEPSLATE_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.break")));
        event.getRegistry().register(BLOCK_CHERRY_WOOD_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.step")));
        event.getRegistry().register(BLOCK_CHERRY_WOOD_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.place")));
        event.getRegistry().register(BLOCK_CHERRY_WOOD_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.fall")));
        event.getRegistry().register(BLOCK_CHERRY_WOOD_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.break")));
        event.getRegistry().register(BLOCK_CHERRY_WOOD_HIT.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.hit")));
        event.getRegistry().register(BLOCK_CHERRY_LEAVES_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.step")));
        event.getRegistry().register(BLOCK_CHERRY_LEAVES_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.place")));
        event.getRegistry().register(BLOCK_CHERRY_LEAVES_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.fall")));
        event.getRegistry().register(BLOCK_CHERRY_LEAVES_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.break")));
        event.getRegistry().register(BLOCK_CHERRY_LEAVES_HIT.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.hit")));
        event.getRegistry().register(ITEM_AXE_STRIP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "item.axe.strip")));
    }

}

