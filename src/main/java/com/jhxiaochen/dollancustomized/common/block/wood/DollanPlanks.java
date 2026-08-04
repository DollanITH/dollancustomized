package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DollanPlanks extends Block {

    public DollanPlanks() {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    public static Block jacaranda_planks =  null;
    public static Block jacaranda_stairs =  null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_planks = new DollanPlanks()
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setHardness(2.0F).setResistance(5.0F)
                    .setUnlocalizedName("jacaranda_planks")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_planks"));

            event.getRegistry().register(jacaranda_stairs = new DollanStairs(jacaranda_planks)
                    .setCreativeTab(DollanCustomized.WOOD_TAB)
                    .setUnlocalizedName("jacaranda_stairs")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_stairs"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(new ItemBlock(jacaranda_planks).setRegistryName(jacaranda_planks.getRegistryName()));
            event.getRegistry().register(new ItemBlock(jacaranda_stairs).setRegistryName(jacaranda_stairs.getRegistryName()));

        }


        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            DollanBlocks.SimpleBlock(jacaranda_planks);
            DollanBlocks.SimpleBlock(jacaranda_stairs);
        }

    }
}
