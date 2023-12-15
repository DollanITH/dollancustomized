package com.jhxiaochen.common;

import com.jhxiaochen.common.blocks.loader.BlockLoader;
import com.jhxiaochen.common.blocks.ModBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class CreativeGroupLoader {
    public static final ItemGroup DOLLAN_METRO_LINE_BLOCK = FabricItemGroup.builder( )	.icon(() -> new ItemStack(ModBlock.DOLLAN_LINE_1.getBlock(ModBlock.MetroLineBlockType.DOLLAN)))
            .displayName(Text.translatable("itemGroup.tabdollanMetro"))
            .entries((context, entries) -> {
                for (ModBlock block: ModBlock.values())
                    for (ModBlock.MetroLineBlockType type : ModBlock.MetroLineBlockType.values())
                    if (type == ModBlock.MetroLineBlockType.DOLLAN)
                        entries.add(block.getBlock(type));
            })
            .build();
}
