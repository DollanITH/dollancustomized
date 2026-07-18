package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class JacarandaLog extends BlockLog {

    public JacarandaLog(@Nullable Block stripped) {
        super();
        this.stripped = stripped;
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(2.0F).setResistance(5.0F);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
    }

    public JacarandaLog() {
        super();
        this.stripped = null;
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(2.0F).setResistance(5.0F);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
    }

    private final Block stripped;

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        // 检查是否是斧头
        if (stack.getItem() instanceof ItemAxe) {
            // 如果是原木（不是去皮的），可以去皮
            if (stripped != null && !world.isRemote) {
                // 替换方块为去皮原木
                world.setBlockState(pos, stripped.getDefaultState(), 3);

                // 添加声音效果
                world.playSound(null, pos, DollanSounds.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

                // 损耗斧头
                stack.damageItem(1, player);

                return true;
            }
        }

        return false;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_log;
        public static Block jacaranda_wood;
        public static Block stripped_jacaranda_log;
        public static Block stripped_jacaranda_wood;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            // 注册蓝花楹木原木
            jacaranda_log = new JacarandaLog(stripped_jacaranda_log);
            event.getRegistry().register(jacaranda_log.setUnlocalizedName("jacaranda_log").setRegistryName(Reference.MOD_ID, "jacaranda_log"));

            // 注册蓝花楹木
            jacaranda_wood = new JacarandaLog(stripped_jacaranda_wood);
            event.getRegistry().register(jacaranda_wood.setUnlocalizedName("jacaranda_wood").setRegistryName(Reference.MOD_ID, "jacaranda_wood"));

            // 注册去皮蓝花楹木原木
            stripped_jacaranda_log = new JacarandaLog();
            event.getRegistry().register(stripped_jacaranda_log.setUnlocalizedName("stripped_jacaranda_log").setRegistryName(Reference.MOD_ID, "stripped_jacaranda_log"));

            // 注册去皮蓝花楹木
            stripped_jacaranda_wood = new JacarandaLog();
            event.getRegistry().register(stripped_jacaranda_wood.setUnlocalizedName("stripped_jacaranda_wood").setRegistryName(Reference.MOD_ID, "stripped_jacaranda_wood"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(new ItemBlock(jacaranda_log).setRegistryName(jacaranda_log.getRegistryName()));
            event.getRegistry().register(new ItemBlock(jacaranda_wood).setRegistryName(jacaranda_wood.getRegistryName()));
            event.getRegistry().register(new ItemBlock(stripped_jacaranda_log).setRegistryName(stripped_jacaranda_log.getRegistryName()));
            event.getRegistry().register(new ItemBlock(stripped_jacaranda_wood).setRegistryName(stripped_jacaranda_wood.getRegistryName()));
        }
    }
}