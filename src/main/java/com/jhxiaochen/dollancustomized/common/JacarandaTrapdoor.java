package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JacarandaTrapdoor extends BlockTrapDoor {
    public JacarandaTrapdoor() {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
        this.setUnlocalizedName("jacaranda_trapdoor").setHardness(3.0F);
        this.setRegistryName(Reference.MOD_ID, "jacaranda_trapdoor");
    }


    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_trapdoor = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry( ).register(
                    jacaranda_trapdoor = new JacarandaTrapdoor( ));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry( ).register(new ItemBlock(jacaranda_trapdoor).setCreativeTab(DollanCustomized.WOOD_TAB).setUnlocalizedName(Reference.MOD_ID + "_" + "jacaranda_trapdoor").setRegistryName(jacaranda_trapdoor.getRegistryName( )));
        }


        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            DollanBlocks.SimpleBlock(jacaranda_trapdoor);
        }
    }
}
