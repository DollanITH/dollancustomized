package com.jhxiaochen.dollancustomized.mixin.mod.optifine;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.client.resourcepack.BuiltinResourcePackInstaller;
import net.optifine.util.ResUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 用 @Redirect 替代 OptiFine ResUtils 中 collectFilesZIP 的调用，让 OptiFine 也能从
 * mod jar 内 resourcepacks/&lt;PACK_NAME&gt;/assets/minecraft/ 收集文件。
 *
 * 原 collectFilesZIP 固定剥离 "assets/minecraft/" 前缀后按 prefixes/suffixes 匹配；
 * 内置三包位于 resourcepacks/&lt;name&gt;/assets/minecraft/ 前缀下，原本匹配不到。
 * 本 mixin 对每个 zip 条目同时尝试 "assets/minecraft/"（原逻辑）与
 * "resourcepacks/&lt;name&gt;/assets/minecraft/"（内置包）前缀，剥离后按同一
 * prefixes/suffixes 匹配。返回的相对路径（mcpatcher/cit/xxx.properties 等）后续由
 * OptiFine 经资源管理器读取，命中 ClasspathResourcePack（从 classpath 读取），
 * 使 CIT(RandomItems) / RandomMobs(RandomEntity) / CTM 生效。
 *
 * method 用正则匹配 collectFiles(IResourcePack,String[],String[],String[])，
 * 第一个参数用 L[A-Za-z0-9_/]+; 通配，避免直接引用 OptiFine 混淆接口类（cer），
 * 以规避 SRG 版与混淆版运行时之间的类名映射差异。
 */
@Mixin(ResUtils.class)
public abstract class MixinResUtils {

    private static final String ASSETS = "assets/minecraft/";

    @Redirect(
            remap = false,
            method = "collectFiles(Lnet/minecraft/client/resources/IResourcePack;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)[Ljava/lang/String;",
            at = @At(value = "INVOKE",
                    target = "Lnet/optifine/util/ResUtils;collectFilesZIP(Ljava/io/File;[Ljava/lang/String;[Ljava/lang/String;)[Ljava/lang/String;")
    )
    private static String[] dollan$collectFilesZIP(File zipFile, String[] prefixes, String[] suffixes) {
        List<String> list = new ArrayList<>();
        if (zipFile == null || !zipFile.isFile()) {
            return list.toArray(new String[0]);
        }
        try (ZipFile zip = new ZipFile(zipFile)) {
            for (ZipEntry entry : Collections.list(zip.entries())) {
                String name = entry.getName();
                if (name.startsWith(ASSETS)) {
                    addIfMatch(list, name.substring(ASSETS.length()), prefixes, suffixes);
                    continue;
                }
                for (String packName : BuiltinResourcePackInstaller.PACK_NAMES) {
                    String inner = "resourcepacks/" + packName + "/" + ASSETS;
                    if (name.startsWith(inner)) {
                        addIfMatch(list, name.substring(inner.length()), prefixes, suffixes);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            DollanCustomized.LOGGER.warn("[内置资源包] 枚举 '{}' 的 zip 失败: {}", zipFile.getName(), e.toString());
        }
        return list.toArray(new String[0]);
    }

    private static void addIfMatch(List<String> list, String rel, String[] prefixes, String[] suffixes) {
        if (prefixes != null && prefixes.length > 0) {
            boolean ok = false;
            for (String p : prefixes) {
                if (p != null && rel.startsWith(p)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return;
            }
        }
        if (suffixes != null && suffixes.length > 0) {
            boolean ok = false;
            for (String s : suffixes) {
                if (s != null && rel.endsWith(s)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return;
            }
        }
        if (!rel.toLowerCase().equals(rel)) {
            DollanCustomized.LOGGER.warn("[内置资源包] Skipping non-lowercase path: {}", rel);
            return;
        }
        list.add(rel);
    }
}
