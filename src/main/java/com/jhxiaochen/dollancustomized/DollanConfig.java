package com.jhxiaochen.dollancustomized;

import com.cleanroommc.configanytime.ConfigAnytime;
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

    @Config.Name("Formwork Safe Range")
    @Config.Comment("模板世界玩家被自杀的设置.若超出此坐标数,玩家将会被自杀")
    @Config.RangeInt(min = 0, max = 10000)
    public static int SAFE_RANGE = 3000;

    @Config.Name("Enable Jacaranda Leaves' Particles")
    @Config.Comment("蓝花楹树树叶粒子效果开关")
    public static boolean ENABLE_JACARANDA_LEAVES = true;

    static {
        ConfigAnytime.register(DollanConfig.class);
    }
}
