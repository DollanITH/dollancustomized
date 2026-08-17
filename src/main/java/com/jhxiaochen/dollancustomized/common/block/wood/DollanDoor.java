package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.registry.DollanItems;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class DollanDoor extends BlockDoor {

    public DollanDoor() {
        super(Material.WOOD);
        this.setHardness(3.0F);
    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType sound) {
        this.blockSoundType = sound;
        return this;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(getItem());
    }

    private Item getItem(){
        if (this == WoodBlocks.jacaranda_door) {
            return DollanItems.item_jacaranda_door;
        }
        if (this == WoodBlocks.mangrove_door) {
            return DollanItems.item_mangrove_door;
        }
        return Items.AIR;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
    }
}
