package com.jhxiaochen.dollancustomized.client.event;

import com.google.common.collect.Maps;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.*;
import com.jhxiaochen.dollancustomized.common.block.light.BlockFrogLight;
import com.jhxiaochen.dollancustomized.common.block.metro.BlockStationEndDoor;
import com.jhxiaochen.dollancustomized.common.block.metro.OverheadSteelDiagonalSupport;
import com.jhxiaochen.dollancustomized.common.block.road.*;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanDoor;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanFenceGate;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanLeaves;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.block.BlockColored;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

/**
 * 客户端专属：方块状态映射（StateMapper）注册。
 * 必须独立于 DollanBlocks —— 服务端加载的类字节码中不能出现
 * StateMap/StateMapperBase/ModelLoader 引用（IStateMapper 是 client 类，
 * 即使被 side 守卫跳过，JVM 类验证阶段也会尝试解析而崩溃）。
 * 本类仅由客户端 ModelRegistryEvent 触发，服务端从不加载。
 */
@SideOnly(Side.CLIENT)
public class BlockModelRegistry {

    public static void registerStateMappers() {
        ModelLoader.setCustomStateMapper(DollanBlocks.beautiful_marble,
                new StateMap.Builder().withSuffix("_marble").withName(BlockMarble.VARIANT).build());
        if (!Loader.isModLoaded("depthsupdate")) {
            ModelLoader.setCustomStateMapper(DollanBlocks.cracked_deepslate,
                    new StateMap.Builder().withSuffix("_cracked_deepslate").withName(BlockCrackedDeepSlate.VARIANT).build());
            ModelLoader.setCustomStateMapper(DollanBlocks.deepslate,
                    new StateMap.Builder().withName(BlockDeepSlate.VARIANT).build());
        }
        ModelLoader.setCustomStateMapper(DollanBlocks.elder_prismarine,
                new StateMap.Builder().withName(BlockElderPrismarine.VARIANT).build());
        ModelLoader.setCustomStateMapper(DollanBlocks.limestone,
                new StateMap.Builder().withName(BlockLimeStone.VARIANT).build());
        ModelLoader.setCustomStateMapper(DollanBlocks.polished_blackstone,
                new StateMap.Builder().withName(BlockPolishedBlackStone.VARIANT).build());
        ModelLoader.setCustomStateMapper(DollanBlocks.froglight,
                new StateMap.Builder().withName(BlockFrogLight.COLOR).withSuffix("_froglight").build());
        ModelLoader.setCustomStateMapper(DollanBlocks.marble_tile,
                new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_marble_tile").build());
        ModelLoader.setCustomStateMapper(DollanBlocks.stop_line,
                new StateMap.Builder().withName(StopLineBlock.VARIENT).build());
        ModelLoader.setCustomStateMapper(DollanBlocks.stop_line_slab,
                new StateMap.Builder().withName(StopLineBlockSlab.VARIENT).withSuffix("_slab").build());
        ModelLoader.setCustomStateMapper(DollanBlocks.stop_line_cross,
                new StateMap.Builder().withName(DiagonalStopLineBlock.VARIENT).build());
        ModelLoader.setCustomStateMapper(DollanBlocks.stop_line_cross_slab,
                new StateMap.Builder().withName(DiagonalStopLineBlockSlab.VARIENT).withSuffix("_slab").build());
        ModelLoader.setCustomStateMapper(DollanBlocks.overhead_steel_diagonal_support,
                new StateMap.Builder()
                        .withName(OverheadSteelDiagonalSupport.TYPE)
                        .withSuffix("_overhead_steel_diagonal_support").build()
        );
        ModelLoader.setCustomStateMapper(DollanBlocks.overhead_steel_diagonal_support_1,
                new StateMap.Builder()
                        .withName(OverheadSteelDiagonalSupport.TYPE)
                        .withSuffix("_overhead_steel_diagonal_support_1").build()
        );
        ModelLoader.setCustomStateMapper(DollanBlocks.station_end_door, new StateMap.Builder().withName(BlockStationEndDoor.HALF).withSuffix("_station_end_door").ignore(BlockStationEndDoor.POWERED).build());

        ModelLoader.setCustomStateMapper(DollanBlocks.height_limit_3, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                HeightLimitSign.EnumHeight typeValue = state.getValue(HeightLimitSign.TYPE);
                String registryName = DollanBlocks.height_limit_3.getRegistryName().getPath();
                String modelName = registryName + "_" + typeValue.getName();

                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                map.remove(HeightLimitSign.TYPE);
                String variantString = this.getPropertyString(map);

                return new ModelResourceLocation(Reference.MOD_ID + ":" + modelName, variantString);
            }
        });
        ModelLoader.setCustomStateMapper(DollanBlocks.height_limit_4, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                HeightLimitSign.EnumHeight typeValue = state.getValue(HeightLimitSign.TYPE);
                String registryName = DollanBlocks.height_limit_4.getRegistryName().getPath();
                String modelName = registryName + "_" + typeValue.getName();

                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                map.remove(HeightLimitSign.TYPE);
                String variantString = this.getPropertyString(map);

                return new ModelResourceLocation(Reference.MOD_ID + ":" + modelName, variantString);
            }
        });
        ModelLoader.setCustomStateMapper(DollanBlocks.height_limit_5, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                HeightLimitSign.EnumHeight typeValue = state.getValue(HeightLimitSign.TYPE);
                String registryName = DollanBlocks.height_limit_5.getRegistryName().getPath();
                String modelName = registryName + "_" + typeValue.getName();

                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                map.remove(HeightLimitSign.TYPE);
                String variantString = this.getPropertyString(map);

                return new ModelResourceLocation(Reference.MOD_ID + ":" + modelName, variantString);
            }
        });
    }

    /** 木材/红木类方块的 StateMapper 注册（原 WoodBlocks.register() 内的模型段）。 */
    public static void registerWoodStateMappers() {
        ModelLoader.setCustomStateMapper(WoodBlocks.jacaranda_door, new StateMap.Builder().ignore(DollanDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(WoodBlocks.jacaranda_fence_gate, new StateMap.Builder().ignore(DollanFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(WoodBlocks.mangrove_fence_gate, new StateMap.Builder().ignore(DollanFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(WoodBlocks.jacaranda_leaves, (new StateMap.Builder()).ignore(DollanLeaves.CHECK_DECAY, DollanLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(WoodBlocks.mangrove_leaves, (new StateMap.Builder()).ignore(DollanLeaves.CHECK_DECAY, DollanLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(WoodBlocks.mangrove_door, new StateMap.Builder().ignore(DollanDoor.POWERED).build());
    }
}
