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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SuppressWarnings("Duplicates")
public class BlockMyalite extends Block {
    public static final PropertyEnum<BlockMyalite.MyaliteType> VARIANT = PropertyEnum.<BlockMyalite.MyaliteType>create("variant", BlockMyalite.MyaliteType.class);

    public enum MyaliteType implements IStringSerializable {
        NORMAL(0, "myalite","normal"),
        CRYSTAL(1, "myalite_crystal","crystal"),
        PAVEMENT(2, "myalite_pavement","pavement");


        private static final BlockMyalite.MyaliteType[] METADATA_LOOKUP = new BlockMyalite.MyaliteType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private MyaliteType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private MyaliteType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockMyalite.MyaliteType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockMyalite.MyaliteType blockasphalt$MyaliteType : values()) {
                METADATA_LOOKUP[blockasphalt$MyaliteType.getMetadata()] = blockasphalt$MyaliteType;
            }
        }
    }
        public BlockMyalite(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID + ".myalite");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightLevel(0F);
        this.setLightOpacity(20);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        BlockMyalite.MyaliteType variant = state.getValue(VARIANT);
        switch (variant){
            case NORMAL:
            case PAVEMENT: return true;
            case CRYSTAL: return false;
        }
        return true;
    }
    public boolean isFullCube(IBlockState state){
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, MyaliteType.NORMAL.getMetadata()));
        items.add(new ItemStack(this, 1, MyaliteType.CRYSTAL.getMetadata()));
        items.add(new ItemStack(this,1, MyaliteType.PAVEMENT.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockMyalite.MyaliteType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockMyalite.MyaliteType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockMyalite.MyaliteType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}

