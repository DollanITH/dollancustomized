package com.jhxiaochen.dollancustomized.client.event;

import com.jhxiaochen.dollancustomized.client.model.OffsetBakedModel;
import com.jhxiaochen.dollancustomized.common.block.road.IronHorizontal;
import com.jhxiaochen.dollancustomized.registry.DollanItems;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ModelHandler {

    private static final Set<Class<?>> TARGET_BLOCK_CLASSES = new HashSet<>(Arrays.asList(
            IronHorizontal.class
    ));

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        float yOffset = 0.015F; // 增大到 0.015F，确保在远距离时也能解决

        ArrayList<ModelResourceLocation> mrls = new ArrayList<>(event.getModelRegistry().getKeys());

        for (ModelResourceLocation mrl : mrls) {
            if (!mrl.getNamespace().equals("dollancustomized")) continue;

            IBakedModel original = event.getModelRegistry().getObject(mrl);
            if (original == null) {
                System.out.println("Model not found: " + mrl);
                continue;
            }

            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(mrl.getNamespace(), mrl.getPath()));
            if (block == null) continue;

            boolean shouldOffset = false;
            for (Class<?> targetClass : TARGET_BLOCK_CLASSES) {
                if (targetClass.isInstance(block)) {
                    shouldOffset = true;
                    break;
                }
            }

            if (shouldOffset) {
                event.getModelRegistry().putObject(mrl, new OffsetBakedModel(original, yOffset));
            }
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        DollanBlocks.registerBlockAndModels();
        DollanBlocks.registerAllCustomModels();
        WoodBlocks.register();
        DollanItems.registerModel();
        DollanItems.register();
        System.out.println("DollanCustomized Registered Models!");
    }

    @SubscribeEvent
    public static void onBlockColors(ColorHandlerEvent.Block evt) {
        final BlockColors blockColors = evt.getBlockColors();
        if (WoodBlocks.mangrove_leaves != null) {
            blockColors.registerBlockColorHandler(new IBlockColor()
            {
                public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
                {
                    return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : 9619016;
                }
            }, WoodBlocks.mangrove_leaves);
        }
    }

    @SubscribeEvent
    public static void onItemColors(ColorHandlerEvent.Item evt) {
        final ItemColors itemColors = evt.getItemColors();
        if (WoodBlocks.mangrove_leaves != null) {
            itemColors.registerItemColorHandler(new IItemColor()
            {
                public int colorMultiplier(ItemStack stack, int tintIndex)
                {
                    return 9619016;
                }
            }, Item.getItemFromBlock(WoodBlocks.mangrove_leaves));
        }
    }

}
