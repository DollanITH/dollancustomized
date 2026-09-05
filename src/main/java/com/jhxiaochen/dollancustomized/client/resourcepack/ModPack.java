package com.jhxiaochen.dollancustomized.client.resourcepack;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModPack extends AbstractResourcePack implements Closeable {

    private static final String PREFIX = "packs/minecraft/";

    private final File source;
    private final boolean isZip;
    private ZipFile zipFile;

    public ModPack(File file) {
        super(file);
        this.source = file;
        this.isZip = file.isFile(); // jar = true；目录（classes 或 resources）= false
    }

    /**
     * 统一资源读取。查找顺序：
     * 1. classloader（jar / classes / resources 均可命中，最可靠）
     * 2. 目录模式文件系统：PREFIX 路径、无前缀路径
     *    （额外尝试兄弟 resources 目录，应对 source 是 classes 目录的情况）
     */
    @Nullable
    private InputStream openStream(String relativePath) throws IOException {
        if (isZip) {
            // 2a) jar 内
            ZipFile zf = (zipFile != null ? zipFile : new ZipFile(source));
            zipFile = zf;
            ZipEntry e = zf.getEntry(PREFIX + relativePath);
            if (e == null) e = zf.getEntry(relativePath);
            return e != null ? zf.getInputStream(e) : null;
        }

        // 2b) 目录模式文件系统
        for (File root : candidateRoots()) {
            File f = new File(root, PREFIX + relativePath);
            if (!f.isFile()) f = new File(root, relativePath);
            if (f.isFile()) return new BufferedInputStream(new FileInputStream(f));
        }
        return null;
    }

    /** 目录模式的候选根：source 本身，以及 build/resources/main（当 source 是 build/classes/java/main 时） */
    private File[] candidateRoots() {
        File res = new File(source.getParentFile(), "resources/main");
        if (res.isDirectory()) {
            return new File[]{source, res};
        }
        // source 可能是 build/classes/java/main → parent 链上找 build
        File build = source.getParentFile() != null && source.getParentFile().getParentFile() != null
                ? source.getParentFile().getParentFile().getParentFile() : null;
        if (build != null) {
            File res2 = new File(build, "resources/main");
            if (res2.isDirectory()) return new File[]{source, res2};
        }
        return new File[]{source};
    }

    // ==== IResourcePack 接口 ====

    public InputStream getInputStreamByName(String path) throws IOException {
        InputStream in = openStream(path);
        if (in == null) {
            DollanCustomized.LOGGER.warn("ModPack 资源读取失败: {} (source={}, isZip={})",
                    path, source.getAbsolutePath(), isZip);
            throw new FileNotFoundException(path);
        }
        return in;
    }

    public boolean hasResourceName(String path) {
        if (path == null || path.isEmpty()) return false;
        try {
            return openStream(path) != null;
        } catch (IOException e) {
            return false;
        }
    }

    public Set<String> getResourceDomains() {
        Set<String> set = Sets.newHashSet();
        set.add("dollancustomized");
        set.add("minecraft");
        return set;
    }

    public String getPackName() {
        return "Dollan Optifine Pack";
    }

    public File getPackFile() {
        return source;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer serializer, String sectionName) throws IOException {
        DollanCustomized.LOGGER.info("[ModPack] getPackMetadata, section={}", sectionName);
        T result = null;
        try (InputStream in = openStream("pack.mcmeta")) {
            if (in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                result = serializer.parseMetadataSection(sectionName, json);
            }
        } catch (Throwable t) {
            DollanCustomized.LOGGER.error("[ModPack] mcmeta 解析异常", t);
        }

        if (result != null) return result;

        // 只有 pack section 才兜底；其他 section（如 language）返回 null 是合法行为
        if ("pack".equals(sectionName)) {
            DollanCustomized.LOGGER.warn("[ModPack] pack section 解析失败，使用内置元数据");
            return (T) new PackMetadataSection(
                    new TextComponentString("Dollan Optifine Pack"), 3);
        }
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        InputStream in = openStream("pack.png");
        if (in == null) {
            DollanCustomized.LOGGER.warn("pack.png 未找到，使用占位图标 (source={})",
                    source.getAbsolutePath());
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        }
        try {
            return TextureUtil.readBufferedImage(in);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Override
    public void close() throws IOException {
        if (zipFile != null) {
            zipFile.close();
            zipFile = null;
        }
    }
}
