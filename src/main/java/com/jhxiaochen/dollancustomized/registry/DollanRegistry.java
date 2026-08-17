package com.jhxiaochen.dollancustomized.registry;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class DollanRegistry {
    @SubscribeEvent
    public static void registerBlock(final RegistryEvent.Register<net.minecraft.block.Block> event) {
        DollanBlocks.registerBlockAndModels();
        WoodBlocks.register();
        DollanBlocks.BLOCKS.forEach(block -> event.getRegistry().register(block));
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        DollanItems.register();
        DollanItems.ITEMS.forEach(item -> event.getRegistry().register(item));
    }

    @SubscribeEvent
    public static void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(DollanSounds.BLOCK_DEEPSLATE_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.step")));
        event.getRegistry().register(DollanSounds.BLOCK_DEEPSLATE_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.place")));
        event.getRegistry().register(DollanSounds.BLOCK_DEEPSLATE_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.fall")));
        event.getRegistry().register(DollanSounds.BLOCK_DEEPSLATE_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.deepslate.break")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_WOOD_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.step")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_WOOD_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.place")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_WOOD_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.fall")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_WOOD_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.break")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_WOOD_HIT.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_wood.hit")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_LEAVES_STEP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.step")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_LEAVES_PLACE.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.place")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_LEAVES_FALL.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.fall")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_LEAVES_BREAK.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.break")));
        event.getRegistry().register(DollanSounds.BLOCK_CHERRY_LEAVES_HIT.setRegistryName(new ResourceLocation(Reference.MOD_ID , "block.cherry_leaves.hit")));
        event.getRegistry().register(DollanSounds.ITEM_AXE_STRIP.setRegistryName(new ResourceLocation(Reference.MOD_ID , "item.axe.strip")));
    }
}
