package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/*
powered by Minecraft Forge 1.12.2 MDK
 */
public class DollanBlock4 extends net.minecraft.block.Block {
        public DollanBlock4(String name) {
        super(Material.IRON);
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID + "." + name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.7F);
        this.setResistance(20F);
        this.setLightLevel(0F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }


}

