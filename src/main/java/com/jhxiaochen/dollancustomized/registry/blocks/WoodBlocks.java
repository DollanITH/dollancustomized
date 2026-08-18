package com.jhxiaochen.dollancustomized.registry.blocks;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.wood.*;
import com.jhxiaochen.dollancustomized.common.block.wood.jacaranda.JacarandaLeaves;
import com.jhxiaochen.dollancustomized.common.block.wood.jacaranda.JacarandaSapling;
import com.jhxiaochen.dollancustomized.common.item.ItemDollanLeaves;
import com.jhxiaochen.dollancustomized.registry.DollanItems;
import com.jhxiaochen.dollancustomized.registry.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WoodBlocks {
    public static Block mangrove_planks = new DollanPlanks().setRegistryName(Reference.MOD_ID, "mangrove_planks").setUnlocalizedName("mangrove_planks");
    public static Block mangrove_button = new DollanButton(false)
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_button")
            .setRegistryName(Reference.MOD_ID, "mangrove_button");
    public static Block mangrove_door = new DollanDoor()
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_door")
            .setRegistryName(Reference.MOD_ID, "mangrove_door");
    public static Block mangrove_fence = new DollanFence()
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_fence")
            .setRegistryName(Reference.MOD_ID, "mangrove_fence");
    public static Block mangrove_pressure_plate = new DollanPressurePlate(true)
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_pressure_plate")
            .setRegistryName(Reference.MOD_ID, "mangrove_pressure_plate");
    public static Block mangrove_stairs = new DollanStairs(mangrove_planks)
            .setCreativeTab(DollanCustomized.WOOD_TAB)
            .setUnlocalizedName("mangrove_stairs")
            .setRegistryName(Reference.MOD_ID, "mangrove_stairs");
    public static Block mangrove_trapdoor = new DollanTrapdoor()
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_trapdoor")
            .setRegistryName(Reference.MOD_ID, "mangrove_trapdoor");
    public static Block mangrove_fence_gate = new DollanFenceGate()
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_fence_gate")
            .setRegistryName(Reference.MOD_ID, "mangrove_fence_gate");
    public static Block jacaranda_button = new DollanButton(true)
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_button")
            .setRegistryName(Reference.MOD_ID, "jacaranda_button");
    public static Block jacaranda_door = new DollanDoor()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_door")
            .setRegistryName(Reference.MOD_ID, "jacaranda_door");
    public static Block jacaranda_fence = new DollanFence()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_fence")
            .setRegistryName(Reference.MOD_ID, "jacaranda_fence");
    public static Block jacaranda_fence_gate = new DollanFenceGate()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_fence_gate")
            .setRegistryName(Reference.MOD_ID, "jacaranda_fence_gate");
    public static Block jacaranda_leaves = new JacarandaLeaves()
            .setSoundType(DollanSounds.CHERRY_LEAVES)
            .setUnlocalizedName("jacaranda_leaves")
            .setRegistryName(Reference.MOD_ID, "jacaranda_leaves");
    public static Block stripped_jacaranda_log = new DollanLog()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("stripped_jacaranda_log")
            .setRegistryName(Reference.MOD_ID, "stripped_jacaranda_log");
    public static Block stripped_jacaranda_wood = new DollanLog()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("stripped_jacaranda_wood")
            .setRegistryName(Reference.MOD_ID, "stripped_jacaranda_wood");
    public static Block jacaranda_log = new DollanLog(stripped_jacaranda_log)
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_log")
            .setRegistryName(Reference.MOD_ID, "jacaranda_log");
    public static Block jacaranda_wood = new DollanLog(stripped_jacaranda_wood)
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_wood")
            .setRegistryName(Reference.MOD_ID, "jacaranda_wood");
    public static Block jacaranda_planks = new DollanPlanks()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setHardness(2.0F).setResistance(5.0F)
            .setUnlocalizedName("jacaranda_planks")
            .setRegistryName(Reference.MOD_ID, "jacaranda_planks");
    public static Block jacaranda_stairs = new DollanStairs(jacaranda_planks)
            .setCreativeTab(DollanCustomized.WOOD_TAB)
            .setUnlocalizedName("jacaranda_stairs")
            .setRegistryName(Reference.MOD_ID, "jacaranda_stairs");
    public static Block jacaranda_pressure_plate = new DollanPressurePlate(true)
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setUnlocalizedName("jacaranda_pressure_plate")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_pressure_plate");
    public static Block jacaranda_sapling = new JacarandaSapling()
            .setSoundType(DollanSounds.CHERRY_LEAVES)
            .setUnlocalizedName("jacaranda_sapling")
            .setRegistryName(Reference.MOD_ID, "jacaranda_sapling");
    public static DollanSlab jacaranda_slab = (DollanSlab) new DollanSlab.DollanHalfSlab()
                    .setSoundType(DollanSounds.CHERRY_WOOD)
                    .setUnlocalizedName("jacaranda_slab")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_slab");
    public static DollanSlab jacaranda_double_slab = (DollanSlab) new DollanSlab.DollanDoubleSlab()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_double_slab")
            .setRegistryName(Reference.MOD_ID, "jacaranda_double_slab");
    public static DollanSlab mangrove_slab = (DollanSlab) new DollanSlab.DollanHalfSlab()
                    .setSoundType(SoundType.WOOD)
                    .setUnlocalizedName("mangrove_slab")
                    .setRegistryName(Reference.MOD_ID, "mangrove_slab");
    public static DollanSlab mangrove_double_slab = (DollanSlab) new DollanSlab.DollanDoubleSlab()
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_double_slab")
            .setRegistryName(Reference.MOD_ID, "mangrove_double_slab");
    public static Block jacaranda_trapdoor = new DollanTrapdoor()
            .setSoundType(DollanSounds.CHERRY_WOOD)
            .setUnlocalizedName("jacaranda_trapdoor")
            .setRegistryName(Reference.MOD_ID, "jacaranda_trapdoor");
    public static Block jacaranda_wall_sign  = new DollanWallSign()
         .setSoundType(DollanSounds.CHERRY_WOOD)
         .setUnlocalizedName("jacaranda_wall_sign")
         .setRegistryName(Reference.MOD_ID, "jacaranda_wall_sign");
    public static Block jacaranda_standing_sign = new DollanStandingSign()
        .setSoundType(DollanSounds.CHERRY_WOOD)
        .setUnlocalizedName("jacaranda_standing_sign")
        .setRegistryName(Reference.MOD_ID, "jacaranda_standing_sign");
    public static DollanHangingSignAttached jacaranda_hanging_sign_attached =
            (DollanHangingSignAttached) new DollanHangingSignAttached("jacaranda_hanging_sign_attached")
                .setSoundType(DollanSounds.CHERRY_WOOD);
    public static Block                     jacaranda_hanging_sign          = new DollanHangingSign("jacaranda_hanging_sign")
        .setSoundType(DollanSounds.CHERRY_WOOD);
    public static Block jacaranda_hanging_sign_wall = new DollanHangingSign("jacaranda_hanging_sign_wall")
        .setSoundType(DollanSounds.CHERRY_WOOD);

    public static Block mangrove_wall_sign  = new DollanWallSign()
          .setSoundType(SoundType.WOOD)
         .setUnlocalizedName("mangrove_wall_sign")
         .setRegistryName(Reference.MOD_ID, "mangrove_wall_sign");
    public static Block mangrove_standing_sign = new DollanStandingSign()
            .setSoundType(SoundType.WOOD)
            .setUnlocalizedName("mangrove_standing_sign")
        .setRegistryName(Reference.MOD_ID, "mangrove_standing_sign");
    public static DollanHangingSignAttached mangrove_hanging_sign_attached =
            (DollanHangingSignAttached) new DollanHangingSignAttached("mangrove_hanging_sign_attached")
                    .setSoundType(SoundType.WOOD);
    public static Block mangrove_hanging_sign
            = new DollanHangingSign("mangrove_hanging_sign")
            .setSoundType(SoundType.WOOD);
    public static Block mangrove_hanging_sign_wall = new DollanHangingSign("mangrove_hanging_sign_wall")
            .setSoundType(SoundType.WOOD);

    public static void register()
    {
        //block register
        registerBlock(mangrove_planks);
        registerBlock(jacaranda_button);
        registerBlock(jacaranda_door, null);
        registerBlock(jacaranda_fence);
        registerBlock(jacaranda_fence_gate);
        registerBlock(jacaranda_leaves, new ItemDollanLeaves(jacaranda_leaves));
        registerBlock(stripped_jacaranda_log);
        registerBlock(stripped_jacaranda_wood);
        registerBlock(jacaranda_wood);
        registerBlock(jacaranda_log);
        registerBlock(jacaranda_planks);
        registerBlock(jacaranda_stairs);
        registerBlock(jacaranda_pressure_plate);
        registerBlock(jacaranda_sapling);
        registerBlock(jacaranda_trapdoor);
        registerSignBlock(jacaranda_wall_sign, jacaranda_standing_sign);
        registerHangingSignBlock(jacaranda_hanging_sign, jacaranda_hanging_sign_wall, jacaranda_hanging_sign_attached);
        registerSignBlock(mangrove_wall_sign, mangrove_standing_sign);
        registerHangingSignBlock(mangrove_hanging_sign, mangrove_hanging_sign_wall, mangrove_hanging_sign_attached);
        registerSlabBlock(jacaranda_slab, jacaranda_double_slab);
        registerSlabBlock(mangrove_slab, mangrove_double_slab);

        // 红木方块注册
        registerBlock(mangrove_button);
        registerBlock(mangrove_door, DollanItems.item_mangrove_door);
        registerBlock(mangrove_fence);
        registerBlock(mangrove_pressure_plate);
        registerBlock(mangrove_stairs);
        registerBlock(mangrove_trapdoor);
        registerBlock(mangrove_fence_gate);

        ModelLoader.setCustomStateMapper(jacaranda_door, new StateMap.Builder().ignore(DollanDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(jacaranda_fence_gate, new StateMap.Builder().ignore(DollanFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(mangrove_fence_gate, new StateMap.Builder().ignore(DollanFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(jacaranda_leaves, (new StateMap.Builder()).ignore(JacarandaLeaves.CHECK_DECAY, JacarandaLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(mangrove_door, new StateMap.Builder().ignore(DollanDoor.POWERED).build());
    }

    public static void registerBlock(Block block, Item itemBlock) {
        boolean flag = itemBlock instanceof ItemBlock && !(itemBlock instanceof ItemMultiTexture);
        DollanBlocks.BLOCKS.add(block);
        if (itemBlock != null) {
            if (itemBlock.getRegistryName() == null && block.getRegistryName() != null) {
                itemBlock.setRegistryName(block.getRegistryName());
            }
            DollanItems.ITEMS.add(itemBlock);
        }
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (flag) {
                registerCustomModel(block, 0, block.getRegistryName().getResourcePath());
            }
        }
    }

    public static void registerSlabBlock(BlockSlab singleslab, BlockSlab doubleslab) {
        DollanBlocks.BLOCKS.add(singleslab);
        DollanBlocks.BLOCKS.add(doubleslab);
    }

    public static void registerSignBlock(Block sign, Block sign1) {
        DollanBlocks.BLOCKS.add(sign);
        DollanBlocks.BLOCKS.add(sign1);
    }

    public static void registerHangingSignBlock(Block sign, Block sign1, Block sign2) {
        DollanBlocks.BLOCKS.add(sign);
        DollanBlocks.BLOCKS.add(sign1);
        DollanBlocks.BLOCKS.add(sign2);
    }

    public static void registerBlock(Block block) {
        registerBlock(block, new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void registerCustomModel(net.minecraft.block.Block block, int metadata, String name) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ModelResourceLocation model = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, model);
        }
    }

}
