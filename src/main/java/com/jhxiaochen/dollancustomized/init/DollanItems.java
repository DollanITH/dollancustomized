package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.item.*;
import com.jhxiaochen.dollancustomized.init.blocks.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.blocks.WoodBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanItems{
    public static final List<Item> ITEMS = new LinkedList<>();
    public static Item leungcraft;
    public static Item dollan;
    public static Item station_end_door;
    public static Item jacaranda_sign;
    public static Item jacaranda_hanging_sign;

    public static void register() {
        registerItem(leungcraft = new ItemLeungCraft(5, 1.2F)
                .setAlwaysEdible()
                .setUnlocalizedName("leungcraft").setRegistryName("leungcraft"));
        registerItem(dollan = new ItemDollanCraft(5, 1.2F)
                .setAlwaysEdible()
                .setUnlocalizedName("dollancraft").setRegistryName("dollan"));
        registerItem(station_end_door = new ItemStationEndDoor(DollanBlocks.station_end_door));
        registerItem(jacaranda_sign = new ItemJacarandaSign());
        registerItem(jacaranda_hanging_sign = new ItemJacarandaHangingSign(
                WoodBlocks.jacaranda_hanging_sign,
                WoodBlocks.jacaranda_hanging_sign_wall,
                WoodBlocks.jacaranda_hanging_sign_attached
        ));
    }

    public static void registerModel() {
        registerModel(jacaranda_hanging_sign);
        registerModel(jacaranda_sign);
        registerModel(station_end_door);
        registerModel(leungcraft);
        registerModel(dollan, 0, "dollancraft");
        registerModel(dollan, 1, "dollancraft");
    }

    public static void registerItem(Item item)
        {
            ITEMS.add(item);
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