package com.jhxiaochen.dollancustomized.mixin.mod.futuremc;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thedarkcolour.futuremc.world.gen.feature.FlowerWorldGen;

import java.util.Random;

@Mixin(FlowerWorldGen.class)
public abstract class MixinFlowerWorldGen {

    @Inject(method = "generate(Ljava/util/Random;IILnet/minecraft/world/World;Lnet/minecraft/world/gen/IChunkGenerator;Lnet/minecraft/world/chunk/IChunkProvider;)V",
            at = @At(value = "HEAD"), cancellable = true)
    private void formworkGenerate(Random rand, int chunkX, int chunkZ, World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, CallbackInfo ci) {
        if (worldIn.provider.getDimensionType() == DollanCustomized.FLAT_DIM) {
            ci.cancel();
        }
    }
}
