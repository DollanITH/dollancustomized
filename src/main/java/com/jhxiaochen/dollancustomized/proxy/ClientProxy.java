package com.jhxiaochen.dollancustomized.proxy;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.client.font.*;
import com.jhxiaochen.dollancustomized.client.font.chinese.ChineseFontLoader;
import com.jhxiaochen.dollancustomized.client.font.chinese.ChineseFontRenderer;
import com.jhxiaochen.dollancustomized.client.particle.ParticleTextureRegister;
import com.jhxiaochen.dollancustomized.client.renderer.road.RenderHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.client.renderer.road.RenderRoadText;
import com.jhxiaochen.dollancustomized.client.renderer.sign.HangingSignRenderer;
import com.jhxiaochen.dollancustomized.client.renderer.sign.SignRenderer;
import com.jhxiaochen.dollancustomized.client.renderer.station.StationNameBoardRenderer;
import com.jhxiaochen.dollancustomized.client.resourcepack.ModPack;
import com.jhxiaochen.dollancustomized.common.tileentity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeEarlyConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

public class ClientProxy implements IProxy {

    public static ModPack modPack;


    // ============================== 字体渲染器 ==============================

    public static RoadFontRenderer roadFontRenderer;      // RoadText专用
    public static SignFontRenderer signFontRenderer;      // 限高牌专用
    public static ChineseFontRenderer chineseFontRenderer;
    public static ChineseFontRenderer chineseFontRenderer_1;
    public static ChineseFontRenderer chineseFontRenderer_2;
    public static EnglishFontRenderer englishFontRenderer;

    /** 保留以兼容现有代码，内部路由到对应的渲染器 */
    @Deprecated
    public static SignFontRenderer customFontRenderer;

    private final ResourceLocation ttfLocation  = new ResourceLocation(Reference.MOD_ID, "fonts/road_text_fonts.ttf");
    private final ResourceLocation ttfLocation1 = new ResourceLocation(Reference.MOD_ID, "fonts/source-sans-pro-bold-2.otf");
    private final ResourceLocation ttfLocation2 = new ResourceLocation(Reference.MOD_ID, "fonts/sourcehansanscn-bold.otf");
    private final ResourceLocation ttfLocation3 = new ResourceLocation(Reference.MOD_ID, "fonts/chinese_1.ttf");
    private final ResourceLocation ttfLocation4 = new ResourceLocation(Reference.MOD_ID, "fonts/chinese_2.ttf");

    /** 记录上一帧是否在资源包界面，用于检测"离开界面 = 玩家点完完成按钮" */
    private boolean lastGuiWasPackScreen = false;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle("DollanITH Client V" + Reference.VERSION);
        // 强制无边框窗口使用显示器精确尺寸（关闭 lwjglxx 在 Windows 下对高度 +1 的兼容处理）
        java.io.File src = event.getSourceFile();
        DollanCustomized.LOGGER.info("[preInit] sourceFile={}, 存在={}", src.getAbsolutePath(), src.exists());
        modPack = new ModPack(src);
        DollanCustomized.LOGGER.info("[preInit] modPack 已创建: {}", modPack.getClass().getName());
        // defaultResourcePacks 挂载这步先删掉（上一版有 MinecraftAccessor 那行的话），
        // 现在只留 GUI 注入一条路，减少变量
    }

    @Override
    public void init(FMLInitializationEvent event) {

        // 初始化粒子系统
        ParticleTextureRegister.preloadTextures();

        // RoadText 使用像素风格渲染器（高性能）
        RoadFontLoader roadFontLoader = new RoadFontLoader(ttfLocation, 32.0F);
        roadFontRenderer = new RoadFontRenderer(roadFontLoader);

        // 限高牌使用边缘抗锯齿渲染器（视觉效果更好）
        SignFontLoader signFontLoader = new SignFontLoader(ttfLocation, 32.0F);
        signFontRenderer = new SignFontRenderer(signFontLoader);
        customFontRenderer = signFontRenderer; // 修正：字段初始化时 signFontRenderer 还是 null

        ChineseFontLoader chineseFontLoader = new ChineseFontLoader(ttfLocation2, 32.0F);
        chineseFontRenderer = new ChineseFontRenderer(chineseFontLoader);

        ChineseFontLoader chineseFontLoader_1 = new ChineseFontLoader(ttfLocation3, 32.0F);
        chineseFontRenderer_1 = new ChineseFontRenderer(chineseFontLoader_1);

        ChineseFontLoader chineseFontLoader_2 = new ChineseFontLoader(ttfLocation4, 32.0F);
        chineseFontRenderer_2 = new ChineseFontRenderer(chineseFontLoader_2);

        EnglishFontLoader englishFontLoader = new EnglishFontLoader(ttfLocation1, 32.0F);
        englishFontRenderer = new EnglishFontRenderer(englishFontLoader);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoadText.class, new RenderRoadText());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeightLimitSignCustom.class, new RenderHeightLimitSignCustom());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDollanSign.class, new SignRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDollanHangingSign.class, new HangingSignRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStationNameBoard.class, new StationNameBoardRenderer());

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (modPack != null) {
            Minecraft mc = Minecraft.getMinecraft();
            // 触发 TAIL：注入 entry + ensureDefaultSelected 写名单
            mc.getResourcePackRepository().updateRepositoryEntriesAll();
            // 按最新名单重新绑定并加载资源（getRepositoryEntries 每次按名字实时查 entry）
            mc.refreshResources();
        }
    }

    @Override
    public void contruction(FMLLoadCompleteEvent event) {
        Display.setTitle("DollanITH Client V" + Reference.VERSION);
        ForgeEarlyConfig.WINDOW_BORDERLESS_WINDOWS_COMPATIBILITY = false;
        ForgeEarlyConfig.WINDOW_BORDERLESS_REPLACES_FULLSCREEN = true;
        ConfigManager.sync(ForgeEarlyConfig.class);
        DollanCustomized.LOGGER.info("[DollanCustomized] 已将全屏模式切换为无边框全屏");
    }

}
