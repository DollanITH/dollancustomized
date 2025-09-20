package com.jhxiaochen.dollancustomized.fix;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber
public class Remaper {
    private static final Map<String, String>          blockRemappings = new HashMap<>();
    private static final Map<String, String>          itemRemappings  = new HashMap<>();
    private static final Map<String, String>     tileRemappings  = new HashMap<>();

    private static final Set<String> ignoredMappings = new HashSet<>();

    static {
        blockRemappings.put("arrow_diaotou_sl","arrow_diaotou");
        blockRemappings.put("arrow_left_diaotou_sl","arrow_left_diaotou");
        blockRemappings.put("arrow_right_staright_sl","arrow_right_straight");
        blockRemappings.put("arrow_left_staright_sl","arrow_left_straight");
        blockRemappings.put("arrow_staright_diaotou_sl","arrow_straight_diaotou");
        blockRemappings.put("arrow_staright_sl","arrow_straight");
        blockRemappings.put("arrow_left_sl","arrow_left");
        blockRemappings.put("arrow_right_sl","arrow_right");
        blockRemappings.put("arrow_left_right_staright_sl","arrow_left_right_straight");
        blockRemappings.put("arrow_left_right_sl","arrow_left_right");
        blockRemappings.put("arrow_confluence_right_sl","arrow_confluence_right");
        blockRemappings.put("arrow_confluence_left_sl","arrow_confluence_left");

        blockRemappings.put("arrow_left_right_staright","arrow_left_right_straight");
        blockRemappings.put("arrow_right_staright","arrow_right_straight");
        blockRemappings.put("arrow_left_staright","arrow_left_straight");
        blockRemappings.put("arrow_staright_diaotou","arrow_straight_diaotou");
        blockRemappings.put("arrow_staright","arrow_straight");

        itemRemappings.put("dollancraft", "dollan");
    }

    @SubscribeEvent
    public static void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> missingMapping : event.getMappings()) {
            ResourceLocation resourceLocation = missingMapping.key;

            String resourcePath = resourceLocation.getResourcePath();
            if (ignoredMappings.contains(resourcePath)) {
                missingMapping.ignore();
            } else if (blockRemappings.containsKey(resourcePath)) {
                ResourceLocation remappedResourceLocation = new ResourceLocation(resourceLocation.getResourceDomain(), blockRemappings.get(resourcePath));
                    Block remappedBlock = ForgeRegistries.BLOCKS.getValue(remappedResourceLocation);
                    if (remappedBlock != Blocks.AIR) {
                        missingMapping.remap(remappedBlock);
                    }
            }
        }
    } 

    @SubscribeEvent
    public static void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> missingMapping : event.getMappings()) {
            ResourceLocation resourceLocation = missingMapping.key;

            String resourcePath = resourceLocation.getResourcePath();
            if (ignoredMappings.contains(resourcePath)) {
                missingMapping.ignore();
            } else if (itemRemappings.containsKey(resourcePath)) {
                ResourceLocation remappedResourceLocation = new ResourceLocation(resourceLocation.getResourceDomain(), itemRemappings.get(resourcePath));
                    Item remappedItem = ForgeRegistries.ITEMS.getValue(remappedResourceLocation);
                if (remappedItem != Items.AIR) {
                    missingMapping.remap(remappedItem);
                }
            } else if (blockRemappings.containsKey(resourcePath)) {
                ResourceLocation remappedResourceLocation = new ResourceLocation(resourceLocation.getResourceDomain(), blockRemappings.get(resourcePath));
                    Item remappedItem = ForgeRegistries.ITEMS.getValue(remappedResourceLocation);
                if (remappedItem != Items.AIR) {
                    missingMapping.remap(remappedItem);
                }
            }
        }
    }


    public static void registerFixable() {

    }

    private Remaper() {
    }

    @Nullable
    public static String getRemappedTileName(String resourcePath) {
        if (tileRemappings.containsKey(resourcePath)) {
            return Reference.MOD_ID + ":" + tileRemappings.get(resourcePath);
        }
        return null;
    }

    public static void addTileRemappingName(String oldName, String remappedName) {
        tileRemappings.put("dollancustomized." + WordUtils.uncapitalize(oldName), remappedName);
    }

}
