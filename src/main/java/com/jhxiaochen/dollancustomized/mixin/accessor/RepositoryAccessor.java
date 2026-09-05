package com.jhxiaochen.dollancustomized.mixin.accessor;

import net.minecraft.client.resources.ResourcePackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ResourcePackRepository.class)
public interface RepositoryAccessor {
    @Accessor("repositoryEntriesAll")
    List<ResourcePackRepository.Entry> getAllEntries();

    @Accessor("repositoryEntries")
    List<ResourcePackRepository.Entry> getSelectedEntries();
}
