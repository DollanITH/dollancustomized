package com.jhxiaochen.dollancustomized.common.block.road;

import com.google.common.collect.Maps;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class HeightLimitSign extends BlockRoadSign {
    public HeightLimitSign(String name) {
        super(name);
    }

    public enum EnumHeight implements IStringSerializable {
        HEIGHT_1(0, "subtype_1", "height_1"),
        HEIGHT_2(1, "subtype_2", "height_2");

        final int metadata;
        private final String name;
        private final String unlocalizedName;
        private static final EnumHeight[] METADATA_LOOKUP = new EnumHeight[values().length];

        EnumHeight(int metadataIn, String name, String unlocalizedNameIn) {
            this.metadata = metadataIn;
            this.name = name;
            this.unlocalizedName = unlocalizedNameIn;
        }

        public String toString() { return this.name; }

        public String getName() { return this.name; }

        public String getUnlocalizedName() { return this.unlocalizedName; }

        public static EnumHeight byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }
            return METADATA_LOOKUP[metadata];
        }

        public int getMetadata() { return this.metadata; }

        static {
            for (EnumHeight height : values()) {
                METADATA_LOOKUP[height.getMetadata()] = height;
            }
        }
    }

    public static final PropertyEnum<EnumHeight> TYPE = PropertyEnum.create("type", EnumHeight.class);

    public int damageDropped(IBlockState state) {
        return (state.getValue(TYPE)).getMetadata();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, state.getValue(TYPE).getMetadata());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(TYPE, EnumHeight.byMetadata(meta)).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, WEST, EAST, NORTH, SOUTH, UP, DOWN, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
                .withProperty(TYPE, EnumHeight.byMetadata(meta >> 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | state.getValue(TYPE).getMetadata() << 2;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumHeight height : EnumHeight.values()) {
            items.add(new ItemStack(this, 1, height.getMetadata()));
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block block;
        public static Block block1;
        public static Block block2;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(block = new HeightLimitSign("road_sign_height_limit_3"));
            event.getRegistry().register(block1 = new HeightLimitSign("road_sign_height_limit_4"));
            event.getRegistry().register(block2 = new HeightLimitSign("road_sign_height_limit_5"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            ItemMultiTexture item = new ItemMultiTexture(block, block, stack -> {
                EnumHeight type = EnumHeight.byMetadata(stack.getMetadata());
                return type.getUnlocalizedName();
            });

            item.setRegistryName(block.getRegistryName());
            ItemMultiTexture item1 = new ItemMultiTexture(block1, block1, stack -> {
                EnumHeight type = EnumHeight.byMetadata(stack.getMetadata());
                return type.getUnlocalizedName();
            });

            item1.setRegistryName(block1.getRegistryName());
            ItemMultiTexture item2 = new ItemMultiTexture(block2, block2, stack -> {
                EnumHeight type = EnumHeight.byMetadata(stack.getMetadata());
                return type.getUnlocalizedName();
            });

            item2.setRegistryName(block2.getRegistryName());
            event.getRegistry().register(item);
            event.getRegistry().register(item1);
            event.getRegistry().register(item2);
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            Item item = Item.getItemFromBlock(block);
            Item item1 = Item.getItemFromBlock(block1);
            Item item2 = Item.getItemFromBlock(block2);

            for (EnumHeight height : EnumHeight.values()) {
                int meta = height.getMetadata();
                String suffix = height.getName();
                ModelResourceLocation location = new ModelResourceLocation(
                        block.getRegistryName() + "_" + suffix,
                        "inventory"
                );
                ModelResourceLocation location1 = new ModelResourceLocation(
                        block1.getRegistryName() + "_" + suffix,
                        "inventory"
                );
                ModelResourceLocation location2 = new ModelResourceLocation(
                        block2.getRegistryName() + "_" + suffix,
                        "inventory"
                );

                // 注册
                ModelLoader.setCustomModelResourceLocation(item, meta, location);
                ModelLoader.setCustomModelResourceLocation(item1, meta, location1);
                ModelLoader.setCustomModelResourceLocation(item2, meta, location2);
            }

            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    EnumHeight typeValue = state.getValue(TYPE);
                    String registryName = block.getRegistryName().toString();
                    String modelName = registryName + "_" + typeValue.getName();

                    Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                    map.remove(TYPE);
                    String variantString = this.getPropertyString(map);

                    return new ModelResourceLocation(modelName, variantString);
                }
            });
            ModelLoader.setCustomStateMapper(block1, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    EnumHeight typeValue = state.getValue(TYPE);
                    String registryName = block1.getRegistryName().toString();
                    String modelName = registryName + "_" + typeValue.getName();

                    Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                    map.remove(TYPE);
                    String variantString = this.getPropertyString(map);

                    return new ModelResourceLocation(modelName, variantString);
                }
            });
            ModelLoader.setCustomStateMapper(block2, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    EnumHeight typeValue = state.getValue(TYPE);
                    String registryName = block2.getRegistryName().toString();
                    String modelName = registryName + "_" + typeValue.getName();

                    Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                    map.remove(TYPE);
                    String variantString = this.getPropertyString(map);

                    return new ModelResourceLocation(modelName, variantString);
                }
            });
        }
    }
}
