package com.jhxiaochen.common;

import com.jhxiaochen.common.blocks.loader.BlockLoader;
import com.jhxiaochen.common.command.Command114514;
import net.fabricmc.api.ModInitializer;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DollanCustomizedMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "dollancustomized";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("正在加载东安自定义4.0中...");
        new Command114514();
        BlockLoader.registerBlock();
        Registry.register(Registries.ITEM_GROUP, new Identifier("dollancustomized", "dollan_metro_block"), CreativeGroupLoader.DOLLAN_METRO_LINE_BLOCK);
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.SMOOTH_STONE)
                .lightWithFluid(Fluids.WATER)
                .destDimID(new Identifier("dollancustomized", "formwork_world"))
                .tintColor(135, 168, 211)
                .onlyLightInOverworld()
                .registerPortal();
    }
}