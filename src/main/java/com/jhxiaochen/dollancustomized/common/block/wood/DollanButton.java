package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import suike.suikecherry.sound.Sound;

import javax.annotation.Nullable;

public class DollanButton extends BlockButton {
    boolean cherry_sound;
    public DollanButton(boolean cherry_sound) {
        super(true);
        this.cherry_sound = cherry_sound;
        this.setHardness(0.5F);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, World world, BlockPos pos) {
        if (cherry_sound) {
            Sound.playSound(world, pos, "block.cherry_button.click", 2.0F, 1.0F);
        } else {
            world.playSound(player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }

    }

    @Override
    protected void playReleaseSound(World world, BlockPos pos) {
        if (cherry_sound) {
            Sound.playSound(world, pos, "block.cherry_button.restore", 2.0F, 1.0F);
        }
        else{
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_button = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_button = new DollanButton(true)
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setUnlocalizedName("jacaranda_button")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_button"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(jacaranda_button)
                    .setCreativeTab(DollanCustomized.WOOD_TAB)
                    .setUnlocalizedName(Reference.MOD_ID + "_" + "jacaranda_button")
                    .setRegistryName(jacaranda_button.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            DollanBlocks.SimpleBlock(jacaranda_button);
        }
    }
}
