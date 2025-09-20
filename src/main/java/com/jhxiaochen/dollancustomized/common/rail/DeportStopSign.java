package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.item.ItemRailwaySign;
import com.jhxiaochen.dollancustomized.common.rail.base.TwoBlocksSignBase;
import com.jhxiaochen.dollancustomized.model.Registry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Registry.ModElement.Tag
public class DeportStopSign extends Registry.ModElement {
    @GameRegistry.ObjectHolder("dollancustomized:deport_stop_sign")
    public static final Block block = null;

    public DeportStopSign(Registry registry) {
        super(registry);
    }

    @Override
    public void initElements() {
        registry.blocks.add(() -> new DeportStopSign.Register().setRegistryName("deport_stop_sign").setUnlocalizedName("DeportStopSign"));
        registry.items.add(() -> new ItemRailwaySign(block).setRegistryName(block.getRegistryName()));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:deport_stop_sign", "inventory"));
    }

    public static class Register extends TwoBlocksSignBase {

    }
}
