package com.jhxiaochen.dollancustomized.common;


import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.item.ItemStack;


public class BlockTranLight extends BlockColored
    {
    public BlockTranLight(String name)
        {
            super(Material.GLASS);
            this.setUnlocalizedName(name);
            this.setCreativeTab(DollanCustomized.LIGHT_TAB);
            this.setSoundType(SoundType.GLASS);
            this.setLightLevel(1.0F);
            this.setResistance(15F);
            this.setLightOpacity(1);
            this.isAmbientOcclusion();
        }
        public boolean isAmbientOcclusion()
        {
            return false;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }
        @Override
        public boolean isFullCube(IBlockState state) {
            return true;
        }
    }
