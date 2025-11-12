package com.jhxiaochen.dollancustomized.client;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DollanResourcePack extends FMLFolderResourcePack{

    public DollanResourcePack() {
        super(FMLCommonHandler.instance().findContainerFor(Reference.MOD_ID));
    }

    public String getPath() {
        return "/packs/dollan/";
    }

    private String getAssetsPath() {
        return "/packs/dollan/assets/";
    }

    @Nullable
    private InputStream get(String name) {
        return DollanCustomized.class.getResourceAsStream(getPath() + name);
    }

    @Nullable
    private InputStream getAsset(String name) {
        return DollanCustomized.class.getResourceAsStream(getAssetsPath() + name);
    }

    @Override
    public InputStream getInputStream(ResourceLocation location) throws IOException {
        InputStream resourceAsStream = getAsset(location.getResourcePath());
        if (resourceAsStream == null) {
            throw new FileNotFoundException("Resource " + location + " does not exist");
        }
        return resourceAsStream;
    }

    @Override
    public boolean resourceExists(ResourceLocation location) {
        if (!location.getResourceDomain().equals(Reference.MOD_ID) || !location.getResourceDomain().equals("Minecraft")) {
            return false;
        }
        try {
            return getAsset(location.getResourcePath()) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Set<String> getResourceDomains() {
        return ImmutableSet.of(Reference.MOD_ID, "Minecraft");
    }

    private static <T extends IMetadataSection> T readMetadata(MetadataSerializer metadataSerializer, InputStream inputStream, String sectionName) {
        JsonObject jsonobject;
        BufferedReader bufferedreader = null;
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
        } catch (RuntimeException runtimeexception) {
            throw new JsonParseException(runtimeexception);
        } finally {
            IOUtils.closeQuietly(bufferedreader);
        }
        return metadataSerializer.parseMetadataSection(sectionName, jsonobject);
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        InputStream inputStream = get("pack.mcmeta");
        if (inputStream == null) {
            throw new FileNotFoundException("pack.mcmeta does not exist");
        }
        return readMetadata(metadataSerializer, inputStream, metadataSectionName);
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        InputStream inputStream = get("pack.png");
        if (inputStream == null) {
            throw new FileNotFoundException("pack.png does not exist");
        }
        return TextureUtil.readBufferedImage(inputStream);
    }

    @Override
    public String getPackName() {
        return "dollan";
    }

}