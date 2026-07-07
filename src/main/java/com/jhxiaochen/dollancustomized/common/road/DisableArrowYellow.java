package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DisableArrowYellow extends ArrowNew {
    public DisableArrowYellow(String name) {
        super(name);
    }

    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(-0.5D, 0.0D, -0.5D, 1.5D, 0.05D, 1.5D);
    protected static final AxisAlignedBB AABB_BOTTOM_DOWN = new AxisAlignedBB(-0.5D, -0.5D, -0.5D, 1.5D, 0.1D, 1.5D);

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(TYPE) == MarkType.FULL ? AABB_BOTTOM_HALF : AABB_BOTTOM_DOWN;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block block;
        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event){
            event.getRegistry().register(block = new DisableArrowYellow("arrow_no_diaotou"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(new ItemBlock(block).setRegistryName("arrow_no_diaotou"));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:arrow_no_diaotou", "inventory"));
        }
    }
}
