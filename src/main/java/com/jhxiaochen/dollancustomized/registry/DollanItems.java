package com.jhxiaochen.dollancustomized.registry;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.item.*;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanItems{
    public static final List<Item> ITEMS = new LinkedList<>();
    public static Item leungcraft = new ItemLeungCraft(5, 1.2F)
            .setAlwaysEdible()
            .setUnlocalizedName("leungcraft").setRegistryName("leungcraft");
    public static Item dollan = new ItemDollanCraft(5, 1.2F)
            .setAlwaysEdible()
            .setUnlocalizedName("dollancraft").setRegistryName("dollan");
    public static Item station_end_door = new ItemStationEndDoor(DollanBlocks.station_end_door);
    public static Item jacaranda_sign = new ItemJacarandaSign();
    public static Item jacaranda_hanging_sign = new ItemJacarandaHangingSign(
            WoodBlocks.jacaranda_hanging_sign,
            WoodBlocks.jacaranda_hanging_sign_wall,
            WoodBlocks.jacaranda_hanging_sign_attached
    );
    public static Item item_mangrove_door = new ItemDoor(WoodBlocks.mangrove_door).setCreativeTab(DollanCustomized.WOOD_TAB).setUnlocalizedName(Reference.MOD_ID + "_" + "mangrove_door").setRegistryName(WoodBlocks.mangrove_door.getRegistryName());
    public static Item item_jacaranda_door = new ItemDoor(WoodBlocks.jacaranda_door).setCreativeTab(DollanCustomized.WOOD_TAB).setUnlocalizedName(Reference.MOD_ID + "_" + "jacaranda_door").setRegistryName(WoodBlocks.jacaranda_door.getRegistryName());
    public static ItemSlab jacaranda_slab_item = (ItemSlab) new ItemSlab(WoodBlocks.jacaranda_slab, WoodBlocks.jacaranda_slab, WoodBlocks.jacaranda_double_slab).setRegistryName(WoodBlocks.jacaranda_slab.getRegistryName( ));

    public static void register() {
        registerItem(leungcraft);
        registerItem(dollan);
        registerItem(station_end_door);
        registerItem(jacaranda_sign);
        registerItem(jacaranda_hanging_sign);
    }

    public static void registerModel() {
        registerModel(dollan, 1, "dollancraft");
        registerModel(jacaranda_slab_item);
        registerModel(item_jacaranda_door);
        registerModel(item_mangrove_door);
    }

    public static void registerItem(Item item)
        {
            ITEMS.add(item);
            registerModel(item);
        }

    private static void registerModel(Item item)
    {
        registerModel(item, 0, item.getRegistryName().getResourcePath());
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item, int metadata, String name) {
            ModelResourceLocation model = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
            ModelLoader.setCustomModelResourceLocation(item, metadata, model);
        }

}