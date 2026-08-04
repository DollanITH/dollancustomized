package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import suike.suikecherry.sound.Sound;

public class DollanPressurePlate extends BlockPressurePlate {
    boolean cherry_sound;
    public DollanPressurePlate(boolean cherry_sound) {
        super(Material.WOOD, BlockPressurePlate.Sensitivity.EVERYTHING);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(0.5F);
        this.cherry_sound = cherry_sound;
    }

    @Override
    protected void playClickOnSound(World world, BlockPos pos) {
        if (cherry_sound) {
            Sound.playSound(world, pos, "block.cherry_button.click", 2.0F, 1.0F);
        } else {
            super.playClickOnSound(world, pos);
        }
    }

    @Override
    protected void playClickOffSound(World world, BlockPos pos) {
        if (cherry_sound) {
            Sound.playSound(world, pos, "block.cherry_button.restore", 2.0F, 1.0F);
        } else {
            super.playClickOnSound(world, pos);
        }
    }

    public static Block jacaranda_pressure_plate = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {


        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_pressure_plate = new DollanPressurePlate(true)
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setUnlocalizedName("jacaranda_pressure_plate")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_pressure_plate"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(jacaranda_pressure_plate)
                    .setCreativeTab(DollanCustomized.WOOD_TAB)
                    .setUnlocalizedName("jacaranda_pressure_plate")
                    .setRegistryName(jacaranda_pressure_plate.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            DollanBlocks.SimpleBlock(jacaranda_pressure_plate);
        }
    }
}
