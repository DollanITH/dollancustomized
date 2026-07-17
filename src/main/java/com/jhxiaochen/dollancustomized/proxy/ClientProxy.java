package com.jhxiaochen.dollancustomized.proxy;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.client.font.RoadFontLoader;
import com.jhxiaochen.dollancustomized.client.font.RoadFontRenderer;
import com.jhxiaochen.dollancustomized.client.font.SignFontLoader;
import com.jhxiaochen.dollancustomized.client.font.SignFontRenderer;
import com.jhxiaochen.dollancustomized.client.renderer.RenderHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.client.renderer.RenderRoadText;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements CommonProxy {

    public static RoadFontRenderer roadFontRenderer; // RoadText专用渲染器
    public static SignFontRenderer signFontRenderer;  // 限高牌专用渲染器
    // 保留customFontRenderer以兼容现有代码，但内部会路由到对应的渲染器
    @Deprecated
    public static SignFontRenderer customFontRenderer = signFontRenderer;

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init() {
        ResourceLocation ttfLocation = new ResourceLocation(Reference.MOD_ID, "fonts/road_text_fonts.ttf");

        // RoadText 使用像素风格渲染器（高性能）
        RoadFontLoader roadFontLoader = new RoadFontLoader(ttfLocation, 32.0F);
        roadFontRenderer = new RoadFontRenderer(roadFontLoader);

        // 限高牌使用边缘抗锯齿渲染器（视觉效果更好）
        SignFontLoader signFontLoader = new SignFontLoader(ttfLocation, 32.0F);
        signFontRenderer = new SignFontRenderer(signFontLoader);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoadText.class, new RenderRoadText());
        // 使用点测试渲染器
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeightLimitSignCustom.class, new RenderHeightLimitSignCustom());
    }

    @Override
    public void postInit() {

    }




}
