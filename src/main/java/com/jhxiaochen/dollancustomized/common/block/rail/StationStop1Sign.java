package com.jhxiaochen.dollancustomized.common.block.rail;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.rail.base.TwoBlocksSignWithHang;
import com.jhxiaochen.dollancustomized.common.item.ItemRailwaySign2;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class StationStop1Sign extends TwoBlocksSignWithHang {
    public static Block block;

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(block = new StationStop1Sign().setRegistryName("station_stop_1_sign").setTranslationKey("StationStopSign"));
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemRailwaySign2(block).setRegistryName(block.getRegistryName()));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:station_stop_1_sign", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 1, new ModelResourceLocation("dollancustomized:station_stop_1_sign_wall", "inventory"));
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().withName(WALL).withSuffix("_station_stop_1_sign").build( ));
    }

}
