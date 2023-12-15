package com.jhxiaochen.common.blocks;

import com.jhxiaochen.common.DollanCustomizedMod;
import com.jhxiaochen.common.blocks.loader.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public enum ModBlock {

    DOLLAN_LINE_1(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_2(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_3(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_4(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_5(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_6(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_7(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_8(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_9(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_10(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_11(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_12(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_13(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_14(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_15(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_16(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_17(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_18(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_19(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_20(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_21(BlockTags.PICKAXE_MINEABLE),
    DOLLAN_LINE_22(BlockTags.PICKAXE_MINEABLE),

    XINGSHEN_LINE_1(true,BlockTags.PICKAXE_MINEABLE),
    XINGSHEN_LINE_2(true,BlockTags.PICKAXE_MINEABLE),
    XINGSHEN_LINE_3(true,BlockTags.PICKAXE_MINEABLE),
    XINGSHEN_LINE_4(true,BlockTags.PICKAXE_MINEABLE),
    XINGSHEN_LINE_5(true,BlockTags.PICKAXE_MINEABLE),
    XINGSHEN_LINE_6(true,BlockTags.PICKAXE_MINEABLE),
    XINGSHEN_LINE_7(true,BlockTags.PICKAXE_MINEABLE);

    public final TagKey<Block>[] blockTags;

    public ModBlock associatedBlock = null;

    public String textureId = "";
    public String bottomId = "";
    public String topId = "";

    public boolean dollan = false;
    public boolean xingshen = false;

    ModBlock(TagKey<Block>... blockTags)
    {
        this.blockTags = blockTags;
        this.dollan = true;
    }

    ModBlock(boolean xingshen, TagKey<Block>... blockTags)
    {
        this.blockTags = blockTags;
        this.xingshen = xingshen;
    }

    public Boolean hasBlock(MetroLineBlockType type){
        return switch (type){
            case DOLLAN -> dollan;
            case XINGSHEN -> xingshen;
        };
    }

    public enum MetroLineBlockType {
        DOLLAN,
        XINGSHEN,
    }
    
    public Identifier getMetroLineBlockId(MetroLineBlockType type){
        return new Identifier(DollanCustomizedMod.MOD_ID, this.toString());
    }

    public Block getBlock(MetroLineBlockType type){
        return BlockLoader.getBlock(this.getMetroLineBlockId(type));
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }




}
