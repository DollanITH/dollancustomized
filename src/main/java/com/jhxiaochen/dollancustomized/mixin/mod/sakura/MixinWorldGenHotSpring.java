package com.jhxiaochen.dollancustomized.mixin.mod.sakura;

import cn.mcmod.sakura.world.gen.WorldGenHotSpring;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenHotSpring.class)
public class MixinWorldGenHotSpring {

    @Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
    public void Redirect$SaplingGenerate(World worldIn, Random rand, BlockPos position, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
