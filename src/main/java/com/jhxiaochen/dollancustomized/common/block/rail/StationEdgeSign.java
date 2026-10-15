package com.jhxiaochen.dollancustomized.common.block.rail;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.rail.base.RailwaySignBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class StationEdgeSign extends RailwaySignBase {
    public static Block block;

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event){
        event.getRegistry().register(block = new StationEdgeSign().setRegistryName("station_edge_sign").setTranslationKey("StationEdgeSign"));
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event){
        event.getRegistry().register(new ItemBlock(block).setRegistryName("station_edge_sign"));
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:station_edge_sign", "inventory"));
    }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            // 牌子后面还带一根 brushed_aluminum 立柱（模型 z: 2 -> 4），碰撞箱取两者并集。
            return getBoxXZRange(state, 5.0 / 16.0, 11.0 / 16.0, 12.0 / 16.0, 0.0, 4.0 / 16.0);
        }





}
