package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.client.font.SignFontLoader;
import com.jhxiaochen.dollancustomized.client.gui.GuiHandler;
import com.jhxiaochen.dollancustomized.client.particle.ParticleTextureRegister;
import com.jhxiaochen.dollancustomized.command.*;
import com.jhxiaochen.dollancustomized.common.tileentity.*;
import com.jhxiaochen.dollancustomized.fixes.Remaper;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.proxy.CommonProxy;
import com.jhxiaochen.dollancustomized.world.level.WorldProviderFlatDim;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = "after:configanytime")
public class DollanCustomized {

    @Mod.Instance(Reference.MOD_ID)
    public static DollanCustomized     instance;
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
                TileEntityDollanSign.class,
                new ResourceLocation(Reference.MOD_ID, "jacaranda_sign")
        );

        GameRegistry.registerTileEntity(
                TileEntityDollanHangingSign.class,
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
        DimensionManager.registerDimension(3, FLAT_DIM);

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

    public static final DimensionType FLAT_DIM = DimensionType.register(
            "formwork", "_formwork", 3, WorldProviderFlatDim.class, false);

    @SubscribeEvent
    void onFormwork(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        int border;
        EntityPlayer player = event.player;
        if (DollanConfig.SAFE_RANGE > 0 && DollanConfig.SAFE_RANGE < 10000) {
            border = DollanConfig.SAFE_RANGE;
        } else {
            border = 3000;
        }
        if (player.world.provider.getDimensionType() != FLAT_DIM) {
            return;
        }

        if (player.posY < 0) {
            player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }

        if (player.posY > player.world.getHeight()) {
            player.changeDimension(0);
        }

        double distSq = player.posX * player.posX + player.posZ * player.posZ;
        if (distSq < (double) border * border) {
            return;
        }

        if (player.posX > border || player.posZ > border) {
            player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }

        if (player.posX < -border || player.posZ < -border) {
            player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }

    }

}