package com.jhxiaochen.common.datagen;

import com.jhxiaochen.common.blocks.ModBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;


public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (ModBlock modBlock : ModBlock.values()) {
            createSimpleBlock(modBlock, blockStateModelGenerator);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }

    private void createSimpleBlock(ModBlock block, BlockStateModelGenerator blockStateModelGenerator) {
            if (block.dollan) {
                Identifier identifier = Models.CUBE_ALL.upload(block.getBlock(ModBlock.MetroLineBlockType.DOLLAN), TextureMap.all(TextureMap.getId(block.getBlock(ModBlock.MetroLineBlockType.DOLLAN))), blockStateModelGenerator.modelCollector);
                blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block.getBlock(ModBlock.MetroLineBlockType.DOLLAN), identifier));
                blockStateModelGenerator.registerParentedItemModel(block.getBlock(ModBlock.MetroLineBlockType.DOLLAN), identifier);
            }
            if (block.xingshen)
            {
                Identifier identifier = Models.CUBE_ALL.upload(block.getBlock(ModBlock.MetroLineBlockType.XINGSHEN), TextureMap.all(TextureMap.getId(block.getBlock(ModBlock.MetroLineBlockType.XINGSHEN))), blockStateModelGenerator.modelCollector);
                blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block.getBlock(ModBlock.MetroLineBlockType.XINGSHEN), identifier));
                blockStateModelGenerator.registerParentedItemModel(block.getBlock(ModBlock.MetroLineBlockType.XINGSHEN), identifier);
            }
        }
    }






