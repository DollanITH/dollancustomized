package com.jhxiaochen.dollancustomized;

import net.minecraftforge.fml.common.Loader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class DollanMixinConfigPlugin implements IMixinConfigPlugin {
    private static final boolean OPTIFINE_LOADED = detectOptiFine();

    private static boolean detectOptiFine() {
        try {
            Class.forName("optifine.OptiFineForgeTweaker");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean detectAquaacrobatics() {
        try {
            Class.forName("com.fuzs.aquaacrobatics.client.handler.FogHandler");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onLoad(String s) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String s, String s1) {
        if (s1.contains(".mod.gnetum.")) {
            return Loader.isModLoaded("gnetum");
        }
        if (s1.contains(".mod.futuremc.")) {
            return Loader.isModLoaded("futuremc");
        }
        if (s1.contains(".mod.railcraft.")) {
            return Loader.isModLoaded("railcraft");
        }
        if (s1.contains(".mod.waterfog.")) {
            return Loader.isModLoaded("nothirium") && detectAquaacrobatics() && OPTIFINE_LOADED;
        }
        if (s1.contains("mod.sakura.")) {
            return Loader.isModLoaded("sakura");
        }
        if (s1.contains(".mod.cherryon.")) {
            return Loader.isModLoaded("suikecherry");
        }
        if (s1.contains(".mod.moarsigns.")) {
            return Loader.isModLoaded("moarsigns");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }
}
