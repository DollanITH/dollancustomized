package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.item.ItemRailwaySign2;
import com.jhxiaochen.dollancustomized.common.rail.base.TwoBlocksSignWithHang;
import com.jhxiaochen.dollancustomized.model.Registry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Registry.ModElement.Tag
public class StationStop1Sign extends Registry.ModElement  {
    @GameRegistry.ObjectHolder("dollancustomized:station_stop_1_sign")
    public static Block block = null;

    public StationStop1Sign(Registry registry) {
        super(registry);
    }

    @Override
    public void initElements() {
        registry.blocks.add(() -> new StationStop1Sign.Register().setRegistryName("station_stop_1_sign").setUnlocalizedName("StationStopSign"));
        registry.items.add(() -> new ItemRailwaySign2(block).setRegistryName(block.getRegistryName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:station_stop_1_sign", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 1, new ModelResourceLocation("dollancustomized:station_stop_1_sign_wall", "inventory"));
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().withName(Register.WALL).withSuffix("_station_stop_1_sign").build( ));
    }


    public static class Register extends TwoBlocksSignWithHang {

    }

}
