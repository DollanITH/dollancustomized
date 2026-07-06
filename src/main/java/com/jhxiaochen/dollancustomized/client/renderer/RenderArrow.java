package com.jhxiaochen.dollancustomized.client.renderer;

import com.jhxiaochen.dollancustomized.tileentity.TileEntityArrow;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderArrow extends TileEntitySpecialRenderer<TileEntityArrow> {

    @Override
    public void render(TileEntityArrow te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 如果你的模型仍然由原版 JSON 方块状态渲染，这里留空即可。
        // 这个类的存在本身就会接管并修复视锥剔除范围。
    }

    // 返回 true，让 Minecraft 始终认为这个方块在玩家视野范围内，不被视锥体剔除
    @Override
    public boolean isGlobalRenderer(TileEntityArrow te) {
        return true;
    }

}
