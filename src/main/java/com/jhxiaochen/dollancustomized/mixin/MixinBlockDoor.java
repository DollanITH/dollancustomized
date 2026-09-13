package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockDoor.class)
public class MixinBlockDoor {

    @Redirect(method = "neighborChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;isSideSolid(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"))
    private boolean cancelChanged(IBlockState state, IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
        if (state.getBlock() instanceof BlockGlass || state.getBlock() instanceof BlockStainedGlass || state.getBlock() instanceof BlockIce || state.getBlock() instanceof BlockSeaLantern || state.getBlock() instanceof BlockGlowstone) {
            return true;
        }
        return state.isSideSolid(iBlockAccess, blockPos, enumFacing);
    }

    @Inject(method = "canPlaceBlockAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;isTopSolid()Z"), cancellable = true)
    private void canPlace(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        IBlockState state = worldIn.getBlockState(pos.down());
        if (state.getBlock() instanceof BlockGlass || state.getBlock() instanceof BlockIce || state.getBlock() instanceof BlockSeaLantern || state.getBlock() instanceof BlockGlowstone) {
            cir.setReturnValue(true);
        }
        if (state.getBlock() instanceof BlockLeaves) {
            cir.setReturnValue(false);
        }
        if (state.isSideSolid(worldIn, pos, EnumFacing.UP)) {
            cir.setReturnValue(true);
        }
    }
}
