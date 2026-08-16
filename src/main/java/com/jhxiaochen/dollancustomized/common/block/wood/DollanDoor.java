package com.jhxiaochen.dollancustomized.common.block.wood;

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
    private Item doorItem;

    public DollanDoor() {
        this(null);
    }

    public DollanDoor(Item doorItem) {
        super(Material.WOOD);
        this.doorItem = doorItem;
        this.setHardness(3.0F);
    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType sound) {
        this.blockSoundType = sound;
        return this;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        if (doorItem != null) {
            return new ItemStack(doorItem);
        }
        return new ItemStack(Items.OAK_DOOR);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(HALF) == EnumDoorHalf.UPPER) {
            return Items.AIR;
        }
        return doorItem != null ? doorItem : Items.OAK_DOOR;
    }

    public DollanDoor setDoorItem(Item doorItem) {
        this.doorItem = doorItem;
        return this;
    }
}
