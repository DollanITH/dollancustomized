package com.jhxiaochen.dollancustomized.fixes;

import net.minecraft.client.renderer.OpenGlHelper;

/**
 * OptiFine 光影兼容：方块实体（TESR）自定义字体渲染的顶点光照贴图辅助。
 *
 * <p><b>问题现象</b>：开启 OptiFine 光影后，地铁大字壁（{@code StationNameBoardRenderer}）、
 * 自定义文字标线（{@code RenderRoadText}）、限高牌等由 TESR 绘制的文字会变黑、被染成
 * 环境色并显得半透明。</p>
 *
 * <p><b>根本原因</b>：这些文字原先都通过
 * {@code DefaultVertexFormats.POSITION_TEX_COLOR} 画四边形。该顶点格式<b>不含
 * lightmap 元素</b>。原版下文字亮度靠固定管线读取“当前 lightmap 状态”尚能正常，
 * 但 OptiFine 光影是延迟渲染：顶点着色器需要逐顶点的 {@code gl_MultiTexCoord1}
 * （lightmap），缺失时按 0 处理，于是延迟光照把文字算成了全黑，并在合成阶段与
 * 背景混合，看起来又暗又透。</p>
 *
 * <p><b>修复方式</b>：改用含 lightmap 的 {@code DefaultVertexFormats.POSITION_TEX_LMAP_COLOR}
 * （与原版粒子同一格式，已确认在光影下工作正常），并逐顶点写入当前 lightmap
 * 坐标。取值与 {@code TileEntityRendererDispatcher} 为方块实体设置的一致
 * （{@code setLightmapTextureCoords(unit, i % 65536, i / 65536)}），
 * 即 U = 方块光、V = 天空光，从而在光影下还原原版的文字亮度与颜色。</p>
 */
public final class ShaderTextLightmap {

    private ShaderTextLightmap() {
    }

    /** lightmap 的 U 分量（方块光），与固定管线当前状态一致。 */
    public static int u() {
        return (int) OpenGlHelper.lastBrightnessX;
    }

    /** lightmap 的 V 分量（天空光），与固定管线当前状态一致。 */
    public static int v() {
        return (int) OpenGlHelper.lastBrightnessY;
    }
}
