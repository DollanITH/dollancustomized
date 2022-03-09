package com.jhxiaochen.dollancustomized.common.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MergeOtherLineSlab extends Block {
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.<EnumVariant>create("variant", EnumVariant.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public enum EnumVariant implements IStringSerializable{
        MERGE_WHITE(0, "mergewhite"),
        MERGE_YELLOW(1, "mergeyellow"),
        SEPARATE_WHITE(2,"separatewhite"),
        SEPARATE_YELLOW(3,"separateyellow");

        private static final EnumVariant[] METADATA_LOOKUP = new EnumVariant[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private EnumVariant(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private EnumVariant(int metadataIn, String nameIn, String unlocalizedNameIn) {
            this.metadata = metadataIn;
            this.name = nameIn;
            this.unlocalizedName = unlocalizedNameIn;
        }

        public int getMetadata() {
            return this.metadata;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public String toString() {
            return this.name;
        }

        public static EnumVariant byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (MergeOtherLineSlab.EnumVariant enumvariant : values()) {
                METADATA_LOOKUP[enumvariant.getMetadata()] = enumvariant;
            }
        }
    }

public MergeOtherLineSlab(String name){
    super(Material.ROCK);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    this.setRegistryName(name);
    this.setUnlocalizedName(name);
    this.setCreativeTab(DollanCustomized.ROAD_TAB);
    this.setHardness(1.2F);
    this.setResistance(10F);
    this.setLightOpacity(1);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 0:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(VARIANT, EnumVariant.MERGE_WHITE);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(VARIANT, EnumVariant.MERGE_YELLOW);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(VARIANT, EnumVariant.SEPARATE_WHITE);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(VARIANT, EnumVariant.SEPARATE_YELLOW);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(VARIANT, EnumVariant.MERGE_WHITE);
                break;
            case 5:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(VARIANT, EnumVariant.MERGE_YELLOW);
                break;
            case 6:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(VARIANT, EnumVariant.SEPARATE_WHITE);
                break;
            case 7:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(VARIANT, EnumVariant.SEPARATE_YELLOW);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(VARIANT, EnumVariant.MERGE_WHITE);
                break;
            case 9:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(VARIANT, EnumVariant.MERGE_YELLOW);
                break;
            case 10:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(VARIANT, EnumVariant.SEPARATE_WHITE);
                break;
            case 11:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(VARIANT, EnumVariant.SEPARATE_YELLOW);
                break;
            case 12:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(VARIANT, EnumVariant.MERGE_WHITE);
                break;
            case 13:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(VARIANT, EnumVariant.MERGE_YELLOW);
                break;
            case 14:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(VARIANT, EnumVariant.SEPARATE_WHITE);
                break;
            case 15:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(VARIANT, EnumVariant.SEPARATE_YELLOW);
                break;
            default:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(VARIANT, EnumVariant.MERGE_WHITE);
        }

        return iblockstate;

    }
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {

        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.CENTER : BlockFaceShape.MIDDLE_POLE;

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
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
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        switch ((EnumFacing) state.getValue(FACING)) {
            case SOUTH:
                switch ((EnumVariant) state.getValue(VARIANT)){
                    case MERGE_WHITE:
                      return   i = i | 0;
                    case MERGE_YELLOW:
                      return   i = i | 1;
                    case SEPARATE_WHITE:
                      return   i = i | 2;
                    case SEPARATE_YELLOW:
                      return   i = i | 3;
                }
                break;
            case WEST:
                switch ((EnumVariant) state.getValue(VARIANT)){
                    case MERGE_WHITE:
                      return   i = i | 4;
                    case MERGE_YELLOW:
                      return   i = i | 5;
                    case SEPARATE_WHITE:
                      return   i = i | 6;
                    case SEPARATE_YELLOW:
                      return   i = i | 7;
                }
                break;
            case NORTH:
                switch ((EnumVariant) state.getValue(VARIANT)){
                    case MERGE_WHITE:
                      return   i = i | 8;
                    case MERGE_YELLOW:
                      return   i = i | 9;
                    case SEPARATE_WHITE:
                      return   i = i | 10;
                    case SEPARATE_YELLOW:
                      return   i = i | 11;
                }
                break;
            case EAST:
                switch ((EnumVariant) state.getValue(VARIANT)) {
                    case MERGE_WHITE:
                     return    i = i | 12;
                    case MERGE_YELLOW:
                     return    i = i | 13;
                    case SEPARATE_WHITE:
                     return    i = i | 14;
                    case SEPARATE_YELLOW:
                     return   i = i | 15;
                }
                break;
        }
        return i;
    }
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase player) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player);
        state = state.withProperty(FACING, player.getHorizontalFacing().getOpposite());
        return state;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, FACING});
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (EnumVariant enumvariant : EnumVariant.values())
        {
            items.add(new ItemStack(this, 1, enumvariant.getMetadata()));
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((EnumVariant)state.getValue(VARIANT)).getMetadata());
    }
    }

