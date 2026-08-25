package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanBlock5 extends net.minecraft.block.Block {
        public DollanBlock5(String name) {
        super(Material.GLASS);
        this.setRegistryName(name);
        this.setTranslationKey(Reference.MOD_ID + "." + name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(0.8F);
        this.setResistance(8F);
        this.setLightLevel(0F);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}
