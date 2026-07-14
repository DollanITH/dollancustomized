package com.jhxiaochen.dollancustomized.proxy;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.client.font.CustomTTFFontRenderer;
import com.jhxiaochen.dollancustomized.client.font.TTFFontLoader;
import com.jhxiaochen.dollancustomized.client.renderer.RenderRoadText;
import com.jhxiaochen.dollancustomized.client.renderer.TestRenderHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements CommonProxy {

    public static CustomTTFFontRenderer customFontRenderer;
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init() {
        ResourceLocation ttfLocation = new ResourceLocation(Reference.MOD_ID, "fonts/road_text_fonts.ttf");
        TTFFontLoader fontLoader = new TTFFontLoader(ttfLocation, 32.0F);

        // 2. 实例化自定义渲染器
        customFontRenderer = new CustomTTFFontRenderer(fontLoader);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoadText.class, new RenderRoadText());// 在你的主加载流程中注册一次即可
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeightLimitSignCustom.class, new TestRenderHeightLimitSignCustom());
    }

    @Override
    public void postInit() {

    }




}
