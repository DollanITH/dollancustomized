package com.jhxiaochen.dollancustomized.fixes;

import com.google.common.collect.ImmutableList;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Manages the flattening definitions for the {@link ItemFlattening} data fixer.
 *
 * @author Choonster
 */
public class ItemFlatteningDefinitions {
    /**
     * Creates an instance of the ItemFlattening data fixer with the definitions of the items to flatten.
     *
     * @return The ItemFlattening instance
     */
    public static ItemFlattening createItemFlattening() {
        final ImmutableList.Builder<ItemFlattening.FlatteningDefinition> flatteningDefinitions = new ImmutableList.Builder<>();

        flatteningDefinitions.add(new ItemFlattening.FlatteningDefinition(
                "myalite",
                0,
                Item.getItemFromBlock(DollanBlocks.myalite),
                (item, oldMetadata, oldStackTagCompound) -> new ItemStack(item)
        ));

        flatteningDefinitions.add(new ItemFlattening.FlatteningDefinition(
                "myalite",
                1,
                Item.getItemFromBlock(DollanBlocks.myalite_crystal),
                (item, oldMetadata, oldStackTagCompound) -> new ItemStack(item)
        ));

        flatteningDefinitions.add(new ItemFlattening.FlatteningDefinition(
                "myalite",
                2,
                Item.getItemFromBlock(DollanBlocks.myalite_pavement),
                (item, oldMetadata, oldStackTagCompound) -> new ItemStack(item)
        ));

        return new ItemFlattening(flatteningDefinitions.build());
    }
}
