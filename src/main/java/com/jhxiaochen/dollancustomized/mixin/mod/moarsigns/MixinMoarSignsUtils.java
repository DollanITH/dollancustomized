package com.jhxiaochen.dollancustomized.mixin.mod.moarsigns;

import gory_moon.moarsigns.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 修复 MoarSigns 6.0.0.11 {@code Utils.isUnderlined} 把任何 {@code §} 都当成下划线的问题。
 *
 * <p>原实现等价于：
 * {@code Pattern.compile("(\\{" + (char) 8747 + "n\\})|(" + (char) 167 + ")").matcher(text).find()}
 * 其中 {@code (char) 167} 是裸的 {@code §}。这意味着<strong>任何带格式代码的行</strong>
 * （{@code §l} 加粗、{@code §4} 颜色……）都会被判定为「有下划线」。
 *
 * <p>后果：{@code Utils.getStyleOffset(String, boolean)} 会为这种行白白预留
 * {@code getCharWidth('i')} 的宽度，导致渲染时少显示一个字。
 *
 * <p>修复：正则收紧为只匹配真正的下划线标记 —— {@code {∫n}} 或 {@code §n}。
 *
 * <p><strong>为什么用 Mixin 而不是改源码</strong>：本模组不对 MoarSigns 做 fork，
 * mods 目录里放的是上游原版 jar，因此所有补丁都以 Mixin 形式单方面施加。
 *
 * <p><strong>关于方法名</strong>：{@code isUnderlined} 是 MoarSigns 自有方法，本身不带混淆名，
 * 直接用源码名即可；<strong>不要写 SRG 名</strong>——SRG 名只对 Minecraft 自身的成员成立。
 * 已由 javap 确认 {@code gory_moon.moarsigns.util.Utils} 中就是 {@code isUnderlined}。
 *
 * <p>本 mixin 用 {@code value = Utils.class} 引用目标类（MoarSigns 已是编译期依赖），
 * Mixin AP 能解析它并生成 refmap 条目，这里保留 {@code remap = false} 是因为
 * {@code isUnderlined} 是 mod 自有方法，不应被重映射。
 */
@Mixin(value = Utils.class, priority = 999, remap = false)
public abstract class MixinMoarSignsUtils {

    /**
     * 只匹配 {@code {∫n}}（MoarSigns 的内部下划线标记）或 {@code §n}（原版下划线格式码）。
     *
     * <p>原版 {@code §} 后的合法格式码为 {@code 0-9a-fk-or}，其中只有 {@code n}
     * 表示下划线，故此处只需匹配 {@code §n}。
     */
    private static final java.util.regex.Pattern DOLLAN$UNDERLINE =
            java.util.regex.Pattern.compile("(\\{\u222bn\\})|(\u00a7n)");

    @Inject(method = "isUnderlined", at = @At("HEAD"), cancellable = true, require = 0)
    private static void dollan$onlyRealUnderline(String text, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(text != null && DOLLAN$UNDERLINE.matcher(text).find());
    }
}
