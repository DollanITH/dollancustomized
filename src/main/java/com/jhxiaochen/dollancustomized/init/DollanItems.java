package com.jhxiaochen.dollancustomized.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanItems
{
@Mod.EventBusSubscriber()
public static class RegistrationHandler
{
    public static final List<Item> ITEMS = new LinkedList<>();

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        ITEMS.stream().forEach(item -> event.getRegistry().register(item));
    }
}
}