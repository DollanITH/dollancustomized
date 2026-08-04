package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.client.font.SignFontLoader;
import com.jhxiaochen.dollancustomized.client.gui.GuiHandler;
import com.jhxiaochen.dollancustomized.command.*;
import com.jhxiaochen.dollancustomized.fixes.Remaper;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanItems;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.particle.ParticleTextureRegister;
import com.jhxiaochen.dollancustomized.proxy.CommonProxy;
import com.jhxiaochen.dollancustomized.tileentity.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = "after:sakura")
public class DollanCustomized {

    @Mod.Instance(Reference.MOD_ID)
    public static DollanCustomized     instance;
    public static Remaper        remaper;
    public static SignFontLoader yourFontLoader;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs LIGHT_TAB = new TabLight();
    public static final CreativeTabs DOLLAN_TRAN_TAB = new TabdollanTran();
    public static final CreativeTabs DOLLAN_METRO_TAB = new TabdollanMetro();
    public static final CreativeTabs METRO_TAB = new TabMetroBlocks();
    public static final CreativeTabs MS_METRO_TAB = new TabMSMetro();
    public static final CreativeTabs ROAD_TAB = new TabRoad();
    public static final CreativeTabs ROAD_Signs = new TabRoadSigns();
    public static final CreativeTabs Blocks = new TabBlocks();
    public static final CreativeTabs WOOD_TAB = new TabWood();


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Pre Init");
        // 初始化网络系统
        NetworkManager.init();

        GameRegistry.registerTileEntity(
                TileEntityRoadText.class,
                new ResourceLocation(Reference.MOD_ID, "road_text")
        );
        GameRegistry.registerTileEntity(
                TileEntityHeightLimitSignCustom.class,
                new ResourceLocation(Reference.MOD_ID, "height_limit_sign_custom")
        );
        GameRegistry.registerTileEntity(
                TileEntityJacarandaSign.class,
                new ResourceLocation(Reference.MOD_ID, "jacaranda_sign")
        );

        GameRegistry.registerTileEntity(
                TileEntityJacarandaHangingSign.class,
                new ResourceLocation(Reference.MOD_ID, "jacaranda_hanging_sign")
        );
        GameRegistry.registerTileEntity(
                TileEntityStationNameBoard.class,
                new ResourceLocation(Reference.MOD_ID, "station_name_board")
        );

        MinecraftForge.EVENT_BUS.register(this);
        Remaper.registerFixable();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Init");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        // Register particle rendering
        registerParticles();

        proxy.init();
    }

    @SideOnly(Side.CLIENT)
    private void registerParticles() {
        // Jacaranda particles are spawned through ParticleManager
        // No special registration needed as they use the standard particle system
        // Preload particle textures
        ParticleTextureRegister.preloadTextures();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("Post Init");
        proxy.postInit();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new Command114514());
        event.registerServerCommand(new CommandExtendedFill());
        event.registerServerCommand(new CommandTreeKiller());
        event.registerServerCommand(new CommandTreeUndo());
        event.registerServerCommand(new CommandTreeUndoDebug());
                System.out.println("Commands Registered successfully.");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        DollanBlocks.registerModels();
        DollanItems.registerModels();
    }

}