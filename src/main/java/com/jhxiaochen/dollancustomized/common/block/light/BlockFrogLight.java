package com.jhxiaochen.dollancustomized.common.block.light;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
    public static final PropertyEnum<BlockFrogLight.FrogLightType> COLOR = PropertyEnum.create("color", BlockFrogLight.FrogLightType.class);

    public enum FrogLightType implements IStringSerializable {
        PURPLE(0, "purple"),
        ORANGE(1, "orange"),
        GREEN(2, "green");

        private static final BlockFrogLight.FrogLightType[] METADATA_LOOKUP = new BlockFrogLight.FrogLightType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        FrogLightType(int metadataIn, String nameIn) {
            this.metadata = metadataIn;
            this.name = nameIn;
            this.unlocalizedName = nameIn;
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
        this.setTranslationKey(Reference.MOD_ID + "." + "froglight");
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setSoundType(SoundType.GLASS);
        this.setHardness(1.2F);
        this.setResistance(12F);
        this.setLightLevel(1.0F);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
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
        return new ItemStack(this, 1, (state.getValue(COLOR)).getMetadata());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, BlockFrogLight.FrogLightType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR);
    }

}

