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
public class BlockMarble extends Block {
    public static final PropertyEnum<BlockMarble.MarbleType> VARIANT = PropertyEnum.<BlockMarble.MarbleType>create("variant", BlockMarble.MarbleType.class);
    public static enum MarbleType implements IStringSerializable {
        NORMAL(0, "normal","normal"),
        BRICKS(1, "bricks","bricks"),
        CUT(2, "cut","cut"),
        LITCUT(3, "litcut","litcut"),
        SMOOTH(4, "smooth","smooth");


        private static final BlockMarble.MarbleType[] METADATA_LOOKUP = new BlockMarble.MarbleType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private MarbleType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private MarbleType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        @Override
        public String toString() {
            return this.name;
        }

        public static BlockMarble.MarbleType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        @Override
        public String getName() {
            return this.name;
        }

        static {
            for (BlockMarble.MarbleType blockasphalt$MarbleType : values()) {
                METADATA_LOOKUP[blockasphalt$MarbleType.getMetadata()] = blockasphalt$MarbleType;
            }
        }
    }
        public BlockMarble(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID + ".beautiful_marble");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightLevel(0F);
    }

    public int damageDropped(IBlockState state)
    {
        return ((state.getValue(VARIANT)).getMetadata());
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
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
            items.add(new ItemStack(this, 1, 2));
            items.add(new ItemStack(this, 1, 3));
            items.add(new ItemStack(this, 1, 4));
    }

   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockMarble.MarbleType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockMarble.MarbleType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockMarble.MarbleType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}

