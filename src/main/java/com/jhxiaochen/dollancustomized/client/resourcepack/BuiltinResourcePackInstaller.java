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
 * 将 mod 内置的独立资源包（位于 classpath 的 resourcepacks/&lt;PACK_NAME&gt;，即
 * src/main/resources/resourcepacks/dollan_opifine_pack；打包后为 jar 内同前缀）
 * 安装为 Minecraft 磁盘上的标准资源包（resourcepacks/&lt;packName&gt;/），
 * 并注册、默认选中、触发重载。
 *
 * 安装后它就是一个标准 FolderResourcePack：OptiFine 与 Continuity 都能正常扫描
 * assets/minecraft/mcpatcher/ctm/*.properties（CTM）和 mcpatcher/cit/*（刷怪蛋新版材质），
 * pack.png 图标、pack.mcmeta 全部正常。dev 目录与打包 jar 统一走 classpath 复制，无需两套逻辑。
 */
public final class BuiltinResourcePackInstaller {

    /** resourcepacks 目录下安装出的文件夹名（= 资源包 id，写入 gameSettings.resourcePacks）。 */
    private static final String PACK_NAME = "dollan_opifine_pack";

    /** classpath 中的资源根（相对 mod 产物根）：src/main/resources/resourcepacks/<PACK_NAME>，打包后 jar 内同前缀。 */
    private static final String PACK_RESOURCE = "resourcepacks/" + PACK_NAME;

    private BuiltinResourcePackInstaller() {
    }

    /** 客户端初始化完成后调用：安装 + 注册 + 默认选中 + 触发资源重载。 */
    public static void install() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft == null) {
            DollanCustomized.LOGGER.warn("Cannot install built-in resource pack before Minecraft is initialized");
            return;
        }
        ResourcePackRepository repository = minecraft.getResourcePackRepository();
        if (repository == null) {
            DollanCustomized.LOGGER.warn("ResourcePackRepository is null, skip install");
            return;
        }

        if (!copyPack(repository.getDirResourcepacks())) {
            return;
        }
        repository.updateRepositoryEntriesAll();

        List<ResourcePackRepository.Entry> selectedPacks = new ArrayList<>(repository.getRepositoryEntries());
        Set<String> selectedNames = new HashSet<>();
        for (ResourcePackRepository.Entry entry : selectedPacks) {
            selectedNames.add(entry.getResourcePackName());
        }
        for (ResourcePackRepository.Entry entry : repository.getRepositoryEntriesAll()) {
            if (isInstalledPack(entry.getResourcePackName()) && !selectedNames.contains(entry.getResourcePackName())) {
                selectedPacks.add(entry);
            }
        }
        repository.setRepositories(selectedPacks);

        GameSettings settings = minecraft.gameSettings;
        if (settings != null) {
            if (!settings.resourcePacks.contains(PACK_NAME)) {
                settings.resourcePacks.add(PACK_NAME);
            }
            settings.saveOptions();
        }

        minecraft.scheduleResourcesRefresh();
        DollanCustomized.LOGGER.info("[内置资源包] {} 已安装并默认启用", PACK_NAME);
    }

    private static boolean isInstalledPack(String packName) {
        return PACK_NAME.equals(packName);
    }

    /** 把 classpath 中的 packs/minecraft 复制到 resourcepacks/<PACK_NAME>/，返回是否成功。 */
    private static boolean copyPack(File destinationDir) {
        URL url = BuiltinResourcePackInstaller.class.getClassLoader().getResource(PACK_RESOURCE + "/pack.mcmeta");
        if (url == null) {
            DollanCustomized.LOGGER.error("[内置资源包] 找不到内置资源包 '{}'", PACK_RESOURCE);
            return false;
        }
        Path target = new File(destinationDir, PACK_NAME).toPath();
        try {
            if ("file".equals(url.getProtocol())) {
                copyDirectory(Paths.get(url.toURI()).getParent(), target);
            } else if ("jar".equals(url.getProtocol())) {
                copyZip(url, target);
            } else {
                DollanCustomized.LOGGER.error("[内置资源包] 不支持的资源 URL '{}'", url);
                return false;
            }
        } catch (Exception e) {
            DollanCustomized.LOGGER.error("[内置资源包] 安装 '{}' 失败", PACK_NAME, e);
            return false;
        }
        DollanCustomized.LOGGER.info("[内置资源包] 已复制 {} -> {}", PACK_RESOURCE, target);
        return true;
    }

    /** dev 目录模式：递归复制目录（url 指向 pack.mcmeta，其父目录即 packs/minecraft 根）。 */
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

    /** 打包 jar 模式：从 jar 内复制 packs/minecraft/ 前缀下的条目到 target。 */
    private static void copyZip(URL url, Path target) throws Exception {
        String spec = url.toExternalForm();
        int separatorIndex = spec.indexOf("!/");
        if (separatorIndex < 0) {
            throw new IllegalArgumentException("Invalid jar resource URL: " + url);
        }
        URI jarUri = new URI(spec.substring("jar:".length(), separatorIndex));
        String prefix = PACK_RESOURCE + "/";
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
