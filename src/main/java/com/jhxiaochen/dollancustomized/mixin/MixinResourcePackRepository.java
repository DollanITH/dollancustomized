package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.mixin.accessor.EntryAccessor;
import com.jhxiaochen.dollancustomized.mixin.accessor.GameSettingsAccessor;
import com.jhxiaochen.dollancustomized.mixin.accessor.RepositoryAccessor;
import com.jhxiaochen.dollancustomized.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mixin(ResourcePackRepository.class)
public class MixinResourcePackRepository {

    /** 一次性标志：首次启动写名单，之后玩家的 GUI 选择优先，不再回写 */
    @Unique
    private static boolean dollan$defaultWritten = false;

    @Inject(method = "updateRepositoryEntriesAll", at = @At("TAIL"))
    private void dollan$reinject(CallbackInfo ci) {
        if (ClientProxy.modPack == null) return;

        ResourcePackRepository repo = Minecraft.getMinecraft().getResourcePackRepository();
        if (repo == null) return;

        // 判重：已存在同名的 Entry 就不重复建立
        boolean exists = false;
        for (ResourcePackRepository.Entry e : ((RepositoryAccessor) repo).getAllEntries()) {
            if (e.getResourcePackName().equals(ClientProxy.modPack.getPackName())) {
                exists = true;
                break;
            }
        }
        if (exists) {
            // 已有 entry，只需确保名单写入
            this.dollan$ensureDefaultSelected(ClientProxy.modPack.getPackName());
            return;
        }

        // 直接用 ModPack 构造 Entry：Entry 从出生就持有 ModPack
        ResourcePackRepository.Entry entry = EntryAccessor.create(repo, ClientProxy.modPack);

        // 主动读取元数据，填入 Entry 的 rePackMetadataSection（消除 GUI 红字）
        try {
            ((EntryAccessor) entry).dollan$updateResourcePack();
            DollanCustomized.LOGGER.info("[注入] entry 建立: {}, packFormat={}",
                    entry.getResourcePackName(), entry.getPackFormat());
        } catch (IOException e) {
            DollanCustomized.LOGGER.error("[注入] 元数据读取失败", e);
        }

        ((RepositoryAccessor) repo).getAllEntries().add(entry);
        DollanCustomized.LOGGER.info("entry pack class: {}", entry.getResourcePack().getClass());
        this.dollan$ensureSelected(repo, entry, entry.getResourcePackName());   // ← 新增

        this.dollan$ensureDefaultSelected(entry.getResourcePackName());
    }

    @Unique
    private void dollan$ensureDefaultSelected(String packName) {
        if (packName == null) return;
        if (dollan$defaultWritten) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings == null) return;

        GameSettingsAccessor gs = (GameSettingsAccessor) mc.gameSettings;
        List<String> names = new ArrayList<>(gs.getResourcePacks());
        if (!names.contains(packName)) {
            names.add(packName);
            gs.setResourcePacks(names);
            mc.gameSettings.saveOptions();
            DollanCustomized.LOGGER.info("[默认加载] 已将 {} 写入默认资源包名单", packName);
        }
        dollan$defaultWritten = true;
    }

    @Unique
    private void dollan$ensureSelected(ResourcePackRepository repo,
                                       ResourcePackRepository.Entry entry, String packName) {
        RepositoryAccessor racc = (RepositoryAccessor) repo;

        // 已选列表判重
        for (ResourcePackRepository.Entry e : racc.getSelectedEntries()) {
            if (e.getResourcePackName().equals(packName)) {
                this.dollan$ensureDefaultSelected(packName);
                return;
            }
        }

        // 只在 settings 名单里确实有这个名字时，才把它放进已选列表
        // （保证“玩家在 GUI 移除后不再强制启用”的逻辑不被破坏）
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings == null) return;
        GameSettingsAccessor gs = (GameSettingsAccessor) mc.gameSettings;
        if (gs.getResourcePacks().contains(packName)) {
            racc.getSelectedEntries().add(entry);
            DollanCustomized.LOGGER.info("[默认加载] {} 已加入已选列表", packName);
        }

        this.dollan$ensureDefaultSelected(packName);
    }

}
