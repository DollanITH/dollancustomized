package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAsphaltSlab extends Block {
    public static final PropertyEnum<BlockAsphaltSlab.AsphaltType> VARIANT = PropertyEnum.<BlockAsphaltSlab.AsphaltType>create("variant", BlockAsphaltSlab.AsphaltType.class);

    public static enum AsphaltType implements IStringSerializable {
        BLACK(0, "black"),
        RED(1, "red"),
        CYAN(2, "cyan");

        private static final BlockAsphaltSlab.AsphaltType[] METADATA_LOOKUP = new BlockAsphaltSlab.AsphaltType[values().length];
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

        public static BlockAsphaltSlab.AsphaltType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockAsphaltSlab.AsphaltType blockasphaltslab$asphalttype : values()) {
                METADATA_LOOKUP[blockasphaltslab$asphalttype.getMetadata()] = blockasphaltslab$asphalttype;
            }
        }
    }
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    public BlockAsphaltSlab(String name) {
        super(Material.GOURD);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.ROAD_TAB);
        this.setHardness(1.2F);
        this.setResistance(450F);
        this.setLightOpacity(1);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {

        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.CENTER : BlockFaceShape.MIDDLE_POLE;

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB_BOTTOM_HALF;
    }
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, BlockAsphaltSlab.AsphaltType.BLACK.getMetadata()));
        items.add(new ItemStack(this, 1, BlockAsphaltSlab.AsphaltType.CYAN.getMetadata()));
        items.add(new ItemStack(this, 1, BlockAsphaltSlab.AsphaltType.RED.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockAsphaltSlab.AsphaltType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockAsphaltSlab.AsphaltType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockAsphaltSlab.AsphaltType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
}
