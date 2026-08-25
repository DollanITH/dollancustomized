package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanPolished extends net.minecraft.block.Block {
        public DollanPolished(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setTranslationKey(Reference.MOD_ID + "." + name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(2.3F);
        this.setResistance(25F);
        this.setLightLevel(0F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}
