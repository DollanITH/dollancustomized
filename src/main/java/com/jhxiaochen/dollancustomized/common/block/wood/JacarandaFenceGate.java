package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JacarandaFenceGate extends BlockFenceGate {
    public JacarandaFenceGate() {
        super(BlockPlanks.EnumType.OAK);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(DollanSounds.CHERRY_WOOD);
        this.setUnlocalizedName("jacaranda_fence_gate");
        this.setRegistryName(Reference.MOD_ID, "jacaranda_fence_gate");
    }
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_fence_gate = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry( ).register(
                    jacaranda_fence_gate = new JacarandaFenceGate( ));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry( ).register(new ItemBlock(jacaranda_fence_gate).setCreativeTab(DollanCustomized.WOOD_TAB).setUnlocalizedName("jacaranda_fence_gate").setRegistryName(jacaranda_fence_gate.getRegistryName( )));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            DollanBlocks.SimpleBlock(jacaranda_fence_gate);
            ModelLoader.setCustomStateMapper(jacaranda_fence_gate, new StateMap.Builder().ignore(JacarandaFenceGate.POWERED).build());

        }
    }
}
