package com.jhxiaochen.dollancustomized.mixin.mod.moarsigns;

import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 修复在 CleanroomMC 下编辑 MoarSigns 告示牌时「只有第 4 行能调出中文输入法」。
 *
 * <h2>根因</h2>
 * Cleanroom 在 {@code patches/minecraft/net/minecraft/client/gui/GuiTextField.java.patch}
 * 里给 {@code setFocused()} 加了一行：
 * <pre>
 * this.isFocused = isFocusedIn;
 * IMEHandler.setIME(this.isFocused);   // ← Cleanroom 新增
 * </pre>
 * {@code com.cleanroommc.client.IMEHandler} 是一个<strong>全局</strong>开关，不区分是哪个输入框
 * （Windows 实现直接调 {@code ImmAssociateContext(hwnd, null)} 摘掉整个窗口的输入法上下文）。
 * 也就是说：<strong>最后一次调用 {@code setFocused()} 的输入框说了算。</strong>
 *
 * <p>而 6.0.0.11 的 {@code GuiMoarSign.mouseClicked()} 是无脑遍历四个输入框
 * （已由 javap 确认，字节码偏移 391-431）：
 * <pre>
 * for (GuiTextField guiTextField : guiTextFields)
 *     guiTextField.func_146192_a(x, y, b);   // mouseClicked，内部每次都会调 setFocused()
 * </pre>
 * 按索引 {@code 0 → 3} 遍历，<strong>最后一次调用永远来自第 4 行</strong>：
 * <table border="1">
 *   <tr><th>点击的行</th><th>最后一次 setFocused</th><th>IME 状态</th></tr>
 *   <tr><td>第 4 行</td><td>第 4 行 setFocused(true)</td><td>开 ✅</td></tr>
 *   <tr><td>第 1/2/3 行</td><td>第 4 行 setFocused(false)</td><td>关 ❌</td></tr>
 * </table>
 *
 * <p>旁证：旧代码里上下方向键切换行的顺序是对的（先 {@code false} 后 {@code true}），
 * 所以在修复前，用方向键切到第 1 行是可以打中文的，只有鼠标点击不行。
 *
 * <h2>修复</h2>
 * 在 {@code mouseClicked} 完成它原有的遍历后，用 {@link #setSelectedTextField(int)}
 * 统一收口：先让所有非选中行 {@code setFocused(false)}，<strong>最后</strong>才让选中行
 * {@code setFocused(true)}，从而保证 IME 状态由真正被选中的那一行决定。
 *
 * <p>同时 {@code onGuiClosed}（{@code func_146281_b}）里补一次
 * {@code setSelectedTextField(-1)}，关闭界面时把输入法上下文还回去，
 * 避免退出告示牌后游戏里仍然挂着 IME。
 *
 * <p><strong>为什么用 Mixin 而不是改源码</strong>：本模组不对 MoarSigns 做 fork，
 * mods 目录里放的是上游原版 jar，因此所有补丁都以 Mixin 形式单方面施加。
 *
 * <p>字段可见性已由 javap 确认：{@code guiTextFields} 与 {@code selectedTextField}
 * 在 6.0.0.11 中<strong>都是 public</strong>，无需 {@code @Accessor}。
 */
@Mixin(value = GuiMoarSign.class, priority = 999)
public abstract class MixinGuiMoarSign {

    @Shadow
    public gory_moon.moarsigns.client.interfaces.sign.GuiSignTextField[] guiTextFields;

    /** 6.0.0.11 中为 public。 */
    @Shadow
    public int selectedTextField;

    @Unique
    public void setSelectedTextField(int index) {
        this.selectedTextField = index;

        for (int i = 0; i < this.guiTextFields.length; i++) {
            if (this.guiTextFields[i] != null && i != index) {
                dollan$setFocused(this.guiTextFields[i], false);
            }
        }

        if (index != -1 && this.guiTextFields[index] != null) {
            dollan$setFocused(this.guiTextFields[index], true);
        }
    }

    @Unique
    private static void dollan$setFocused(GuiTextField field, boolean focused) {
        try {
            java.lang.reflect.Method m;
            try {
                m = GuiTextField.class.getMethod("setFocused", boolean.class);
            } catch (NoSuchMethodException e) {
                m = GuiTextField.class.getMethod("func_146195_b", boolean.class);
            }
            m.invoke(field, focused);
        } catch (ReflectiveOperationException e) {
            // 焦点设置失败不应打断 GUI，静默降级（IME 修复失效但界面可用）。
        }
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"), require = 0)
    private void dollan$fixImeOnClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        this.setSelectedTextField(this.selectedTextField);
    }

    @Inject(method = "onGuiClosed", at = @At("RETURN"), require = 0)
    private void dollan$releaseImeOnClose(CallbackInfo ci) {
        this.setSelectedTextField(-1);
    }

    @Redirect(
            method = "onGuiClosed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;trimStringToWidth(Ljava/lang/String;I)Ljava/lang/String;"
            ),
            require = 0
    )
    private String dollan$skipSaveTrim(net.minecraft.client.gui.FontRenderer fr, String value, int width) {
        // 保存路径完全不裁剪：存档文本逐字节保留。
        return value;
    }
}
