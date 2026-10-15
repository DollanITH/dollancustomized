package com.jhxiaochen.dollancustomized.mixin.mod.moarsigns;

import gory_moon.moarsigns.client.renderers.MoarSignRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

/**
 * 修复 MoarSigns 6.0.0.11 {@code MoarSignRenderer.render} 中多余的
 * {@code GuiUtilRenderComponents.splitText()} 调用导致的「显示少一个字」。
 *
 * <p><strong>背景</strong>：Cleanroom 重写了 {@code splitText}
 * （{@code patches/minecraft/net/minecraft/client/gui/GuiUtilRenderComponents.java.patch}）
 * 的断行逻辑：
 * <pre>
 * -  int l = s2.lastIndexOf(32);          // 原版：找最后一个空格
 * +  ICU4JInstances.BREAK_ITERATOR.setText(s4);
 * +  int l = ICU4JInstances.BREAK_ITERATOR.preceding(s2.length());
 * </pre>
 * 中文没有空格：<strong>原版</strong>找不到断点（{@code lastIndexOf == -1}）就整段保留；
 * 而 <strong>ICU BreakIterator</strong> 认为每两个汉字之间都可断行，于是又往回缩了一个字。
 *
 * <p><strong>为什么这次 splitText 是有害的</strong>：{@code render} 在它紧后面
 * （已由 javap 确认字节码顺序）本来就已经按该行真实宽度裁剪过一次：
 * <pre>
 *  738: invokestatic  GuiUtilRenderComponents.func_178908_a(...)    // splitText
 *  769: invokeinterface ITextComponent.func_150254_d()              // getFormattedText
 *  817: invokevirtual FontRenderer.func_78256_a(String)             // getStringWidth
 *  823: invokevirtual FontRenderer.func_78269_a(String, int)        // trimStringToWidth
 *  871: invokevirtual FontRenderer.func_175065_a(String, ...)       // drawString
 * </pre>
 * 也就是说后面那次裁剪已经能保证不溢出，前面这次 {@code splitText} 纯属多余，
 * 只会把 ICU 多缩掉的那个字永久丢掉。只有加粗行会超过 90px，所以只有加粗告示牌受影响。
 *
 * <p><strong>修复</strong>：让 {@code render} 直接拿到完整的 {@code getFormattedText()}
 * 结果，跳过 {@code splitText} 的分行，交由后续既有的按行宽裁剪处理。
 *
 * <p>用 {@code @Redirect} 而非 {@code @Overwrite}：只改写这一个调用点，
 * 其余渲染逻辑（矩阵变换、GlStateManager 状态、纹理绑定）全部保持上游原样。
 */
@Mixin(value = MoarSignRenderer.class, priority = 999)
public abstract class MixinMoarSignRenderer {

    /**
     * 拦截 {@code render} 里对 {@code splitText} 的调用，改为不进行 ICU 断行、整段返回。
     *
     * <p>目标方法名用 <strong>{@code render}</strong>：这是 MoarSigns 覆写
     * {@code TileEntitySpecialRenderer} 的方法，在 SRG jar 中同样保持 {@code render}
     * （已由 javap 确认；第三方 mod 的覆写方法不会变成 {@code func_} 名）。
     * 之前这里写的 {@code func_192841_a} 是 Minecraft {@code TileEntitySpecialRenderer}
     * 的 SRG 名，MoarSigns 里<strong>根本不存在</strong>，配合 {@code require = 0}
     * 只会静默失效。
     *
     * <p>调用点写 MCP 名 {@code splitText}，由 refmap 自动重映射为
     * {@code func_178908_a}。本 mixin 用 {@code value = MoarSignRenderer.class}
     * 引用目标类，Mixin AP 能解析它，因此 refmap 条目可以正常生成。
     *
     * <p><strong>必须写全签名</strong>：MoarSigns 里有两个 {@code render} ——
     * {@code render(TileEntityMoarSign, ...)} 和编译器生成的桥接方法
     * {@code render(TileEntity, ...)}。只写 {@code render} 会让 Mixin 无法判断
     * 注入哪一个（{@code FLAG_UNIQUE} 歧义），因此这里显式给出参数签名。
     */
    @Redirect(
            method = "render(Lgory_moon/moarsigns/tileentites/TileEntityMoarSign;DDDFIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiUtilRenderComponents;splitText(Lnet/minecraft/util/text/ITextComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;"
            ),
            require = 0
    )
    private List<ITextComponent> dollan$skipSplitText(ITextComponent component, int maxWidth,
                                                      FontRenderer font, boolean p1, boolean p2) {
        // 不做 ICU 断行：把整段文本作为单行返回，交由 render 中紧随其后的
        // trimStringToWidth 按真实宽度裁剪。
        return java.util.Collections.singletonList(component);
    }
}
