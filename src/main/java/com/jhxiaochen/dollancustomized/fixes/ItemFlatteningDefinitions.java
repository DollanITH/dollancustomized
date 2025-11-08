package com.jhxiaochen.dollancustomized.fixes;

import com.google.common.collect.ImmutableList;
import com.jhxiaochen.dollancustomized.common.BlockMyalite;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
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
                BlockMyalite.MyaliteType.NORMAL.getMetadata(),
                Item.getItemFromBlock(DollanBlocks.myalite),
                (item, oldMetadata, oldStackTagCompound) -> new ItemStack(item)
        ));

        flatteningDefinitions.add(new ItemFlattening.FlatteningDefinition(
                "myalite",
                BlockMyalite.MyaliteType.CRYSTAL.getMetadata(),
                Item.getItemFromBlock(DollanBlocks.myalite_crystal),
                (item, oldMetadata, oldStackTagCompound) -> new ItemStack(item)
        ));

        flatteningDefinitions.add(new ItemFlattening.FlatteningDefinition(
                "myalite",
                BlockMyalite.MyaliteType.PAVEMENT.getMetadata(),
                Item.getItemFromBlock(DollanBlocks.myalite_pavement),
                (item, oldMetadata, oldStackTagCompound) -> new ItemStack(item)
        ));

		return new ItemFlattening(flatteningDefinitions.build());
	}
}