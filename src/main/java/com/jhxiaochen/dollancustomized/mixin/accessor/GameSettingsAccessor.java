package com.jhxiaochen.dollancustomized.mixin.accessor;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GameSettings.class)
public interface GameSettingsAccessor {
    @Accessor("resourcePacks")   // SRG 名若不同用 @Accessor("field_183018_l")，unimined 下直接字段名即可
    List<String> getResourcePacks();

    @Accessor("resourcePacks")
    void setResourcePacks(List<String> list);
}
