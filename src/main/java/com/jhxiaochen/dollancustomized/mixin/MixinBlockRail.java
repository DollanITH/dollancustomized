package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import com.jhxiaochen.dollancustomized.common.rail.RailPlacementTracker;

@Mixin(BlockRail.class)
public abstract class MixinBlockRail extends Block {
    public MixinBlockRail(Material materialIn) {
        super(materialIn);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        // 登记放置者朝向：place() 里没有 placer 参数，只能靠这里带过去。
        RailPlacementTracker.record(world, pos, placer);
        EnumFacing direction = placer.getHorizontalFacing().getOpposite();
        boolean flag1 = direction == EnumFacing.EAST || direction == EnumFacing.WEST;
        return state.withProperty(BlockRail.SHAPE, flag1 ? BlockRailBase.EnumRailDirection.EAST_WEST : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }
}
