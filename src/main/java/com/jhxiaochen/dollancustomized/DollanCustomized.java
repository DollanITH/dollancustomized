package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "dollancustomized", useMetadata = true, updateJSON = "https://wiki.dollancraft.top", version = "3.1")
public class DollanCustomized {

    @Instance(Reference.MOD_ID)
    public static com.jhxiaochen.dollancustomized.DollanCustomized instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static final String MODID = "dollancustomized";
    public static final String VERSION = "3.1";
    public static final String Accept_Minecraft_version = "1.12.2";
    public static com.jhxiaochen.dollancustomized.DollanCustomized getInstance() {
        return instance;
    }

    public static final CreativeTabs LIGHT_TAB = new TabLight();
    public static final CreativeTabs DOLLAN_TRAN_TAB = new TabdollanTran();
    public static final CreativeTabs DOLLAN_METRO_TAB = new TabdollanMetro();
    public static final CreativeTabs MS_METRO_TAB = new TabMSMetro();
    public static final CreativeTabs ROAD_TAB = new TabRoad();
    public static final CreativeTabs ROAD_Signs = new TabRoadSigns();


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Pre Init");
        MinecraftForge.EVENT_BUS.register(proxy);
        proxy.preInit();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        System.out.println("Init");
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("Post Init");
        proxy.postInit();
    }
}