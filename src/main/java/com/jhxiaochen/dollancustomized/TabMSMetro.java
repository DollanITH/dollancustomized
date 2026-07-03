package com.jhxiaochen.dollancustomized;

import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabMSMetro extends CreativeTabs {

    public TabMSMetro() {
        super("tabMSMetro");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(DollanBlocks.mingsha_S1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);

        items.sort((o1, o2) -> {
            ResourceLocation loc1 = o1.getItem( ).getRegistryName( );
            ResourceLocation loc2 = o2.getItem( ).getRegistryName( );

            // 防御性编程，防止空指针
            if (loc1 == null && loc2 == null) return 0;
            if (loc1 == null) return 1;
            if (loc2 == null) return -1;

            String name1 = loc1.toString( ); // 例如 "dollancustomized:dollan_line_1"
            String name2 = loc2.toString( ); // 例如 "dollancustomized:dollan_line_2"

            // --- 第一步：比较名字前缀 ---
            // 我们需要确定它们是不是"同一类"物品（即除了末尾数字外都一样）

            // 找到最后一个下划线的位置
            int lastUnderscore1 = name1.lastIndexOf('_');
            int lastUnderscore2 = name2.lastIndexOf('_');

            // 截取数字前面的字符串部分（前缀）
            // 如果找不到下划线，就把整个名字当作前缀（数字默认为0）
            String prefix1 = (lastUnderscore1 > 0) ? name1.substring(0, lastUnderscore1 + 1) : name1;
            String prefix2 = (lastUnderscore2 > 0) ? name2.substring(0, lastUnderscore2 + 1) : name2;

            // 比较前缀
            int prefixCompare = String.CASE_INSENSITIVE_ORDER.compare(prefix1, prefix2);
            if (prefixCompare != 0) {
                return prefixCompare; // 如果前缀不同，直接按字母顺序排
            }

            // --- 第二步：比较名字末尾的数字 ---
            // 如果前缀相同（比如都是 dollan_line_），则比较后面的数字

            int num1 = extractNumber(name1, lastUnderscore1);
            int num2 = extractNumber(name2, lastUnderscore2);

            return Integer.compare(num1, num2);
        });
    }

    /**
     * 辅助方法：从字符串末尾提取数字
     */
    private int extractNumber(String str, int startIndex) {
        if (startIndex == -1) return 0; // 没有下划线，默认为0

        try {
            // 截取下划线后的部分，例如 "1", "10", "abc"
            String numberStr = str.substring(startIndex + 1);
            // 尝试解析为整数
            return Integer.parseInt(numberStr);
        } catch (Exception e) {
            // 如果后缀不是纯数字（例如 "dollan_line_red"），解析会失败
            // 返回 0 或者其他默认值，保证不崩溃
            return 0;
        }
    }
}