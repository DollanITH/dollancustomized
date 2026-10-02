package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.client.resourcepack.BuiltinResourcePackInstaller;
import net.minecraft.client.resources.ResourcePackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 1. 把三个内置内存资源包注入资源包仓库的"可用列表"，使它们出现在游戏内
 *    "资源包"界面的可用列表里，可像普通资源包一样勾选/取消。
 * 2. dollan_pack 与 programmer_art 互斥：启用其中一个时自动禁用另一个
 *    （保留"后启用"的那个），在 setRepositories 选择列表变更时强制互斥。
 */
@Mixin(ResourcePackRepository.class)
public class MixinResourcePackRepository {

    @Unique private static final String PACK_A = "dollan_pack";
    @Unique private static final String PACK_B = "programmer_art";

    /** 可用列表（GUI 显示未勾选的候选包）。 */
    @Shadow private List<ResourcePackRepository.Entry> repositoryEntriesAll;

    /** 在刷新可用列表时注入三个内置资源包（AbstractResourcePack，绑定隔离目录），使其在资源包界面可选。 */
    @Inject(method = "updateRepositoryEntriesAll", at = @At("TAIL"))
    private void dollan$injectAvailablePacks(CallbackInfo ci) {
        if (this.repositoryEntriesAll == null) {
            return;
        }
        ResourcePackRepository self = (ResourcePackRepository) (Object) this;
        for (String packName : BuiltinResourcePackInstaller.PACK_NAMES) {
            if (dollan$contains(this.repositoryEntriesAll, packName)) {
                continue;
            }
            ResourcePackRepository.Entry entry = BuiltinResourcePackInstaller.createEntry(
                    self, BuiltinResourcePackInstaller.buildPack(packName));
            if (entry != null) {
                this.repositoryEntriesAll.add(entry);
                DollanCustomized.LOGGER.debug("[内置资源包] '{}' 已加入可用列表", packName);
            }
        }
    }

    @Unique private static boolean dollan$contains(List<ResourcePackRepository.Entry> entries, String packName) {
        for (ResourcePackRepository.Entry e : entries) {
            if (packName.equals(e.getResourcePackName())) {
                return true;
            }
        }
        return false;
    }

    /** 上一次生效的已选资源包名（用于判断"这次新增了哪个"= 刚启用的那个）。 */
    @Unique private static Set<String> dollan$lastSelected = new HashSet<>();

    @Inject(method = "setRepositories", at = @At("HEAD"))
    private void dollan$enforceMutex(List<ResourcePackRepository.Entry> repositories, CallbackInfo ci) {
        if (repositories == null) {
            return;
        }

        List<String> names = new ArrayList<>();
        for (ResourcePackRepository.Entry e : repositories) {
            names.add(e.getResourcePackName());
        }

        boolean hasA = names.contains(PACK_A);
        boolean hasB = names.contains(PACK_B);
        if (hasA && hasB) {
            boolean aWas = dollan$lastSelected.contains(PACK_A);
            boolean bWas = dollan$lastSelected.contains(PACK_B);

            String keep;
            if (aWas && !bWas) {
                keep = PACK_B;  // 新增 B → 保留 B，移除 A
            } else if (bWas && !aWas) {
                keep = PACK_A;  // 新增 A → 保留 A，移除 B
            } else if (aWas) {
                keep = PACK_A;  // 两者此前都在，保守保留 A
            } else {
                keep = PACK_B;  // 两者都新增，保留 B
            }
            String drop = keep.equals(PACK_A) ? PACK_B : PACK_A;
            repositories.removeIf(e -> drop.equals(e.getResourcePackName()));
            DollanCustomized.LOGGER.info("[互斥] {} 与 {} 冲突，保留 {}，移除 {}",
                    PACK_A, PACK_B, keep, drop);
        }

        // 记录本次生效的已选集合，供下次判断"新增者"
        dollan$lastSelected = new HashSet<>();
        for (ResourcePackRepository.Entry e : repositories) {
            dollan$lastSelected.add(e.getResourcePackName());
        }
    }
}
