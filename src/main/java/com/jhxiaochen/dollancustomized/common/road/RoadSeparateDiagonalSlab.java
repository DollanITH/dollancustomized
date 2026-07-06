package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RoadSeparateDiagonalSlab extends Block {
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public enum EnumVariant implements IStringSerializable {
        WHITE_0(0, "white_0"),
        WHITE_1(1, "white_1"),
        YELLOW_0(2, "yellow_0"),
        YELLOW_1(3, "yellow_1");

        private static final EnumVariant[] METADATA_LOOKUP = new EnumVariant[values().length];
        private final int metadata;
        private final String name;

        EnumVariant(int metadataIn, String nameIn) {
            this.metadata = metadataIn;
            this.name = nameIn;
        }

        public int getMetadata() {
            return metadata;
        }

        public String getName() {
            return name;
        }

        public static EnumVariant byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }
            return METADATA_LOOKUP[metadata];
        }

        static {
            for (EnumVariant variant : values()) {
                METADATA_LOOKUP[variant.metadata] = variant;
            }
        }
    }

    public RoadSeparateDiagonalSlab() {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(DollanCustomized.ROAD_TAB);
        this.setHardness(1.2F);
        this.setResistance(10F);
        this.setTickRandomly(true);
        this.setLightOpacity(1);
    }

    // 核心修改：先解析 FACING (低位)，再解析 VARIANT (高位)
    // Meta布局: [Variant (2bit)][Facing (2bit)]
    @Override
    public IBlockState getStateFromMeta(int meta) {
        // 获取低2位作为方向索引
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        // 获取高位作为变体索引 (右移2位)
        EnumVariant variant = EnumVariant.byMetadata(meta >> 2);
        return this.getDefaultState().withProperty(FACING, facing).withProperty(VARIANT, variant);
    }

    // 核心修改：FACING 存入低位，VARIANT 存入高位
    @Override
    public int getMetaFromState(IBlockState state) {
        int facingIndex = state.getValue(FACING).getHorizontalIndex();
        int variantIndex = state.getValue(VARIANT).getMetadata() << 2;
        return variantIndex | facingIndex;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.CENTER : BlockFaceShape.MIDDLE_POLE;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_BOTTOM_HALF;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase player) {
        // 调用父类方法获取基础状态（这通常会自动处理基本的属性，但对于自定义属性需要手动处理）
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player);

        // 1. 设置朝向（根据玩家面向的反方向）
        state = state.withProperty(FACING, player.getHorizontalFacing().getOpposite());

        // 2. 【关键补充】设置变体（根据物品的 metadata）
        // 必须要加上这一行，否则放置时变体会丢失，变回默认值
        state = state.withProperty(VARIANT, EnumVariant.byMetadata(meta));

        return state;
    }


    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, FACING});
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumVariant variant : EnumVariant.values()) {
            items.add(new ItemStack(this, 1, variant.getMetadata()));
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this, 1, state.getValue(VARIANT).getMetadata());
    }

    // 新增：确保中键拾取时只获取 Variant 信息，忽略旋转
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, state.getValue(VARIANT).getMetadata());
    }

        @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block block;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(block = new RoadSeparateDiagonalSlab().setRegistryName("separate_diagonal_slab").setUnlocalizedName("separate_diagonal_slab"));
        }

            @SubscribeEvent
            public static void registerItem(RegistryEvent.Register<Item> event) {
                event.getRegistry().register(
                        new ItemMultiTexture(block, block, new ItemMultiTexture.Mapper() {
                            @Override
                            public String apply(ItemStack stack) {
                                // 根据 metadata 返回对应的名称，用于生成 UnlocalizedName
                                // 这里的 name 会拼接到 tile.前缀之后
                                // 例如：tile.separate_diagonal_white_0.name
                                switch (stack.getMetadata()) {
                                    case 0: return "white_0";
                                    case 1: return "white_1";
                                    case 2: return "yellow_0";
                                    case 3: return "yellow_1";
                                    default: return "white_0";
                                }
                            }
                        }).setRegistryName("separate_diagonal_slab")
                );
            }


        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            // 注意：由于Item的meta只保存了Variant信息（0-3），这里的注册逻辑不需要改变
            // 只有方块放置后的旋转状态会保存在方块本身的元数据中
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:white_separate_diagonal_0_slab", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 1, new ModelResourceLocation("dollancustomized:white_separate_diagonal_1_slab", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 2, new ModelResourceLocation("dollancustomized:yellow_separate_diagonal_0_slab", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 3, new ModelResourceLocation("dollancustomized:yellow_separate_diagonal_1_slab", "inventory"));
        }
    }
}
