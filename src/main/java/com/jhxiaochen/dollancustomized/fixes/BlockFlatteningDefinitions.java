package com.jhxiaochen.dollancustomized.fixes;

import com.google.common.collect.ImmutableList;
import com.jhxiaochen.dollancustomized.common.BlockMyalite;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;

/**
 * Manages the flattening definitions for the {@link BlockFlattening} data fixer.
 *
 * @author Choonster
 */
public class BlockFlatteningDefinitions {
	/**
	 * Creates an instance of the BlockFlattening data fixer with the definitions of the blocks to flatten.
	 *
	 * @return The BlockFlattening instance
	 */
	public static BlockFlattening createBlockFlattening() {
		final ImmutableList.Builder<BlockFlattening.FlatteningDefinition> flatteningDefinitions = new ImmutableList.Builder<>();

            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "myalite",
                    BlockMyalite.MyaliteType.NORMAL.getMetadata(),
                    DollanBlocks.myalite,
                    (block, tileEntityNBT) -> block.getDefaultState(),
                    null
            ));

            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "myalite",
                    BlockMyalite.MyaliteType.CRYSTAL.getMetadata(),
                    DollanBlocks.myalite_crystal,
                    (block, tileEntityNBT) -> block.getDefaultState(),
                    null
            ));

            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "myalite",
                    BlockMyalite.MyaliteType.PAVEMENT.getMetadata(),
                    DollanBlocks.myalite_pavement,
                    (block, tileEntityNBT) -> block.getDefaultState(),
                    null
            ));

		return new BlockFlattening(flatteningDefinitions.build());
	}
}