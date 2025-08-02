package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
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
public class BlockAsphalt extends Block {
    public static final PropertyEnum<BlockAsphalt.AsphaltType> VARIANT = PropertyEnum.<BlockAsphalt.AsphaltType>create("variant", BlockAsphalt.AsphaltType.class);
    public static enum AsphaltType implements IStringSerializable {
        BLACK(0, "black"),
        RED(1, "red"),
        CYAN(2, "cyan"),
        YELLOW(3,"yellow"),
        WHITE(4,"white");

        private static final BlockAsphalt.AsphaltType[] METADATA_LOOKUP = new BlockAsphalt.AsphaltType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private AsphaltType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private AsphaltType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockAsphalt.AsphaltType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockAsphalt.AsphaltType blockasphalt$asphalttype : values()) {
                METADATA_LOOKUP[blockasphalt$asphalttype.getMetadata()] = blockasphalt$asphalttype;
            }
        }
    }
        public BlockAsphalt(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.ROAD_TAB);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightLevel(0F);
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
        items.add(new ItemStack(this, 1, AsphaltType.BLACK.getMetadata()));
        items.add(new ItemStack(this, 1, AsphaltType.RED.getMetadata()));
        items.add(new ItemStack(this, 1, AsphaltType.CYAN.getMetadata()));
        items.add(new ItemStack(this,1,AsphaltType.WHITE.getMetadata()));
        items.add(new ItemStack(this,1,AsphaltType.YELLOW.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockAsphalt.AsphaltType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockAsphalt.AsphaltType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockAsphalt.AsphaltType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}

