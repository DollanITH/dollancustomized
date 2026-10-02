package com.jhxiaochen.dollancustomized.client.resourcepack;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 内置资源包注册器（AbstractResourcePack + 资源包列表可选 + 互斥，零落盘）。
 *
 * 三个内置资源包以 {@link ClasspathResourcePack}（继承 AbstractResourcePack）从 classpath
 * resourcepacks/&lt;name&gt;/ 读取，不复制、不解压到磁盘。继承 AbstractResourcePack 是为了让
 * OptiFine 识别资源包类型。
 *
 * 可选项列表 + 互斥由 {@code MixinResourcePackRepository} 完成；勾选状态持久化走
 * gameSettings.resourcePacks。
 */
public final class BuiltinResourcePackInstaller {

    /** 内置包清单（classpath 前缀 resourcepacks/&lt;name&gt;/，与 mixin 共享）。 */
    public static final String[] PACK_NAMES = {"dollan_pack", "programmer_art", "programmer_art_rc"};
    /** 默认启用（首次启动无任何内置包勾选时）。 */
    public static final String DEFAULT_PACK = "dollan_pack";

    private BuiltinResourcePackInstaller() {
    }

    /** 客户端初始化完成后调用：解压三包到隔离目录、按 gameSettings 恢复已选并触发资源重载。 */
    public static void install() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft == null || minecraft.getResourcePackRepository() == null) {
            DollanCustomized.LOGGER.warn("Cannot install built-in resource packs before Minecraft is initialized");
            return;
        }
        ResourcePackRepository repository = minecraft.getResourcePackRepository();
        List<String> saved = new ArrayList<>(minecraft.gameSettings.resourcePacks);
        boolean anyBuiltinSaved = saved.stream().anyMatch(BuiltinResourcePackInstaller::isBuiltinPack);

        List<ResourcePackRepository.Entry> selected = new ArrayList<>(repository.getRepositoryEntries());
        for (String packName : PACK_NAMES) {
            boolean enable;
            if (saved.contains(packName)) {
                enable = true;
            } else if (!anyBuiltinSaved) {
                enable = packName.equals(DEFAULT_PACK);
            } else {
                enable = false;
            }
            if (!enable) {
                continue;
            }
            if (containsPack(selected, packName)) {
                continue;
            }
            ResourcePackRepository.Entry entry = createEntry(repository, buildPack(packName));
            if (entry != null) {
                selected.add(entry);
            }
        }

        enforceMutex(selected);
        repository.setRepositories(selected);
        minecraft.scheduleResourcesRefresh();
        DollanCustomized.LOGGER.info("[内置资源包] 已恢复内置资源包勾选状态: {}",
                String.join(", ", selected.stream().map(e -> e.getResourcePackName()).collect(java.util.stream.Collectors.toList())));
    }

    /** 构建一个内置资源包（AbstractResourcePack 子类，从 classpath 读取，零落盘）。 */
    public static ClasspathResourcePack buildPack(String packName) {
        return new ClasspathResourcePack(packName);
    }

    public static boolean isBuiltinPack(String name) {
        for (String p : PACK_NAMES) {
            if (p.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsPack(List<ResourcePackRepository.Entry> entries, String packName) {
        for (ResourcePackRepository.Entry entry : entries) {
            if (packName.equals(entry.getResourcePackName())) {
                return true;
            }
        }
        return false;
    }

    /** dollan_pack 与 programmer_art 互斥：保留后出现者。 */
    public static void enforceMutex(List<ResourcePackRepository.Entry> selected) {
        int dollan = indexOf(selected, "dollan_pack");
        int programmer = indexOf(selected, "programmer_art");
        if (dollan >= 0 && programmer >= 0) {
            if (dollan > programmer) {
                removePack(selected, "programmer_art");
            } else {
                removePack(selected, "dollan_pack");
            }
        }
    }

    private static int indexOf(List<ResourcePackRepository.Entry> entries, String packName) {
        for (int i = 0; i < entries.size(); i++) {
            if (packName.equals(entries.get(i).getResourcePackName())) {
                return i;
            }
        }
        return -1;
    }

    private static void removePack(List<ResourcePackRepository.Entry> entries, String packName) {
        entries.removeIf(e -> packName.equals(e.getResourcePackName()));
    }

    /** 反射创建 ResourcePackRepository.Entry（其构造为 private），供 install 与 mixin 共用。
     *  Entry(repository, IResourcePack) 构造不读取 pack.mcmeta，需额外调用 updateResourcePack()
     *  设置 rePackMetadataSection，否则资源包界面会显示 "Invalid pack.mcmeta"。 */
    public static ResourcePackRepository.Entry createEntry(ResourcePackRepository repository, IResourcePack pack) {
        try {
            Constructor<ResourcePackRepository.Entry> constructor =
                    ResourcePackRepository.Entry.class.getDeclaredConstructor(ResourcePackRepository.class, IResourcePack.class);
            constructor.setAccessible(true);
            ResourcePackRepository.Entry entry = constructor.newInstance(repository, pack);
            try {
                entry.updateResourcePack();
            } catch (Exception e) {
                DollanCustomized.LOGGER.warn("[内置资源包] 读取 '{}' 的 pack 元数据失败: {}", pack.getPackName(), e.toString());
            }
            return entry;
        } catch (Exception e) {
            DollanCustomized.LOGGER.error("[内置资源包] 创建资源包 Entry 失败: {}", pack.getPackName(), e);
            return null;
        }
    }
}
