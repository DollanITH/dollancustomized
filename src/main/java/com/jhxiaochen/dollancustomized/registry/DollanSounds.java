package com.jhxiaochen.dollancustomized.registry;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

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
            BLOCK_CHERRY_BUTTON_CLICK = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_button.click"));
    public static SoundEvent
            BLOCK_CHERRY_BUTTON_RESTORE = new SoundEvent(new ResourceLocation(Reference.MOD_ID , "block.cherry_button.restore"));
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


}
