package com.jhxiaochen.dollancustomized.common.road;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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

    public int damageDropped(IBlockState state)
    {
        return ((state.getValue(VARIENT)).getMetadata());
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumVarient subtype : EnumVarient.values( )) {
        items.add(new ItemStack(this, 1, subtype.getMetadata( )));
        }
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumVarient varient = EnumVarient.NORMAL;
        if ((meta & 0x4) != 0) varient = EnumVarient.DIAGONAL_1;
        else if ((meta & 0x8) != 0) varient = EnumVarient.DIAGONAL_2;
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(VARIENT, varient);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumVarient varient = state.getValue(VARIENT);
        int v = 0x0;
        if (varient == EnumVarient.DIAGONAL_1) v = 0x4;
        else if (varient == EnumVarient.DIAGONAL_2) v = 0x8;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        super.getPickBlock(state, target, world, pos, player);
        return new ItemStack(this, 1 , (state.getValue(VARIENT).getMetadata()));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, VARIENT});
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        EnumVarient varient = EnumVarient.byMetadata(meta);
        state = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(VARIENT, varient);
        return state;
    }

}
