package com.jhxiaochen.dollancustomized.common.block.rail;

import com.jhxiaochen.dollancustomized.common.block.rail.base.RailwaySignBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EmptySignBlock extends RailwaySignBase {

    public static final PropertyEnum<EmptySignBlock.SignNone> VARIANT = PropertyEnum.<EmptySignBlock.SignNone>create("variant", EmptySignBlock.SignNone.class);

    public enum SignNone implements IStringSerializable {
        SIGN_NONE_0(0, "sign_none_0"),
        SIGN_NONE_1(1, "sign_none_1");

        private final int index;
        private final String name;

        SignNone(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public static SignNone byIndex(int index) {
            return index == 0 ? SIGN_NONE_0 : SIGN_NONE_1;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        // 牌子后面还带一根 brushed_aluminum 立柱（模型 z: 2 -> 4），碰撞箱取两者并集。
        if (state.getValue(VARIANT) == SignNone.SIGN_NONE_0) {
            return getBoxXZRange(state, 3.0 / 16.0, 13.0 / 16.0, 10.0 / 16.0, 0.0, 4.0 / 16.0);
        }
        return getBoxXZRange(state, 5.0 / 16.0, 11.0 / 16.0, 12.0 / 16.0, 0.0, 4.0 / 16.0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, VARIANT});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3))
                .withProperty(VARIANT, SignNone.byIndex((meta >> 2) & 1));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex()
                | (state.getValue(VARIANT).getIndex() << 2);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand)
                .withProperty(VARIANT, SignNone.byIndex((meta >> 2) & 1));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getIndex() << 2;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 4));
    }
}
