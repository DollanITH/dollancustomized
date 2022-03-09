package com.jhxiaochen.dollancustomized.common;


import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

@SuppressWarnings("Duplicates")
public class BlockLight extends BlockColored {
    public BlockLight(String name) {
        super(Material.ROCK);
        this.setRegistryName("light");
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.LIGHT_TAB);
        this.setHardness(2F);
        this.setLightLevel(1.0F);
        this.isAmbientOcclusion();
        this.setSoundType(SoundType.GLASS);
    }
    public boolean isAmbientOcclusion()
    {
        return false;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

}
