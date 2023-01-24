package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.common.ItemDollanCraft;
import com.jhxiaochen.dollancustomized.common.ItemLeungCraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;
import java.util.List;

public class DollanItems{
        public static Item leungcraft;
        public static Item dollancraft;

        public static void init() {
        leungcraft = new ItemLeungCraft(3,1.2F,false)
                .setAlwaysEdible()
                .setUnlocalizedName("leungcraft").setRegistryName("leungcraft");
        dollancraft = new ItemDollanCraft(3,1.2F,false)
                    .setAlwaysEdible()
                    .setUnlocalizedName("dollancraft").setRegistryName("dollancraft");
        }

        public static void register()
        {
            registerItem(leungcraft);
            registerItem(dollancraft);
        }

        public static void registerItem(Item item)
        {
            RegistrationHandler.ITEMS.add(item);
        }

        public static void registerModels() {
            registerModel(leungcraft);
            registerModel(dollancraft,0,"dollancustomized:dollancraft");
            registerModel(dollancraft,1,"dollancustomized:dollancraft");
        }

        public static void registerModel(Item item) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }

        private static void registerModel(Item item, int metadata, String name) {
            ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
            ModelLoader.setCustomModelResourceLocation(item, metadata, model);
        }

@Mod.EventBusSubscriber()
public static class RegistrationHandler {
    public static final List<Item> ITEMS = new LinkedList<>();

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        DollanItems.init();
        DollanItems.register();
        ITEMS.stream().forEach(item -> event.getRegistry().register(item));
    }
}
}