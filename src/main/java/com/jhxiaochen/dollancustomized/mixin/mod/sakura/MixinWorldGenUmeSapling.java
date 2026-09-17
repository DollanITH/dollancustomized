package com.jhxiaochen.dollancustomized.mixin.mod.sakura;

import cn.mcmod.sakura.world.gen.WorldGenUmeSaping;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(WorldGenUmeSaping.class)
public class MixinWorldGenUmeSapling {

    @Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
    public void Redirect$SaplingGenerate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, CallbackInfo ci) {
        ci.cancel();
    }
}
