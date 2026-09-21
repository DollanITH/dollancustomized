package com.jhxiaochen.dollancustomized.mixin.mod.gnetum;

import me.decce.gnetum.ElementType;
import me.decce.gnetum.UncachedElements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(UncachedElements.class)
public abstract class MixinUncachedElements {

    @Unique
    private static final Set<String> TARGET_MODS = Set.of("malilib", "minihud");

    @Inject(
            method = "has(Ljava/lang/String;Lme/decce/gnetum/ElementType;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void dollan$forceUncacheMinihud(String moddedOverlay, ElementType type,
                                            CallbackInfoReturnable<Boolean> cir) {
        // MiniHUD 的 FPS / 坐标等文本在 RenderGameOverlayEvent.Post 阶段渲染
        if (type == ElementType.POST) {
            if (TARGET_MODS.contains(moddedOverlay) && !cir.getReturnValue()) {
                cir.setReturnValue(true);
            }
        }
    }
}
