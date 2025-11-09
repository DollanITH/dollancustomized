package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.command.Command114514;
import com.jhxiaochen.dollancustomized.fixes.Remaper;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanItems;
import com.jhxiaochen.dollancustomized.model.Registry;
import com.jhxiaochen.dollancustomized.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

@Mod(modid = Reference.MOD_ID, useMetadata = true, version = Reference.VERSION)
public class DollanCustomized {

    @Mod.Instance(Reference.MOD_ID)
    public static DollanCustomized instance;
    public static Remaper remaper;
    public Registry registry = new Registry();

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


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Pre Init , thank you fucking mother");
        MinecraftForge.EVENT_BUS.register(this);
        Remaper.registerFixable();
        registry.preInit(event);
        MinecraftForge.EVENT_BUS.register(registry);
        registry.getElements().forEach(registry -> registry.preInit(event));
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registry.getElements().forEach(registry -> registry.init(event));
        System.out.println("Init, thank you fucking mother");
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("Post Init, thank you fucking mother");
        proxy.postInit();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new Command114514());
        System.out.println("Hey guys, you're welcome.");
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        DollanBlocks.registerModels();
        DollanItems.registerModels();
        registry.getElements().forEach(modelRegistry -> modelRegistry.registerBlockItemModel(event));
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(registry.getBlocks().stream().map(Supplier::get).toArray(Block[]::new));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(registry.getItems().stream().map(Supplier::get).toArray(Item[]::new));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event){
        for (IBlockState state : DollanBlocks.station_end_door.getBlockState().getValidStates())
        {
            ModelResourceLocation modelResourceLocation = event.getModelManager().getBlockModelShapes().getBlockStateMapper().putAllStateModelLocations().get(state);
            event.getModelRegistry().putObject(modelResourceLocation, event.getModelManager().getModel(modelResourceLocation));
        }
    }
}