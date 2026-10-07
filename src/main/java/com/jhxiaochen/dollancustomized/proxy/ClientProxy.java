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
import com.jhxiaochen.dollancustomized.client.resourcepack.BuiltinResourcePackInstaller;
import com.jhxiaochen.dollancustomized.common.tileentity.*;
import com.jhxiaochen.dollancustomized.config.DollanConfig;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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

    @Override
    public AxisAlignedBB getLightSelectionBox(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = minecraft.player;
        if (player != null
                && (player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(DollanBlocks.light_block)
                || player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(DollanBlocks.light_block))) {
            return net.minecraft.block.Block.FULL_BLOCK_AABB;
        }
        return new net.minecraft.util.math.AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle("DollanITH Client V10");
        BuiltinResourcePackInstaller.install();
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
    }

    @Override
    public void contruction(FMLLoadCompleteEvent event) {
        if (DollanConfig.CLIENT_CONFIG.ENABLE_BORDERLESS_WINDOW) {
            ForgeEarlyConfig.WINDOW_BORDERLESS_WINDOWS_COMPATIBILITY = false;
            ForgeEarlyConfig.WINDOW_BORDERLESS_REPLACES_FULLSCREEN = true;
            ConfigManager.sync(ForgeEarlyConfig.class);
        }
        DollanCustomized.LOGGER.info("[DollanCustomized] 已将全屏模式切换为无边框全屏");
    }

}
