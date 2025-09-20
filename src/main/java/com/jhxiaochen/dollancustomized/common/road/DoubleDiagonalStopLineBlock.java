package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.model.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
@Registry.ModElement.Tag
public class DoubleDiagonalStopLineBlock extends Registry.ModElement {
    @GameRegistry.ObjectHolder("dollancustomized:stop_line_cross_1")
    public static Block block = null;

    public DoubleDiagonalStopLineBlock(Registry registry) {
        super(registry);
    }

    @Override
    public void initElements() {
        registry.blocks.add(() -> new Register( ).setRegistryName("stop_line_cross_1").setUnlocalizedName("stop_line_cross_1"));
        registry.items.add(() -> new ItemMultiTexture(block, block, var1 -> Register.EnumVarient.byMetadata(var1.getMetadata( )).getUnlocalizedName()).setRegistryName(block.getRegistryName( )));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), Register.EnumVarient.WHITE.getMetadata( ), new ModelResourceLocation("dollancustomized:road_stop_diagonal_double_white_line", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), Register.EnumVarient.WHITE_1.getMetadata( ), new ModelResourceLocation("dollancustomized:road_stop_diagonal_double_white_line_1", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), Register.EnumVarient.YELLOW.getMetadata( ), new ModelResourceLocation("dollancustomized:road_stop_diagonal_double_yellow_line", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), Register.EnumVarient.YELLOW_1.getMetadata( ), new ModelResourceLocation("dollancustomized:road_stop_diagonal_double_yellow_line_1", "inventory"));
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder( ).withName(DoubleDiagonalStopLineBlock.Register.VARIENT).build( ));
    }

    public static class Register extends BlockAsphaltHorizontal {

        public enum EnumVarient implements IStringSerializable {
            WHITE(0, "stop_diagonal_double_white_line", "white"),
            WHITE_1(1, "stop_diagonal_double_white_line_1", "white_1"),
            YELLOW(2, "stop_diagonal_double_yellow_line", "yellow"),
            YELLOW_1(3, "stop_diagonal_double_yellow_line_1", "yellow_1");

            final                int           metadata;
            private final        String        name;
            private final        String        unlocalizedName;
            private static final EnumVarient[] METADATA_LOOKUP = new EnumVarient[values( ).length];

            EnumVarient(int metadataIn, String name, String unlocalizedNameIn) {
                this.metadata = metadataIn;
                this.name = name;
                this.unlocalizedName = unlocalizedNameIn;
            }

            public String toString() {
                return this.name;
            }

            public String getName() {
                return this.name;
            }

            public String getUnlocalizedName() {
                return this.unlocalizedName;
            }

            public static EnumVarient byMetadata(int metadata) {
                if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                    metadata = 0;
                }

                return METADATA_LOOKUP[metadata];
            }


            public int getMetadata() {
                return this.metadata;
            }

            static {
                for (EnumVarient varient : values( )) {
                    METADATA_LOOKUP[varient.getMetadata( )] = varient;
                }
            }

        }

        public static final PropertyEnum<EnumVarient>
                VARIENT = PropertyEnum.create("varient", EnumVarient.class);

        public Register() {
        }

        public int damageDropped(IBlockState state) {
            return ((state.getValue(VARIENT)).getMetadata( ));
        }

        @Override
        public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
            for (EnumVarient subtype : EnumVarient.values( )) {
                items.add(new ItemStack(this, 1, subtype.getMetadata( )));
            }

        }

        @Override
        @Nonnull
        public IBlockState getStateFromMeta(int meta) {
            EnumVarient varient = EnumVarient.WHITE;
            if (meta >= 4 && meta <= 7) varient = EnumVarient.WHITE_1;
            else if (meta >= 8 && meta <= 11) varient = EnumVarient.YELLOW;
            else if (meta >= 12 && meta <= 15) varient = EnumVarient.YELLOW_1;
            return getDefaultState( ).withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(VARIENT, varient);
        }

        @Override
        public int getMetaFromState(IBlockState state) {
            EnumVarient varient = state.getValue(VARIENT);
            int v = 0x0;
            if (varient == EnumVarient.WHITE_1) v = 0x8;
            else if (varient == EnumVarient.YELLOW) v = 0x4;
            else if (varient == EnumVarient.YELLOW_1) v = 0xc;
            return state.getValue(FACING).getHorizontalIndex( ) | v;
        }

        @Override
        public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
            super.getPickBlock(state, target, world, pos, player);
            return new ItemStack(this, 1, (state.getValue(VARIENT).getMetadata( )));
        }

        @Override
        protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, new IProperty[]{FACING, VARIENT});
        }

        @Override
        public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
            IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
            EnumVarient varient = EnumVarient.byMetadata(meta);
            state = state.withProperty(FACING, placer.getHorizontalFacing( )).withProperty(VARIENT, varient);
            return state;
        }
    }
}