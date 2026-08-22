package com.jhxiaochen.dollancustomized.mixin;

import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.MixinLoader;

@MixinLoader
public class MixinInit {
    public MixinInit()
    {
        Mixins.addConfiguration("mixins.dollancustomized.json");
    }
}



