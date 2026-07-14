package com.jhxiaochen.dollancustomized.client.model;

import com.jhxiaochen.dollancustomized.common.road.IronHorizontal;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelBakeHandler {

    private static final Set<Class<?>> TARGET_BLOCK_CLASSES = new HashSet<>(Arrays.asList(
            IronHorizontal.class
    ));

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        float yOffset = 0.015F; // 增大到 0.015F，确保在远距离时也能解决

        ArrayList<ModelResourceLocation> mrls = new ArrayList<>(event.getModelRegistry().getKeys());

        for (ModelResourceLocation mrl : mrls) {
            if (!mrl.getResourceDomain().equals("dollancustomized")) continue;

            IBakedModel original = event.getModelRegistry().getObject(mrl);
            if (original == null) {
                System.out.println("Model not found: " + mrl);
                continue;
            }

            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(mrl.getResourceDomain(), mrl.getResourcePath()));
            if (block == null) continue;

            boolean shouldOffset = false;
            for (Class<?> targetClass : TARGET_BLOCK_CLASSES) {
                if (targetClass.isInstance(block)) {
                    shouldOffset = true;
                    break;
                }
            }

            if (shouldOffset) {
                System.out.println("Applying offset to: " + mrl);
                event.getModelRegistry().putObject(mrl, new OffsetBakedModel(original, yOffset));
            }
        }
    }
}
