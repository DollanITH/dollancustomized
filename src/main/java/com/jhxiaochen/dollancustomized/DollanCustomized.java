package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.client.font.SignFontLoader;
import com.jhxiaochen.dollancustomized.client.gui.GuiHandler;
import com.jhxiaochen.dollancustomized.command.*;
import com.jhxiaochen.dollancustomized.common.tileentity.*;
import com.jhxiaochen.dollancustomized.config.DollanConfig;
import com.jhxiaochen.dollancustomized.fixes.Remaper;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.proxy.IProxy;
import com.jhxiaochen.dollancustomized.world.level.WorldProviderFlatDim;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class DollanCustomized {

    @Mod.Instance(Reference.MOD_ID)
    public static DollanCustomized instance;

    public static SignFontLoader yourFontLoader;
    public static ModContainer container;
    public static File modSourceFile;

    @SidedProxy(
            modId = Reference.MOD_ID,
            clientSide = "com.jhxiaochen.dollancustomized.proxy.ClientProxy",
            serverSide = "com.jhxiaochen.dollancustomized.proxy.CommonProxy"
    )
    public static IProxy proxy;

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    // ============================== 创造模式物品栏 ==============================

    public static final CreativeTabs LIGHT_TAB = new TabLight();
    public static final CreativeTabs DOLLAN_TRAN_TAB = new TabdollanTran();
    public static final CreativeTabs DOLLAN_METRO_TAB = new TabdollanMetro();
    public static final CreativeTabs METRO_TAB = new TabMetroBlocks();
    public static final CreativeTabs MS_METRO_TAB = new TabMSMetro();
    public static final CreativeTabs ROAD_TAB = new TabRoad();
    public static final CreativeTabs ROAD_Signs = new TabRoadSigns();
    public static final CreativeTabs Blocks = new TabBlocks();
    public static final CreativeTabs WOOD_TAB = new TabWood();

    // ============================== 维度 ==============================

    public static final DimensionType FLAT_DIM = DimensionType.register(
            "formwork", "_formwork", 3, WorldProviderFlatDim.class, false);

    // ============================== 生命周期 ==============================

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Pre Init");
        proxy.preInit(event);
        container = Loader.instance().getIndexedModList().get(Reference.MOD_ID);
        modSourceFile = event.getSourceFile(); // 可能为 null，ResourcePackManager 有兜底

        NetworkManager.init();

        GameRegistry.registerTileEntity(TileEntityRoadText.class,
                new ResourceLocation(Reference.MOD_ID, "road_text"));
        GameRegistry.registerTileEntity(TileEntityHeightLimitSignCustom.class,
                new ResourceLocation(Reference.MOD_ID, "height_limit_sign_custom"));
        GameRegistry.registerTileEntity(TileEntityDollanSign.class,
                new ResourceLocation(Reference.MOD_ID, "sign"));
        GameRegistry.registerTileEntity(TileEntityDollanHangingSign.class,
                new ResourceLocation(Reference.MOD_ID, "hanging_sign"));
        GameRegistry.registerTileEntity(TileEntityStationNameBoard.class,
                new ResourceLocation(Reference.MOD_ID, "station_name_board"));

        MinecraftForge.EVENT_BUS.register(this);
        if (!DollanConfig.REMAPPER) {
        Remaper.registerFixable();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Init");
        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        DimensionManager.registerDimension(3, FLAT_DIM);

        addEgg("villager_golem", 0xDBCDC2, 0x74A332);
        addEgg("snowman", 0xD9F2F2, 0x81A4A4);

    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("Post Init");
        proxy.postInit(event);
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

    @Mod.EventHandler
    public void contruction(FMLLoadCompleteEvent event) {
        proxy.contruction(event);
    }

    // ============================== 模板世界边界 ==============================

    @SubscribeEvent
    public void onFormwork(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        int border;
        EntityPlayer player = event.player;
        if (DollanConfig.SAFE_RANGE > 0 && DollanConfig.SAFE_RANGE < 10000) {
            border = DollanConfig.SAFE_RANGE;
        } else {
            border = 0;
        }

        if (player.world.provider.getDimensionType() != FLAT_DIM) return;
        if (DollanConfig.OUTSIDE_BUILDING_HEIGHT_RANGE) {
            if (player.posY < 0 || player.posY > player.world.getHeight()) {
                player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
            }
        }

        double distSq = player.posX * player.posX + player.posZ * player.posZ;
        if (distSq < (double) border * border) return;

        if (player.posX > border || player.posZ > border) {
            player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }
        if (player.posX < -border || player.posZ < -border) {
            player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }
    }

    // ============================== 刷怪蛋 ==============================

    public static ItemStack createEgg(String entityId) {
        ItemStack egg = new ItemStack(Items.SPAWN_EGG);
        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entityId);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("EntityTag", entityTag);
        egg.setTagCompound(tag);
        return egg;
    }

    public static void addEgg(String name, int primary, int secondary) {
        ResourceLocation rl = new ResourceLocation(name);
        EntityList.EntityEggInfo egg = new EntityList.EntityEggInfo(rl, primary, secondary);
        net.minecraftforge.fml.common.registry.EntityEntry entry =
                net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES.getValue(rl);
        if (entry != null) entry.setEgg(egg);
        EntityList.ENTITY_EGGS.put(rl, egg);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    @SideOnly(Side.CLIENT)
    public static void clampUnderwaterFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (!Loader.isModLoaded("nothirium") && detectAquaacrobatics()) return;
        if (event.getState() == null || event.getState().getMaterial() != Material.WATER) return;

        if (event.getDensity() < 0.1F) {
            event.setDensity(0.1F);
        }
    }

    private static boolean detectAquaacrobatics() {
        try {
            Class.forName("com.fuzs.aquaacrobatics.client.handler.FogHandler");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
