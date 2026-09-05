package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFenceGate.class)
public class MixinBlockFenceGate extends Block {

    public MixinBlockFenceGate(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "canPlaceBlockAt", at = @At(value = "HEAD"), cancellable = true)
    public void canPlaceBlockAt(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canPlaceBlockAt(worldIn, pos));
    }
}
