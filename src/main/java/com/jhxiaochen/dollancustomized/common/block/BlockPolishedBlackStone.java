package com.jhxiaochen.dollancustomized.common.block;

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
public class BlockPolishedBlackStone extends Block {
    public static final PropertyEnum<BlockPolishedBlackStone.BlackStoneType> VARIANT = PropertyEnum.<BlockPolishedBlackStone.BlackStoneType>create("variant", BlockPolishedBlackStone.BlackStoneType.class);

    public static enum BlackStoneType implements IStringSerializable {
        NORMAL(0, "polished_blackstone","backstone"),
        BRICKS(1, "polished_blackstone_bricks","bricks");

        private static final BlockPolishedBlackStone.BlackStoneType[] METADATA_LOOKUP = new BlockPolishedBlackStone.BlackStoneType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private BlackStoneType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private BlackStoneType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockPolishedBlackStone.BlackStoneType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockPolishedBlackStone.BlackStoneType blockasphalt$BlackStoneType : values()) {
                METADATA_LOOKUP[blockasphalt$BlackStoneType.getMetadata()] = blockasphalt$BlackStoneType;
            }
        }
    }
        public BlockPolishedBlackStone() {
        super(Material.ROCK);
        this.setRegistryName("polished_blackstone");
        this.setTranslationKey(Reference.MOD_ID + "." + "polished_blackstone");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(2.3F);
        this.setResistance(20F);
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
        items.add(new ItemStack(this, 1, BlackStoneType.NORMAL.getMetadata()));
        items.add(new ItemStack(this, 1, BlackStoneType.BRICKS.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockPolishedBlackStone.BlackStoneType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockPolishedBlackStone.BlackStoneType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockPolishedBlackStone.BlackStoneType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}
