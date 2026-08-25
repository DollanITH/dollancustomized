package com.jhxiaochen.dollancustomized.registry;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanHangingSign;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanSign;
import com.jhxiaochen.dollancustomized.common.item.*;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanItems{
    public static final List<Item> ITEMS = new LinkedList<>();
    public static Item leungcraft = new ItemLeungCraft(5, 1.2F)
            .setAlwaysEdible()
            .setTranslationKey("leungcraft").setRegistryName("leungcraft");
    public static Item dollan = new ItemDollanCraft(5, 1.2F)
            .setAlwaysEdible()
            .setTranslationKey("dollancraft").setRegistryName("dollan");
    public static Item station_end_door = new ItemStationEndDoor(DollanBlocks.station_end_door);
    public static Item jacaranda_sign = new ItemDollanSign((DollanSign) WoodBlocks.jacaranda_wall_sign, (DollanSign) WoodBlocks.jacaranda_standing_sign)
        .setTranslationKey("jacaranda_sign")
        .setRegistryName("jacaranda_sign");
    public static Item jacaranda_hanging_sign = new ItemDollanHangingSign(
            WoodBlocks.jacaranda_hanging_sign,
            (DollanHangingSign) WoodBlocks.jacaranda_hanging_sign_wall,
            WoodBlocks.jacaranda_hanging_sign_attached
    ).setTranslationKey("jacaranda_hanging_sign")
        .setRegistryName("jacaranda_hanging_sign");
    public static Item mangrove_sign = new ItemDollanSign((DollanSign) WoodBlocks.mangrove_wall_sign, (DollanSign) WoodBlocks.mangrove_standing_sign)
            .setTranslationKey("mangrove_sign")
            .setRegistryName("mangrove_sign");
    public static Item mangrove_hanging_sign = new ItemDollanHangingSign(
            WoodBlocks.mangrove_hanging_sign,
            (DollanHangingSign) WoodBlocks.mangrove_hanging_sign_wall,
            WoodBlocks.mangrove_hanging_sign_attached
    ).setTranslationKey("mangrove_hanging_sign")
            .setRegistryName("mangrove_hanging_sign");
    public static Item item_mangrove_door = new ItemDollanDoor(WoodBlocks.mangrove_door).setTranslationKey(Reference.MOD_ID + "_" + "mangrove_door").setRegistryName(WoodBlocks.mangrove_door.getRegistryName());
    public static Item item_jacaranda_door = new ItemDollanDoor(WoodBlocks.jacaranda_door).setTranslationKey(Reference.MOD_ID + "_" + "jacaranda_door").setRegistryName(WoodBlocks.jacaranda_door.getRegistryName());
    public static ItemDollanSlab jacaranda_slab_item =
            (ItemDollanSlab) new ItemDollanSlab(WoodBlocks.jacaranda_slab, WoodBlocks.jacaranda_double_slab)
                    .setRegistryName(WoodBlocks.jacaranda_slab.getRegistryName( ));
    public static ItemDollanSlab mangrove_slab_item =
            (ItemDollanSlab) new ItemDollanSlab(WoodBlocks.mangrove_slab, WoodBlocks.mangrove_double_slab)
                    .setRegistryName(WoodBlocks.mangrove_slab.getRegistryName( ));

    public static void register() {
        registerItem(leungcraft);
        registerItem(dollan);
        registerItem(station_end_door);
        registerItem(jacaranda_slab_item);
        registerItem(mangrove_slab_item);
        registerItem(item_jacaranda_door);
        registerItem(item_mangrove_door);
        registerItem(jacaranda_sign);
        registerItem(jacaranda_hanging_sign);
        registerItem(mangrove_sign);
        registerItem(mangrove_hanging_sign);
    }

    public static void registerModel() {
        registerModel(dollan, 1, "dollancraft");
    }

    public static void registerItem(Item item)
        {
            ITEMS.add(item);
            registerModel(item);
        }

    private static void registerModel(Item item)
    {
        registerModel(item, 0, item.getRegistryName().getPath());
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item, int metadata, String name) {
            ModelResourceLocation model = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
            ModelLoader.setCustomModelResourceLocation(item, metadata, model);
        }

}
