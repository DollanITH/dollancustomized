package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class TabWood extends CreativeTabs {

    public TabWood() {
        super("tabWood");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(WoodBlocks.jacaranda_log);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);

        // 定义物品类型的优先级顺序
        final String[] ORDER = {
            "log", "wood", "stripped", "planks", "stairs",
            "slab", "fence", "fence_gate", "door", "trapdoor",
            "pressure_plate", "button" , "sign" , "hanging_sign"
        };

        items.sort((o1, o2) -> {
            ResourceLocation name1 = o1.getItem().getRegistryName();
            ResourceLocation name2 = o2.getItem().getRegistryName();
            String s1 = name1 != null ? name1.getResourcePath() : "";
            String s2 = name2 != null ? name2.getResourcePath() : "";

            // 先按木材种类排序（mangrove, jacaranda）
            String woodType1 = getWoodType(s1);
            String woodType2 = getWoodType(s2);
            int woodTypeCompare = String.CASE_INSENSITIVE_ORDER.compare(woodType1, woodType2);
            if (woodTypeCompare != 0) {
                return woodTypeCompare;
            }

            // 再按物品类型顺序排序
            int priority1 = getItemPriority(s1, ORDER, woodType1);
            int priority2 = getItemPriority(s2, ORDER, woodType2);
            if (priority1 != priority2) {
                return Integer.compare(priority1, priority2);
            }

            // 最后按物品名称排序
            return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
        });
    }

    private String getWoodType(String itemName) {
        if (itemName == null) return "";

        // 检查是否是去皮物品
        if (itemName.startsWith("stripped_")) {
            // 提取去皮物品的木材类型（stripped_jacaranda_log -> jacaranda）
            String strippedName = itemName.substring(9); // 去掉"stripped_"
            int underscoreIndex = strippedName.indexOf('_');
            if (underscoreIndex > 0) {
                return strippedName.substring(0, underscoreIndex);
            }
            return "";
        }

        // 普通物品提取木材类型
        int underscoreIndex = itemName.indexOf('_');
        if (underscoreIndex > 0) {
            return itemName.substring(0, underscoreIndex);
        }

        return "";
    }

    private int getItemPriority(String itemName, String[] order, String woodType) {
        if (itemName == null) return order.length;

        // 处理去皮物品的特殊情况
        if (itemName.startsWith("stripped_")) {
            // 去皮物品在wood之后，planks之前
            return 2;
        }

        // 检查其他类型
        for (int i = 0; i < order.length; i++) {
            if (itemName.contains(order[i])) {
                // 特殊处理wood类型，避免和stripped混淆
                if (order[i].equals("wood") && itemName.startsWith("stripped_")) {
                    continue;
                }
                return i;
            }
        }

        // 如果都不匹配，排在最后
        return order.length;
    }

}