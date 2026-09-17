package com.jhxiaochen.dollancustomized.mixin.mod.waterfog;

import com.jhxiaochen.dollancustomized.fixes.OptiFineFogHelper;
import meldexun.nothirium.mc.util.FogUtil;
import meldexun.renderlib.util.GLShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FogUtil.class, remap = false)
public abstract class FogUtilMixin {

    /**
     * 2) 雾=关时强制着色器不渲染雾（水面/陆地一致），尊重 OptiFine。
     *    运行时反射读 Config.isFogOff()，安全，不会触发启动崩溃。
     */
    @Inject(method = "setupFogFromGL", at = @At("HEAD"), require = 0)
    private static void nothiriumCompat$syncFogToOptifine(GLShader shader, CallbackInfo ci) {
        OptiFineFogHelper.syncFog();
    }

}
