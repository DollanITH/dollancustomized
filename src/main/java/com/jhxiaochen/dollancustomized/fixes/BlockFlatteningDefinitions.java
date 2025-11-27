package com.jhxiaochen.dollancustomized.fixes;

import com.google.common.collect.ImmutableList;
import com.jhxiaochen.dollancustomized.common.road.ArrowNew;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.util.EnumFacing;

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
                1,
                DollanBlocks.myalite_crystal,
                (block, tileEntityNBT) -> block.getDefaultState(),
                null
        ));

        flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                "myalite",
                2,
                DollanBlocks.myalite_pavement,
                (block, tileEntityNBT) -> block.getDefaultState(),
                null
        ));

        for (EnumFacing facing : EnumFacing.HORIZONTALS)
        {
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_right_staright",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_right_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_right_staright_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_right_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_right_staright",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_right_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_right_staright_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_right_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_staright",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_staright_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_staright_diaotou",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_straight_diaotou,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_staright_diaotou_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_straight_diaotou,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_staright",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_staright_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_straight,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_diaotou",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_diaotou,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_diaotou_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_diaotou,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_diaotou",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_diaotou,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_diaotou_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_diaotou,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_confluence_right",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_confluence_right,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_confluence_right_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_confluence_right,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_confluence_left",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_confluence_left,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_confluence_left_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_confluence_left,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_right",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_right,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_left_right_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_left_right,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_right",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_right,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.FULL).withProperty(ArrowNew.FACING, facing),
                    null
            ));
            flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(
                    "arrow_right_sl",
                    facing.getHorizontalIndex(),
                    DollanBlocks.arrow_right,
                    (block, tileEntityNBT) -> block.getDefaultState().withProperty(ArrowNew.TYPE, ArrowNew.MarkType.SLAB).withProperty(ArrowNew.FACING, facing),
                    null
            ));
        }

		return new BlockFlattening(flatteningDefinitions.build());
	}
}