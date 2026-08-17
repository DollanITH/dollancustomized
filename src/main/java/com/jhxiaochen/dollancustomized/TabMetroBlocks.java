package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabMetroBlocks extends CreativeTabs {

    public TabMetroBlocks() {
        super("tabMetroBlocks");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.signal_green_two_lights);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);
        items.sort((o1, o2) -> {
            ResourceLocation name1 = o1.getItem().getRegistryName();
            ResourceLocation name2 = o2.getItem().getRegistryName();
            String s1 = name1 != null ? name1.toString() : "";
            String s2 = name2 != null ? name2.toString() : "";
            //先把对象储存，避免性能问题
            return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
        });
    }

}