package com.jhxiaochen.dollancustomized.common;


import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

@SuppressWarnings("Duplicates")
public class BlockMarbleTile extends BlockColored {
    public BlockMarbleTile() {
        super(Material.ROCK);
        this.setRegistryName("marble_tile");
        this.setUnlocalizedName(Reference.MOD_ID + "." + "marble_tile");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.1F);
        this.setResistance(5F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }
}
