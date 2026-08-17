package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.registry.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DollanLog extends BlockLog {

    public static final PropertyEnum<EnumAxis> AXIS = PropertyEnum.create("axis", EnumAxis.class);

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS);
    }

    public DollanLog(Block stripped) {
        super();
        this.stripped = stripped;
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.Y));
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(2.0F).setResistance(5.0F);
    }

    public DollanLog() {
        super();
        this.stripped = null;
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.Y));
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(2.0F).setResistance(5.0F);
    }

    private final Block stripped;

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 0:
                iblockstate = iblockstate.withProperty(AXIS, EnumAxis.Y);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(AXIS, EnumAxis.X);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(AXIS, EnumAxis.Z);
                break;
        }

        return iblockstate;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i;

        switch (state.getValue(AXIS))
        {
            case X:
                i = 1;
                break;
            case Z:
                i = 2;
                break;
            case Y:
            default:
                i = 0;
        }

        return i;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        // 检查是否是斧头
        if (stack.getItem() instanceof ItemAxe && hand == EnumHand.MAIN_HAND) {
            // 如果是原木（不是去皮的），可以去皮
            if (stripped != null) {
                // 替换方块为去皮原木
                world.setBlockState(pos, stripped.getDefaultState().withProperty(AXIS, state.getValue(AXIS)), 3);

                // 添加声音效果
                world.playSound(null, pos, DollanSounds.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

                int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);

                boolean damage = true;
                if (efficiencyLevel > 0) {
                    if (Math.random() * 10 < efficiencyLevel) {
                        damage = false;
                    }
                }

                if (damage) {
                    stack.damageItem(1, player);
                }


                return true;
            }
        }

        return false;
    }

    public enum EnumAxis implements IStringSerializable
    {
        X("x"),
        Y("y"),
        Z("z");

        private final String name;

        EnumAxis(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public static EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Z:
                    return Z;
                case Y:
                default:
                    return Y;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch (state.getValue(AXIS))
                {
                    case X:
                        return state.withProperty(AXIS, EnumAxis.Z);
                    case Z:
                        return state.withProperty(AXIS, EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType sound) {
        this.blockSoundType = sound;
        return this;
    }

}