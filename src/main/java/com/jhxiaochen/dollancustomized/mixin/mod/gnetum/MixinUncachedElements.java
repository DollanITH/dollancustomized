package com.jhxiaochen.dollancustomized.mixin.mod.gnetum;

import me.decce.gnetum.ElementType;
import me.decce.gnetum.UncachedElements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 修复 Gnetum 缓存导致 MiniHUD 的 FPS 等实时文本刷新滞后 / 卡住的问题。
 *
 * <p>背景：Gnetum 会把 POST 阶段的 modded HUD（含 MiniHUD 的文本）渲染进离屏
 * framebuffer 做缓存，并按 pass 调度 / HUD 刷新率重绘，于是屏幕上的 FPS 数字
 * 是缓存里的旧图，看起来像“显示错误”。
 *
 * <p>Gnetum 的 {@link UncachedElements} 是“强制不缓存”例外表（其 {@code moddedPost}
 * 已收录 ingameinfo 等同类文本 HUD，注释写明“它们有自己的 framebuffer 和刷新率限制”），
 * 但遗漏了 minihud。这里让 {@code has("minihud", POST)} 返回 true，使 MiniHUD 走
 * uncached 直渲路径，FPS / 坐标等文本恢复每帧刷新。
 *
 * <p>注意：仅在安装了 Gnetum 时才注入，见 {@code DollanMixinConfigPlugin#shouldApplyMixin}。
 * 编译需要 Gnetum jar 在 classpath（放入 {@code libs/} 目录即可，见 build 依赖配置）。
 */
@Mixin(UncachedElements.class)
public abstract class MixinUncachedElements {

    /**
     * 对应 Gnetum 源码：{@code public boolean has(String moddedOverlay, ElementType type)}
     * 返回 true 表示“强制不缓存”。在 RETURN 处覆盖，使 MiniHUD 的 POST 元素不被缓存。
     */
    @Inject(
            method = "has(Ljava/lang/String;Lme/decce/gnetum/ElementType;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void dollan$forceUncacheMinihud(String moddedOverlay, ElementType type,
                                            CallbackInfoReturnable<Boolean> cir) {
        // MiniHUD 的 FPS / 坐标等文本在 RenderGameOverlayEvent.Post 阶段渲染
        if ("minihud".equals(moddedOverlay) && type == ElementType.POST && !cir.getReturnValue()) {
            cir.setReturnValue(true);
        }
    }
}
