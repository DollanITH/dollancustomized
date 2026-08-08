package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DollanFence extends BlockFence {
    public DollanFence() {
        super(Material.WOOD, MapColor.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_fence = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_fence = new DollanFence()
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setUnlocalizedName("jacaranda_fence")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_fence"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(jacaranda_fence)
                    .setUnlocalizedName(Reference.MOD_ID + "_" + "jacaranda_fence")
                    .setRegistryName(jacaranda_fence.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            DollanBlocks.SimpleBlock(jacaranda_fence);
        }
    }
}
