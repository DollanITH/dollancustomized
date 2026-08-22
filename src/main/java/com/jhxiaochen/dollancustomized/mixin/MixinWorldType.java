package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldType.class)
public class MixinWorldType {
    @Shadow
    @Final
    public static WorldType FLAT;

    @Inject(method = "voidFadeMagnitude()D", at = @At(value = "RETURN"), cancellable = true, remap = false)
    // 4. 参数必须是 CallbackInfoReturnable<Double>，注意是包装类 Double
    private void dollanModifyVoidFade(CallbackInfoReturnable<Double> cir) {

        World world = Minecraft.getMinecraft().world;
        if ((Object) this != FLAT) {
            cir.setReturnValue(1.0D);
            return;
        }
        if (world.provider.getDimensionType() == DollanCustomized.FLAT_DIM) {
            cir.setReturnValue(1.0D);
            return;
        }
        cir.cancel();
    }

    @Inject(method = "getCloudHeight()F", at = @At(value = "RETURN") , cancellable = true, remap = false)
    private void voidGetCloudHeight(CallbackInfoReturnable<Float> cir)
    {
        World world = Minecraft.getMinecraft().world;
        if ((Object) this == FLAT || world.provider.getDimensionType() == DollanCustomized.FLAT_DIM) {
            cir.cancel();
            return;
        }
        cir.setReturnValue(256.0F);
    }

}
