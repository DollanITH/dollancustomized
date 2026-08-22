package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.access.IAxis;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DollanStandingSign extends DollanSign implements IAxis {
    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

    public DollanStandingSign() {
        super();
        this.setCreativeTab(DollanCustomized.WOOD_TAB).setHardness(1.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos clickPos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing clickFacing, float hitX, float hitY, float hitZ) {
        if (clickFacing == EnumFacing.UP || clickFacing == EnumFacing.DOWN) return false;

        // 获取点击面
        int slot = this.getSlotFromFacings(state.getValue(ROTATION), clickFacing, player);
        if (slot == -1) return false;

        TileEntityDollanSign tile = (TileEntityDollanSign) world.getTileEntity(clickPos);

        // 获取手持物
        ItemStack heldItem = player.getHeldItem(hand);

        // 潜行右键切换编辑面
        if (player.isSneaking()) {
            return com.jhxiaochen.dollancustomized.common.interaction.SignInteractionHandler.handleSignClick(
                world, clickPos, player, true);
        }

        // 尝试打蜡
        if (tile.tryWaxed(slot, heldItem)) {
            // 减少物品栏物品
            if (!player.capabilities.isCreativeMode) heldItem.shrink(1);
            return true;
        }

        // 检查是否可以编辑
        if (!com.jhxiaochen.dollancustomized.common.interaction.SignInteractionHandler.canEditSign(world, clickPos, player)) {
            return false;
        }

        // 编辑文字
        return tile.editText(slot, player);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.getBlockState(pos.down()).getMaterial().isSolid())
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(ROTATION)).intValue();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(ROTATION, Integer.valueOf(rot.rotate(((Integer)state.getValue(ROTATION)).intValue(), 16)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withProperty(ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(((Integer)state.getValue(ROTATION)).intValue(), 16)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ROTATION});
    }

}