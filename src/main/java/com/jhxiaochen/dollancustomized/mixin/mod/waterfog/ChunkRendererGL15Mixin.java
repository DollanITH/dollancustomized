package com.jhxiaochen.dollancustomized.mixin.mod.waterfog;

import com.jhxiaochen.dollancustomized.fixes.OptiFineFogHelper;
import meldexun.nothirium.mc.renderer.chunk.ChunkRendererGL15;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChunkRendererGL15.class, remap = false)
public abstract class ChunkRendererGL15Mixin {

    /** GL15 走固定功能 GL 雾，不走着色器：雾关时强制真实 GL 关雾，水面不再挂雾。 */
    @Inject(method = "renderChunks", at = @At("HEAD"), require = 0)
    private void nothiriumCompat$syncFog(CallbackInfo ci) {
        OptiFineFogHelper.syncFog();
    }

}
