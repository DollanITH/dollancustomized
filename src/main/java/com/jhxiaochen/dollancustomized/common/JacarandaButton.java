package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import suike.suikecherry.sound.Sound;

import javax.annotation.Nullable;

public class JacarandaButton extends BlockButton {
    public JacarandaButton() {
        super(true);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
        this.setUnlocalizedName("jacaranda_button").setHardness(0.5F);
        this.setRegistryName(Reference.MOD_ID, "jacaranda_button");
    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, World world, BlockPos pos) {
        Sound.playSound(world, pos, "block.cherry_button.click", 2.0F, 1.0F);
    }

    @Override
    protected void playReleaseSound(World world, BlockPos pos) {
        Sound.playSound(world, pos, "block.cherry_button.restore", 2.0F, 1.0F);
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_button = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry( ).register(
                    jacaranda_button = new JacarandaButton( ));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry( ).register(new ItemBlock(jacaranda_button).setCreativeTab(DollanCustomized.WOOD_TAB).setUnlocalizedName(Reference.MOD_ID + "_" + "jacaranda_door").setRegistryName(jacaranda_button.getRegistryName( )));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            DollanBlocks.SimpleBlock(jacaranda_button);
        }
    }
}
