package com.jhxiaochen.common.blocks.loader;

import com.jhxiaochen.common.DollanCustomizedMod;
import com.jhxiaochen.common.blocks.ModBlock;
import com.jhxiaochen.common.blocks.metroline.DollanMetroLineBlock;
import com.jhxiaochen.common.blocks.metroline.XingShenMetroLineBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class BlockLoader {
    public static final HashMap<Identifier, Block> DOLLAN_BLOCKS = new HashMap<>();

    public static Block getBlock(Identifier id) {
        return DOLLAN_BLOCKS.get(id);
    }
    public static void addBlock(ModBlock block) {
        switch (block) {
            case DOLLAN_LINE_1, DOLLAN_LINE_2, DOLLAN_LINE_3, DOLLAN_LINE_4, DOLLAN_LINE_5, DOLLAN_LINE_6, DOLLAN_LINE_7, DOLLAN_LINE_8, DOLLAN_LINE_9,
                    DOLLAN_LINE_10, DOLLAN_LINE_11, DOLLAN_LINE_12, DOLLAN_LINE_13, DOLLAN_LINE_14, DOLLAN_LINE_15, DOLLAN_LINE_16, DOLLAN_LINE_17,
                    DOLLAN_LINE_18, DOLLAN_LINE_19, DOLLAN_LINE_20, DOLLAN_LINE_21, DOLLAN_LINE_22: registerMetroLineBlock(block, DollanMetroLineSettings());
            case XINGSHEN_LINE_1, XINGSHEN_LINE_2, XINGSHEN_LINE_3, XINGSHEN_LINE_4, XINGSHEN_LINE_5, XINGSHEN_LINE_6, XINGSHEN_LINE_7/*, XINGSHEN_LINE_8, XINGSHEN_LINE_9,
                    XINGSHEN_LINE_10, XINGSHEN_LINE_11, XINGSHEN_LINE_12, XINGSHEN_LINE_13, XINGSHEN_LINE_14, XINGSHEN_LINE_15, XINGSHEN_LINE_16, XINGSHEN_LINE_17,
                    XINGSHEN_LINE_18, XINGSHEN_LINE_19, XINGSHEN_LINE_20, XINGSHEN_LINE_21, XINGSHEN_LINE_22*/: registerMetroLineBlock(block, XingShenMetroLineSettings());
        }
    }


    public static void registerMetroLineBlock(ModBlock block, Block lineblock) {
        DOLLAN_BLOCKS.put(block.getMetroLineBlockId(ModBlock.MetroLineBlockType.DOLLAN),
                          Registry.register(Registries.BLOCK, block.getMetroLineBlockId(ModBlock.MetroLineBlockType.DOLLAN), lineblock));
        registerItem(block.getMetroLineBlockId(ModBlock.MetroLineBlockType.DOLLAN), lineblock);
    }

    public static void registerItem(Identifier id, Block block) {
        Registry.register(Registries.ITEM, id,
                new BlockItem((Block) block, new FabricItemSettings( )));
    }

    public static Block DollanMetroLineSettings()
    {
        return new DollanMetroLineBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.STONE).strength(1.8F).requiresTool( ));
    }

    public static Block XingShenMetroLineSettings()
    {
        return new XingShenMetroLineBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.STONE).strength(1.8F).requiresTool( ));
    }

    public static void registerBlock(){
        for (ModBlock modBlock : ModBlock.values()) {
            addBlock(modBlock);
        }
        DollanCustomizedMod.LOGGER.info("正在加载东安自定义: 线路颜色方块部分");
    }

}
