package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class JacarandaSlab extends BlockSlab {
    public static final PropertyEnum<EnumType>
                                VARIANT = PropertyEnum.create("variant", EnumType.class);

    public JacarandaSlab() {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(DollanSounds.CHERRY_WOOD);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iBlockState = this.getDefaultState().withProperty(VARIANT, EnumType.NORMAL);
        return !isDouble() ? iBlockState.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP) : iBlockState;
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (!isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName( );
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumType.NORMAL;
    }



    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(jacaranda_slab_item);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess access, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(
                jacaranda_slab_item,
                this.isDouble() ? 2 : 1
        ));
    }

    enum EnumType implements IStringSerializable {
        NORMAL("normal");

        private final String name;

        EnumType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }

    public static BlockSlab jacaranda_slab        = null;
    public static Item      jacaranda_slab_item   = null;
    public static BlockSlab jacaranda_double_slab = null;

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, VARIANT, HALF);
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry( ).register(
                    jacaranda_slab =
                            (BlockSlab) new JacarandaHalfSlab().setUnlocalizedName("jacaranda_slab").setRegistryName(Reference.MOD_ID, "jacaranda_slab"));
            event.getRegistry( ).register(
                    jacaranda_double_slab =
                            (BlockSlab) new JacarandaDoubleSlab().setUnlocalizedName("jacaranda_double_slab").setRegistryName(Reference.MOD_ID, "jacaranda_double_slab"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry( ).register(jacaranda_slab_item = new ItemSlab(jacaranda_slab,jacaranda_slab,jacaranda_double_slab).setRegistryName(jacaranda_slab.getRegistryName( )));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(jacaranda_slab), 0, new ModelResourceLocation(jacaranda_slab.getRegistryName( ), "inventory"));
        }
    }
}
