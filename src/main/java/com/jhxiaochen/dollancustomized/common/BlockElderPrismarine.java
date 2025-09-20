package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("Duplicates")
public class BlockElderPrismarine extends Block {
    public static final PropertyEnum<BlockElderPrismarine.PrismarineType> VARIANT = PropertyEnum.<BlockElderPrismarine.PrismarineType>create("variant", BlockElderPrismarine.PrismarineType.class);

    public enum PrismarineType implements IStringSerializable {
        NORMAL(0, "elder_prismarine","normal"),
        BRICKS(1, "elder_prismarine_bricks","bricks"),
        SMOOTH(2, "smooth_elder_prismarine","smooth");


        private static final BlockElderPrismarine.PrismarineType[] METADATA_LOOKUP = new BlockElderPrismarine.PrismarineType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        PrismarineType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        PrismarineType(int metadataIn, String nameIn, String unlocalizedNameIn) {
            this.metadata = metadataIn;
            this.name = nameIn;
            this.unlocalizedName = unlocalizedNameIn;
        }

        public int getMetadata() {
            return this.metadata;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public String toString() {
            return this.name;
        }

        public static BlockElderPrismarine.PrismarineType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockElderPrismarine.PrismarineType blockasphalt$PrismarineType : values()) {
                METADATA_LOOKUP[blockasphalt$PrismarineType.getMetadata()] = blockasphalt$PrismarineType;
            }
        }
    }
        public BlockElderPrismarine(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID + ".elder_prismarine");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightLevel(0F);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, PrismarineType.NORMAL.getMetadata()));
        items.add(new ItemStack(this, 1, PrismarineType.BRICKS.getMetadata()));
        items.add(new ItemStack(this,1, PrismarineType.SMOOTH.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockElderPrismarine.PrismarineType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockElderPrismarine.PrismarineType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockElderPrismarine.PrismarineType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}

