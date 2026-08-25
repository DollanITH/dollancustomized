package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFence.class)
public class MixinBlockFence {

    @Inject(method = "canConnectTo", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing, CallbackInfoReturnable<Boolean> cir)
    {
        if (worldIn.getBlockState(pos).getBlock() instanceof BlockGlass) {
            cir.setReturnValue(true);
        }
        
        if (worldIn.getBlockState(pos).getBlock() instanceof BlockStainedGlass) {
            cir.setReturnValue(true);
        }
    }
}
