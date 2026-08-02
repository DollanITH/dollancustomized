package com.jhxiaochen.dollancustomized.common.block;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("Duplicates")
public class BlockCrackedDeepSlate extends Block {
    public static final PropertyEnum<BlockCrackedDeepSlate.DeepslateType> VARIANT = PropertyEnum.<BlockCrackedDeepSlate.DeepslateType>create("variant", BlockCrackedDeepSlate.DeepslateType.class);

    public static enum DeepslateType implements IStringSerializable {
        BRICKS(0, "bricks","bricks"),
        TILES(1, "tiles","tiles");


        private static final BlockCrackedDeepSlate.DeepslateType[] METADATA_LOOKUP = new BlockCrackedDeepSlate.DeepslateType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private DeepslateType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private DeepslateType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockCrackedDeepSlate.DeepslateType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockCrackedDeepSlate.DeepslateType blockasphalt$DeepslateType : values()) {
                METADATA_LOOKUP[blockasphalt$DeepslateType.getMetadata()] = blockasphalt$DeepslateType;
            }
        }
    }
        public BlockCrackedDeepSlate(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightLevel(0F);
        this.setSoundType(new SoundType(1.0F, 1.0F, DollanSounds.BLOCK_DEEPSLATE_BREAK, DollanSounds.BLOCK_DEEPSLATE_STEP, DollanSounds.BLOCK_DEEPSLATE_PLACE, SoundEvents.BLOCK_STONE_HIT, DollanSounds.BLOCK_DEEPSLATE_FALL));
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
        items.add(new ItemStack(this, 1, DeepslateType.BRICKS.getMetadata()));
        items.add(new ItemStack(this,1, DeepslateType.TILES.getMetadata()));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((BlockCrackedDeepSlate.DeepslateType)state.getValue(VARIANT)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockCrackedDeepSlate.DeepslateType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockCrackedDeepSlate.DeepslateType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

}

