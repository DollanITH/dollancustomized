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
public class BlockFrogLight extends Block {
    public static final PropertyEnum<BlockFrogLight.FrogLightType> COLOR = PropertyEnum.<BlockFrogLight.FrogLightType>create("color", BlockFrogLight.FrogLightType.class);

    public static enum FrogLightType implements IStringSerializable {
        PURPLE(0, "purple","purple"),
        ORANGE(1, "orange","orange"),
        GREEN(2, "green","green");

        private static final BlockFrogLight.FrogLightType[] METADATA_LOOKUP = new BlockFrogLight.FrogLightType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private FrogLightType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private FrogLightType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockFrogLight.FrogLightType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockFrogLight.FrogLightType blockasphalt$FrogLightType : values()) {
                METADATA_LOOKUP[blockasphalt$FrogLightType.getMetadata()] = blockasphalt$FrogLightType;
            }
        }
    }
        public BlockFrogLight() {
        super(Material.ROCK);
        this.setRegistryName("froglight");
        this.setUnlocalizedName(Reference.MOD_ID + "." + "froglight");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.2F);
        this.setResistance(12F);
        this.setLightLevel(1.0F);
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
        items.add(new ItemStack(this, 1, FrogLightType.PURPLE.getMetadata()));
        items.add(new ItemStack(this, 1, FrogLightType.ORANGE.getMetadata()));
        items.add(new ItemStack(this, 1, FrogLightType.GREEN.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockFrogLight.FrogLightType)state.getValue(COLOR)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, BlockFrogLight.FrogLightType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockFrogLight.FrogLightType)state.getValue(COLOR)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COLOR});
    }

}

