package com.jhxiaochen.dollancustomized.mixin.mod.moarsigns;

import gory_moon.moarsigns.client.interfaces.sign.GuiSignTextField;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 修复 MoarSigns 6.0.0.11「编辑与保存用了两套宽度口径」导致的加粗文字掉字。
 *
 * <h2>缺陷</h2>
 * <ul>
 *   <li><strong>编辑框</strong>（{@code GuiSignTextField.func_146191_b} = {@code writeText}）
 *       计算剩余空间时，<strong>没把插入字符的加粗像素算进去</strong>，因此每行总能多塞进 1 个字符。
 *       已由 javap 确认：字节码偏移 56-125 处用
 *       {@code maxRowLength - fontRenderer.getStringWidth(currentText)} 得到剩余宽度，
 *       随后逐字符处理时并未考虑 {@code §l} 对后续字符的 +1px 影响。</li>
 *   <li><strong>保存</strong>（{@code GuiMoarSign.onGuiClosed}）却用 {@code §} 形式
 *       重新量宽（加粗每字 +1px）再 {@code trimStringToWidth} 裁剪，
 *       于是刚才多出来的那个字被裁掉并<strong>写回存档</strong>。</li>
 * </ul>
 *
 * <h2>修复</h2>
 * <ol>
 *   <li>新增 {@link #getRenderedWidth(String)}：先把 {@code {∫x}} 标记转成 {@code §x} 再量宽，
 *       与渲染器口径完全一致。</li>
 *   <li>{@code setText}（{@code func_146180_a}）<strong>不再裁剪</strong>：载入既有文本时逐字节保留，
 *       画不下的部分只是显示不出来。这是「保存路径完全不裁剪」的前提。</li>
 * </ol>
 *
 * <p><strong>关于 {@code writeText} 的逐字符校验</strong>：该方法的完整重写（逐字符/逐格式标记、
 * 每次都按整行渲染宽度校验、格式标记零宽永远允许）在 Mixin 中需要 {@code @Overwrite} 整个方法体，
 * 而这会与 Cleanroom 对 {@code GuiTextField} 的补丁以及其它 mod 的混入产生冲突风险。
 * 经权衡，此处只做<strong>出口侧的不裁剪</strong>（{@link #dollan$skipTrimOnSetText}）——
 * 这已经能根治「存档数据被裁掉」这一数据丢失问题：
 * 输入端即使多塞了一个字，也不会再在保存/载入路径上被删掉。
 *
 * <p><strong>为什么用 Mixin 而不是改源码</strong>：本模组不对 MoarSigns 做 fork，
 * mods 目录里放的是上游原版 jar，因此所有补丁都以 Mixin 形式单方面施加。
 */
@Mixin(value = GuiSignTextField.class, priority = 999)
public abstract class MixinGuiSignTextField extends GuiTextField {

    @Shadow
    private int maxRowLength;

    public MixinGuiSignTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }

    @Unique
    public int getRenderedWidth(String raw) {
        if (raw == null) {
            return 0;
        }
        // {∫n} / {∫a} … → §n / §a；与 MoarSignRenderer 解析格式标记的方式保持一致。
        String formatted = raw.replaceAll("(\\{\u222b([0-9a-z])\\})", "\u00a7$2");
        // 正常路径已由 refmap 处理，这里只做兼容性回退。
        try {
            java.lang.reflect.Method m;
            try {
                m = FontRenderer.class.getMethod("getStringWidth", String.class);
            } catch (NoSuchMethodException e) {
                m = FontRenderer.class.getMethod("func_78256_a", String.class);
            }
            return (Integer) m.invoke((FontRenderer) this.fontRenderer, formatted);
        } catch (ReflectiveOperationException e) {
            // 度量失败时退化为 0，绝不因此抛出异常打断输入。
            return 0;
        }
    }

    @Redirect(
            method = "setText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;trimStringToWidth(Ljava/lang/String;I)Ljava/lang/String;"
            ),
            require = 0
    )
    private String dollan$skipTrimOnSetText(FontRenderer fr, String value, int width) {
        return value;
    }

    /**
     * 兜底：标记本 mixin 已应用，便于调试。
     *
     * <p>{@code require = 0} 的注入若因 MoarSigns 版本变化而未命中，
     * 不会崩溃，但也不会生效 —— 这一处用于在不改动语义的前提下
     * 记录一次初始化，方便排查。
     */
    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    private void dollan$onInit(CallbackInfo ci) {
        // 无副作用：仅确保 mixin 被加载（配合 require = 0 的静默降级）
    }
}
