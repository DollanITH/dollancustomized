package com.jhxiaochen.dollancustomized.config.category;

import net.minecraftforge.common.config.Config;

public class ClientConfig {
    @Config.Name("Enable Jacaranda Leaves' Particles")
    @Config.Comment("蓝花楹树树叶粒子效果开关")
    public final boolean ENABLE_JACARANDA_LEAVES = true;
    @Config.Name("Enable Enhanced F3+N")
    @Config.Comment("强化F3+N游戏模式切换开关")
    public final boolean ENABLE_ENHANCED_F3N = true;
}
