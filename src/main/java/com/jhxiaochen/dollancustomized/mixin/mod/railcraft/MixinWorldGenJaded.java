package com.jhxiaochen.dollancustomized.mixin.mod.railcraft;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import mods.railcraft.common.worldgen.WorldGenJaded;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenJaded.class)
public class MixinWorldGenJaded {
    @Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
    private void FormworkGenerate(World world, Random rand, BlockPos position, CallbackInfoReturnable<Boolean> cir) {
        if (world.provider.getDimensionType() == DollanCustomized.FLAT_DIM) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        if (world.getWorldType() == WorldType.FLAT)
        {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
