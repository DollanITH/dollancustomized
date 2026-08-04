package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class DollanDoor extends BlockDoor {
    public DollanDoor() {
        super(Material.WOOD);
        this.setHardness(3.0F);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Registry.item_jacaranda_door);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : new ItemStack(Registry.item_jacaranda_door).getItem( );
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_door = null;
        public static Item item_jacaranda_door = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_door = new DollanDoor()
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setUnlocalizedName("jacaranda_door")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_door"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(item_jacaranda_door = new ItemDoor(jacaranda_door).setCreativeTab(DollanCustomized.WOOD_TAB).setUnlocalizedName(Reference.MOD_ID + "_" + "jacaranda_door").setRegistryName(jacaranda_door.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            ModelLoader.setCustomModelResourceLocation(item_jacaranda_door, 0, new ModelResourceLocation(item_jacaranda_door.getRegistryName(), "inventory"));
            ModelLoader.setCustomStateMapper(jacaranda_door, new StateMap.Builder().ignore(DollanDoor.POWERED).build());
        }

    }
}
