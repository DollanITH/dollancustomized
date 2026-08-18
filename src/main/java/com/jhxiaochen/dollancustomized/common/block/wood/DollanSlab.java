package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class DollanSlab extends BlockSlab {
    public static final PropertyEnum<EnumType>
                                VARIANT = PropertyEnum.create("variant", EnumType.class);
    private ItemSlab itemSlab;
    public DollanSlab() {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    public void setItem(Item item) {
        if (this.itemSlab == null) {
            itemSlab = (ItemSlab) item;
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iBlockState = this.getDefaultState().withProperty(VARIANT, EnumType.NULL);
        return !isDouble() ? iBlockState.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP) : iBlockState;
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (!isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName( );
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumType.NULL;
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(itemSlab);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess access, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(
                itemSlab,
                this.isDouble() ? 2 : 1
        ));
    }

    enum EnumType implements IStringSerializable {
        NULL("null");

        private final String name;

        EnumType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, VARIANT, HALF);
    }

    public static class DollanDoubleSlab extends DollanSlab {
        public DollanDoubleSlab() {
            super();
        }

        @Override
        public boolean isDouble() {
            return true;
        }
    }

    public static class DollanHalfSlab extends DollanSlab {

        public DollanHalfSlab() {
            super();
            this.setLightOpacity(0);
        }

        @Override
        public boolean isDouble() {
            return false;
        }
    }
}
