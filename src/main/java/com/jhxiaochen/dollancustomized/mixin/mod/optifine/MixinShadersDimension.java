package com.jhxiaochen.dollancustomized.mixin.mod.optifine;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.world.DimensionType;
import net.optifine.shaders.Shaders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Shaders.class)
public abstract class MixinShadersDimension {

    @Redirect(
            method = {"getDimensionId", "init"},
            require = 0,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/DimensionType;func_186068_a()I")
    )
    private static int dollanCustomized$fakeDimension0(DimensionType instance) {
        return instance == DollanCustomized.FLAT_DIM
                ? 0
                : instance.getId();
    }
}
