package com.jhxiaochen.dollancustomized.client.resourcepack;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 内置资源包安装器（参照 CleanContinuity 的 BuiltinResourcePackInstaller）。
 *
 * 将 mod 内置的多个独立资源包（位于 classpath 的 resourcepacks/&lt;packName&gt;/，即
 * src/main/resources/resourcepacks/&lt;packName&gt;/；打包后为 jar 内同前缀）
 * 安装为 Minecraft 磁盘上的标准资源包（resourcepacks/&lt;packName&gt;/），
 * 并注册、默认选中、触发重载。
 *
 * 安装后每个包都是标准 FolderResourcePack：OptiFine 与 Continuity 都能正常扫描
 * assets/minecraft/mcpatcher/*（CTM / CIT / RandomMobs），pack.png 图标、pack.mcmeta 正常。
 * dev 目录与打包 jar 统一走 classpath 复制，无需两套逻辑。
 */
public final class BuiltinResourcePackInstaller {

    /** 要安装的内置资源包目录名（= resourcepacks 目录下的文件夹名 = 资源包 id，写入 gameSettings.resourcePacks）。 */
    private static final String[] PACK_NAMES = {
            "dollan_pack",
            "programmer_art",
            "programmer_art_rc",
    };

    /** 这些包安装后默认启用（写入已选列表和 resourcePacks 名单）；其余只安装进资源包列表、不默认选中。 */
    private static final String[] DEFAULT_ENABLED = {
            "dollan_pack",
    };

    private BuiltinResourcePackInstaller() {
    }

    /** 客户端初始化完成后调用：安装所有内置资源包 + 注册 + 默认选中 + 触发资源重载。 */
    public static void install() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft == null) {
            DollanCustomized.LOGGER.warn("Cannot install built-in resource packs before Minecraft is initialized");
            return;
        }
        ResourcePackRepository repository = minecraft.getResourcePackRepository();
        if (repository == null) {
            DollanCustomized.LOGGER.warn("ResourcePackRepository is null, skip install");
            return;
        }

        for (String packName : PACK_NAMES) {
            copyPack(packName, repository.getDirResourcepacks());
        }
        repository.updateRepositoryEntriesAll();

        List<ResourcePackRepository.Entry> selectedPacks = new ArrayList<>(repository.getRepositoryEntries());
        Set<String> selectedNames = new HashSet<>();
        for (ResourcePackRepository.Entry entry : selectedPacks) {
            selectedNames.add(entry.getResourcePackName());
        }
        for (ResourcePackRepository.Entry entry : repository.getRepositoryEntriesAll()) {
            if (isInstalledPack(entry.getResourcePackName())
                    && isDefaultEnabled(entry.getResourcePackName())
                    && !selectedNames.contains(entry.getResourcePackName())) {
                selectedPacks.add(entry);
            }
        }
        repository.setRepositories(selectedPacks);

        GameSettings settings = minecraft.gameSettings;
        if (settings != null) {
            for (String packName : PACK_NAMES) {
                if (isDefaultEnabled(packName) && !settings.resourcePacks.contains(packName)) {
                    settings.resourcePacks.add(packName);
                }
            }
            settings.saveOptions();
        }

        minecraft.scheduleResourcesRefresh();
        for (String packName : PACK_NAMES) {
            DollanCustomized.LOGGER.info("[内置资源包] {} 已安装并默认启用", packName);
        }
    }

    private static boolean isInstalledPack(String packName) {
        for (String installedName : PACK_NAMES) {
            if (installedName.equals(packName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDefaultEnabled(String packName) {
        for (String enabledName : DEFAULT_ENABLED) {
            if (enabledName.equals(packName)) {
                return true;
            }
        }
        return false;
    }

    /** 把 classpath 中的 resourcepacks/<packName> 复制到 resourcepacks/<packName>/，返回是否成功。 */
    private static boolean copyPack(String packName, File destinationDir) {
        URL url = BuiltinResourcePackInstaller.class.getClassLoader()
                .getResource("resourcepacks/" + packName + "/pack.mcmeta");
        if (url == null) {
            DollanCustomized.LOGGER.error("[内置资源包] 找不到内置资源包 '{}'", packName);
            return false;
        }
        Path target = new File(destinationDir, packName).toPath();
        try {
            if ("file".equals(url.getProtocol())) {
                copyDirectory(Paths.get(url.toURI()).getParent(), target);
            } else if ("jar".equals(url.getProtocol())) {
                copyZip(url, packName, target);
            } else {
                DollanCustomized.LOGGER.error("[内置资源包] 不支持的资源 URL '{}'", url);
                return false;
            }
        } catch (Exception e) {
            DollanCustomized.LOGGER.error("[内置资源包] 安装 '{}' 失败", packName, e);
            return false;
        }
        DollanCustomized.LOGGER.info("[内置资源包] 已复制 {} -> {}", "resourcepacks/" + packName, target);
        return true;
    }

    /** dev 目录模式：递归复制目录（url 指向 pack.mcmeta，其父目录即包根）。 */
    private static void copyDirectory(Path source, Path target) throws Exception {
        try (Stream<Path> stream = Files.walk(source)) {
            for (Path path : stream.toList()) {
                Path relative = source.relativize(path);
                Path destination = target.resolve(relative.toString());
                if (Files.isDirectory(path)) {
                    Files.createDirectories(destination);
                } else {
                    Files.createDirectories(destination.getParent());
                    Files.copy(path, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /** 打包 jar 模式：从 jar 内复制 resourcepacks/<packName>/ 前缀下的条目到 target。 */
    private static void copyZip(URL url, String packName, Path target) throws Exception {
        String spec = url.toExternalForm();
        int separatorIndex = spec.indexOf("!/");
        if (separatorIndex < 0) {
            throw new IllegalArgumentException("Invalid jar resource URL: " + url);
        }
        URI jarUri = new URI(spec.substring("jar:".length(), separatorIndex));
        String prefix = "resourcepacks/" + packName + "/";
        try (ZipFile zipFile = new ZipFile(new File(jarUri))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().startsWith(prefix)) {
                    continue;
                }
                Path destination = target.resolve(entry.getName().substring(prefix.length()));
                Files.createDirectories(destination.getParent());
                try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
