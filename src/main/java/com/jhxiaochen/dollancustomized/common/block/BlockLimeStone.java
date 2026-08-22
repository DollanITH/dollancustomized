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
public class BlockLimeStone extends Block {
    public static final PropertyEnum<BlockLimeStone.LimeStoneType> VARIANT = PropertyEnum.<BlockLimeStone.LimeStoneType>create("variant", BlockLimeStone.LimeStoneType.class);

    public static enum LimeStoneType implements IStringSerializable {
        NORMAL(0, "limestone","normal"),
        BRICKS(1, "limestone_bricks","bricks"),
        PAVEMENT(2, "limestone_pavement","pavement");


        private static final BlockLimeStone.LimeStoneType[] METADATA_LOOKUP = new BlockLimeStone.LimeStoneType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private LimeStoneType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private LimeStoneType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockLimeStone.LimeStoneType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockLimeStone.LimeStoneType blockasphalt$LimeStoneType : values()) {
                METADATA_LOOKUP[blockasphalt$LimeStoneType.getMetadata()] = blockasphalt$LimeStoneType;
            }
        }
    }
        public BlockLimeStone() {
        super(Material.ROCK);
        this.setRegistryName("limestone");
        this.setTranslationKey(Reference.MOD_ID + ".limestone");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(2.0F);
        this.setResistance(15F);
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
        items.add(new ItemStack(this, 1, LimeStoneType.NORMAL.getMetadata()));
        items.add(new ItemStack(this, 1, LimeStoneType.BRICKS.getMetadata()));
        items.add(new ItemStack(this,1, LimeStoneType.PAVEMENT.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockLimeStone.LimeStoneType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockLimeStone.LimeStoneType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockLimeStone.LimeStoneType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}

