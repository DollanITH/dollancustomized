package com.jhxiaochen.dollancustomized.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.config.category.ClientConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
public class DollanConfig {

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class ConfigEvent {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

    @Config.Name("Config (Client Only)")
    @Config.Comment("客户端设置,服务端设置没有效果!")
    public static final ClientConfig CLIENT_CONFIG = new ClientConfig();

    @Config.Name("Formwork Safe Range")
    @Config.Comment("模板世界玩家被自杀的设置.若超出此坐标数,玩家将会被自杀")
    @Config.RangeInt(min = 0, max = 30000)
    public static int SAFE_RANGE = 4000;

    @Config.Name("Formwork Dead Setting")
    @Config.Comment("模板世界玩家被自杀的设置.若开启此选项，超出世界高度的玩家将会被自杀")
    public static boolean OUTSIDE_BUILDING_HEIGHT_RANGE = true;

    @Config.Name("Remapper Boolean")
    @Config.Comment("开启方块重映射、物品重映射(用于当方块或物品有变更时,或有缺失时)")
    public static boolean REMAPPER = true;

    static {
        ConfigAnytime.register(DollanConfig.class);
    }
}
