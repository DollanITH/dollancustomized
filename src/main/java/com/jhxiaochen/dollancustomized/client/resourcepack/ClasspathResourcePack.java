package com.jhxiaochen.dollancustomized.client.resourcepack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 继承 {@link AbstractResourcePack} 的资源包，资源仍从 classpath（mod jar 内
 * resourcepacks/&lt;packName&gt;/）读取，零落盘。
 *
 * 继承 AbstractResourcePack 是为了让 OptiFine 识别为已知资源包类型（不再是
 * "Unknown resource pack type"，这是纯内存 IResourcePack 报的警告）。
 * {@link #getResourcePackFile()} 返回 mod jar 作为占位真实文件以满足父类构造。
 *
 * 注意：OptiFine 的 CIT / RandomMobs / CTM 最终通过 getResourcePackFile() 扫描
 * 磁盘真实文件位置来生效；若资源仍位于 jar 内 resourcepacks/&lt;name&gt;/ 的非标准位置，
 * 这些逐文件/目录扫描可能仍扫不到（由继承是否能被 OptiFine 采纳而定）。
 */
public final class ClasspathResourcePack extends AbstractResourcePack {

    private final String packName;
    private final String prefix;

    public ClasspathResourcePack(String packName) {
        super(locateFile(packName));
        this.packName = packName;
        this.prefix = "resourcepacks/" + packName + "/";
    }

    /** 定位占位真实文件：jar 模式返回 mod jar，dev 模式返回占位路径。 */
    private static File locateFile(String packName) {
        try {
            URL url = ClasspathResourcePack.class.getClassLoader()
                    .getResource("resourcepacks/" + packName + "/pack.mcmeta");
            if (url != null && "jar".equals(url.getProtocol())) {
                String spec = url.toExternalForm();
                int separatorIndex = spec.indexOf("!/");
                if (separatorIndex >= 0) {
                    return new File(new URI(spec.substring("jar:".length(), separatorIndex)));
                }
            }
        } catch (Exception ignored) {
        }
        return new File(packName);
    }

    @Override
    protected InputStream getInputStreamByName(String name) throws IOException {
        InputStream in = ClasspathResourcePack.class.getClassLoader().getResourceAsStream(prefix + name);
        if (in == null) {
            throw new FileNotFoundException(name);
        }
        return in;
    }

    @Override
    protected boolean hasResourceName(String name) {
        return ClasspathResourcePack.class.getClassLoader().getResource(prefix + name) != null;
    }

    @Override
    public Set<String> getResourceDomains() {
        Set<String> domains = new HashSet<>();
        URL assetsUrl = ClasspathResourcePack.class.getClassLoader().getResource(prefix + "assets/");
        if (assetsUrl == null) {
            domains.add("minecraft");
            return domains;
        }
        try {
            if ("file".equals(assetsUrl.getProtocol())) {
                try (Stream<Path> stream = Files.list(Paths.get(assetsUrl.toURI()))) {
                    stream.filter(Files::isDirectory)
                            .forEach(p -> domains.add(p.getFileName().toString()));
                }
            } else if ("jar".equals(assetsUrl.getProtocol())) {
                String spec = assetsUrl.toExternalForm();
                int separatorIndex = spec.indexOf("!/");
                if (separatorIndex >= 0) {
                    URI jarUri = new URI(spec.substring("jar:".length(), separatorIndex));
                    try (ZipFile zipFile = new ZipFile(new File(jarUri))) {
                        String assetPrefix = prefix + "assets/";
                        Enumeration<? extends ZipEntry> entries = zipFile.entries();
                        while (entries.hasMoreElements()) {
                            String name = entries.nextElement().getName();
                            if (name.startsWith(assetPrefix) && name.length() > assetPrefix.length()) {
                                String rest = name.substring(assetPrefix.length());
                                int slash = rest.indexOf('/');
                                if (slash > 0) {
                                    domains.add(rest.substring(0, slash));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            DollanCustomized.LOGGER.warn("[内置资源包] 枚举 '{}' 的命名空间失败: {}", packName, e.toString());
        }
        if (domains.isEmpty()) {
            domains.add("minecraft");
        }
        return domains;
    }

    @Override
    public <T extends IMetadataSection> T getPackMetadata(
            MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        InputStream in = ClasspathResourcePack.class.getClassLoader().getResourceAsStream(prefix + "pack.mcmeta");
        if (in == null) {
            return null;
        }
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            JsonObject root = (JsonObject) new JsonParser().parse(reader);
            if (!root.has(metadataSectionName)) {
                return null;
            }
            return metadataSerializer.parseMetadataSection(metadataSectionName, root);
        } catch (RuntimeException e) {
            DollanCustomized.LOGGER.warn("[内置资源包] 解析 '{}' 的 pack.mcmeta 失败: {}", packName, e.toString());
            return null;
        }
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        InputStream in = ClasspathResourcePack.class.getClassLoader().getResourceAsStream(prefix + "pack.png");
        if (in == null) {
            return null;
        }
        try {
            return ImageIO.read(in);
        } finally {
            in.close();
        }
    }

    @Override
    public String getPackName() {
        return packName;
    }
}
