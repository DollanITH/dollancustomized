package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.blocks.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabRoad extends CreativeTabs {

    public TabRoad() {
        super("tabRoad");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.asphalt);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);

        items.sort((o1, o2) -> {
            // 1. 防御性判空处理
            if (o1.isEmpty() && o2.isEmpty()) return 0;
            if (o1.isEmpty()) return -1;
            if (o2.isEmpty()) return 1;

            ResourceLocation name1 = o1.getItem().getRegistryName();
            ResourceLocation name2 = o2.getItem().getRegistryName();
            // 只取路径部分（忽略modid），如果为null给个默认值
            String s1 = name1 != null ? name1.getResourcePath() : "";
            String s2 = name2 != null ? name2.getResourcePath() : "";

            // 2. 获取材质前缀
            String base1 = getBaseName(s1);
            String base2 = getBaseName(s2);

            // 3. 第一级排序：按材质前缀排序 (如 asphalt 和 asphalt_slab 的前缀都是 asphalt)
            int baseCompare = String.CASE_INSENSITIVE_ORDER.compare(base1, base2);
            if (baseCompare != 0) {
                return baseCompare;
            }

            // 4. 第二级排序：如果前缀相同，判断是否为台阶
            boolean isSlab1 = s1.endsWith("_slab");
            boolean isSlab2 = s2.endsWith("_slab");

            // 如果一个是台阶，一个不是
            if (isSlab1 && !isSlab2) return 1;  // o1是台阶，排在o2后面
            if (!isSlab1 && isSlab2) return -1; // o2是台阶，排在o1后面

            // 5. 兜底排序：如果前缀相同且类型相同（比如两个都是台阶，或两个都不是），按完整名字排
            return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
        });
    }

    // 辅助方法：提取材质前缀
    private String getBaseName(String path) {
        if (path.endsWith("_slab")) {
            return path.substring(0, path.length() - 5); // 截掉 "_slab" (5个字符)
        }
        return path; // 不是台阶，原样返回
    }


}