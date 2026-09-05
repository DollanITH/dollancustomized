package com.jhxiaochen.dollancustomized.mixin.accessor;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.IOException;

@Mixin(ResourcePackRepository.Entry.class)
public interface EntryAccessor {

    // 内部类构造器 Invoker：第一个参数是外部类实例
    @Invoker("<init>")
    static ResourcePackRepository.Entry create(
            ResourcePackRepository outer, IResourcePack pack) {
        throw new AssertionError();
    }

    @Invoker("updateResourcePack")
    void dollan$updateResourcePack() throws IOException;
}

