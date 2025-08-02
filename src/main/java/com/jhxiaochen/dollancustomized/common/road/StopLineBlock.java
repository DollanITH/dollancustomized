package com.jhxiaochen.dollancustomized.common.road;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class StopLineBlock extends BlockAsphaltHorizontal
{
    public enum EnumVarient implements IStringSerializable {
        NORMAL(0,"stop_line","normal"),
        DIAGONAL_1(1,"stop_line_diagonal_1","diagonal_1"),
        DIAGONAL_2(2,"stop_line_diagonal_2","diagonal_2");

        final int metadata;
        private final String name;
        private final String unlocalizedName;
        private static final EnumVarient[] METADATA_LOOKUP = new EnumVarient[values().length];
        EnumVarient(int metadataIn ,String name, String unlocalizedNameIn) {
            this.metadata = metadataIn;
            this.name = name;
            this.unlocalizedName = unlocalizedNameIn;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        public static EnumVarient byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }


        public int getMetadata() {
            return this.metadata;
        }

        static {
            for (EnumVarient varient : values()) {
                METADATA_LOOKUP[varient.getMetadata()] = varient;
            }
        }

    }
    public static final PropertyEnum<EnumVarient>
            VARIENT = PropertyEnum.create("varient", EnumVarient.class);

    public StopLineBlock() {
        super("stop_line");
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 4));
        items.add(new ItemStack(this, 1, 8));
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumVarient varient = EnumVarient.NORMAL;
        if ((meta & 0x8) != 0) varient = EnumVarient.DIAGONAL_1;
        else if ((meta & 0x4) != 0) varient = EnumVarient.DIAGONAL_2;
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(VARIENT, varient);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumVarient varient = state.getValue(VARIENT);
        int v = 0x0;
        if (varient == EnumVarient.DIAGONAL_1) v = 0x8;
        else if (varient == EnumVarient.DIAGONAL_2) v = 0x4;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, (state.getValue(VARIENT)).getMetadata());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, VARIENT});
    }

}
