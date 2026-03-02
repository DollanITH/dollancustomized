package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SuppressWarnings("Duplicates")
public class BlockMyalite extends Block {
    public static final PropertyEnum<BlockMyalite.MyaliteType> VARIANT = PropertyEnum.create("variant", BlockMyalite.MyaliteType.class);

    public enum MyaliteType implements IStringSerializable {
        NORMAL(0, "myalite","normal"),
        CRYSTAL(1, "myalite_crystal","crystal"),
        PAVEMENT(2, "myalite_pavement","pavement");


        private static final BlockMyalite.MyaliteType[] METADATA_LOOKUP = new BlockMyalite.MyaliteType[values().length];
        public final int metadata;
        private final String name;
        private final String unlocalizedName;

        MyaliteType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        MyaliteType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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
    }

    public int damageDropped(IBlockState state)
    {
        return ((state.getValue(VARIANT)).getMetadata());
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !(state.getValue(VARIANT).getMetadata() == 1);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        BlockMyalite.MyaliteType variant = state.getValue(VARIANT);
        switch (variant){
            case NORMAL: return BlockFaceShape.SOLID;
            case PAVEMENT: return BlockFaceShape.SOLID;
            case CRYSTAL: return BlockFaceShape.UNDEFINED;
        }
        return BlockFaceShape.UNDEFINED;

    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return !(state.getValue(VARIANT).getMetadata() == 1);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        BlockMyalite.MyaliteType variant = blockState.getValue(VARIANT);
        Block block = iblockstate.getBlock();

        if (variant == MyaliteType.CRYSTAL) {
            if (blockState != iblockstate) {
                return true;
            }
            if (block == this) {
                return false;
            }
            return false;
        }
        return blockState == iblockstate ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
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

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return state != getDefaultState().withProperty(BlockMyalite.VARIANT, MyaliteType.CRYSTAL) ? layer == BlockRenderLayer.SOLID : super.canRenderInLayer(state, layer);
    }

    @Override
    public int getLightOpacity(IBlockState state)
    {
        return state.getValue(BlockMyalite.VARIANT) == BlockMyalite.MyaliteType.CRYSTAL ? 0 : this.lightOpacity;
    }
}

