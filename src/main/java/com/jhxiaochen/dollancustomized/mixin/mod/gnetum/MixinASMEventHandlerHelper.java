package com.jhxiaochen.dollancustomized.mixin.mod.gnetum;

import me.decce.gnetum.ASMEventHandlerHelper;
import net.minecraftforge.fml.common.eventhandler.ASMEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ASMEventHandlerHelper.class)
public abstract class MixinASMEventHandlerHelper {

    @Inject(
            method = "tryGetModId(Lnet/minecraftforge/fml/common/eventhandler/ASMEventHandler;)Ljava/lang/String;",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void dollan$recoverMinihudModId(ASMEventHandler asm, CallbackInfoReturnable<String> cir) {
        // 仅当 Gnetum 本身识别失败（Windows 路径匹配不到的典型情况）才介入
        if (cir.getReturnValue() == null) {
            String s = String.valueOf(asm);
            if (s.contains("minihud") || s.contains("malilib")) {
                cir.setReturnValue("minihud");
            }
        }
    }
}
