package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockMetroFence extends Block {
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D),
            new AxisAlignedBB(0.4D, 0.0D, 0.3D, 0.6D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.3D, 0.6D, 1.0D, 0.6D),
            new AxisAlignedBB(0.0D, 0.0D, 0.3D, 0.6D, 1.0D, 1.0D),
            new AxisAlignedBB(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 0.6D),
            new AxisAlignedBB(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D, 1.0D, 0.6D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D, 1D, 1.0D),
            new AxisAlignedBB(0.4D, 0.0D, 0.4D, 1.0D, 1D, 0.6D),
            new AxisAlignedBB(0.4D, 0.0D, 0.4D, 1.0D, 1D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.4D, 1.0D, 1D, 0.6D),
            new AxisAlignedBB(0.0D, 0.0D, 0.4D, 1.0D, 1D, 1.0D),
            new AxisAlignedBB(0.4D, 0.0D, 0.0D, 1.0D, 1D, 0.6D),
            new AxisAlignedBB(0.4D, 0.0D, 0.0D, 1.0D, 1D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 0.6D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 1.0D)};
    public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.625D, 0.625D, 1.0D, 1.0D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1.0D, 0.625D);
    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.625D, 1.0D, 0.375D);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.7D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);


    public static final PropertyEnum<BlockMetroFence.FenceType> TYPE = PropertyEnum.<BlockMetroFence.FenceType>create("type", BlockMetroFence.FenceType.class);
    public static enum FenceType implements IStringSerializable {
        METRO(0, "metro","metro"),
        ROAD_CENTER(1, "roadcenter","road_center"),
        ROAD(2,"road","road");

        private static final BlockMetroFence.FenceType[] METADATA_LOOKUP = new BlockMetroFence.FenceType[values().length];
        private final int metadata;
        private final String name;
        private final String unlocalizedName;

        private FenceType(int metadataIn, String nameIn) {
            this(metadataIn, nameIn, nameIn);
        }

        private FenceType(int metadataIn, String nameIn, String unlocalizedNameIn) {
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

        public static BlockMetroFence.FenceType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
                metadata = 0;
            }

            return METADATA_LOOKUP[metadata];
        }

        public String getName() {
            return this.name;
        }

        static {
            for (BlockMetroFence.FenceType BlockMetroFence$FenceType : values()) {
                METADATA_LOOKUP[BlockMetroFence$FenceType.getMetadata()] = BlockMetroFence$FenceType;
            }
        }
    }
    public BlockMetroFence(String name) {
        super(Material.IRON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.Blocks);
        this.setHardness(3.0F);
        this.setSoundType(SoundType.STONE);
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + FenceType.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        if (!isActualState)
        {
            state = state.getActualState(worldIn, pos);
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);

        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        }

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = this.getActualState(state, source, pos);
        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        {
            items.add(new ItemStack(this, 1, FenceType.METRO.getMetadata()));
            items.add(new ItemStack(this, 1, FenceType.ROAD_CENTER.getMetadata()));
            items.add(new ItemStack(this, 1, FenceType.ROAD.getMetadata()));
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, ((FenceType)state.getValue(TYPE)).getMetadata());
    }

    private static int getBoundingBoxIdx(IBlockState state)
    {
        int i = 0;

        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
        Block block = iblockstate.getBlock();
        boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE && (iblockstate.getMaterial() == this.blockMaterial || block instanceof BlockFenceGate);
        return !isExcepBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
    }

    protected static boolean isExcepBlockForAttachWithPiston(Block p_194142_0_)
    {
        return Block.isExceptBlockForAttachWithPiston(p_194142_0_) || p_194142_0_ == Blocks.BARRIER || p_194142_0_ == Blocks.MELON_BLOCK || p_194142_0_ == Blocks.PUMPKIN || p_194142_0_ == Blocks.LIT_PUMPKIN;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            return ItemLead.attachToFence(playerIn, worldIn, pos);
        }
        else
        {
            ItemStack itemstack = playerIn.getHeldItem(hand);
            return itemstack.getItem() == Items.LEAD || itemstack.isEmpty();
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 1:
                iblockstate = iblockstate.withProperty(TYPE, FenceType.ROAD_CENTER);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(TYPE, FenceType.ROAD);
                break;
            case 0:
            default:
                iblockstate = iblockstate.withProperty(TYPE, FenceType.METRO);
        }

        return iblockstate;
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        switch ((FenceType)state.getValue(TYPE))
        {
            case METRO:
                i = i | 0;
                break;
            case ROAD_CENTER:
                i = i | 1;
                break;
            case ROAD:
                i = i | 2;
                break;
            default:
                i = i | 0;
        }

        return i;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int meta = state.getValue(TYPE).getMetadata();
        switch (meta) {
            case 0:
               return state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
                    .withProperty(EAST, canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
                    .withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
                    .withProperty(WEST, canFenceConnectTo(worldIn, pos, EnumFacing.WEST));
            case 1:
               return state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(EAST, canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
                        .withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(WEST, canFenceConnectTo(worldIn, pos, EnumFacing.WEST));
            case 2:
                state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(EAST, canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
                        .withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(WEST, canFenceConnectTo(worldIn, pos, EnumFacing.WEST));
            case 3:
                state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(EAST, canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
                        .withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(WEST, canFenceConnectTo(worldIn, pos, EnumFacing.WEST));
            default:
            return state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
                    .withProperty(EAST, canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
                    .withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
                    .withProperty(WEST, canFenceConnectTo(worldIn, pos, EnumFacing.WEST));
        }
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {TYPE, NORTH, EAST, WEST, SOUTH});
    }

    /* ======================================== FORGE START ======================================== */

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return canConnectTo(world, pos.offset(facing), facing.getOpposite());
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
    }

    /* ======================================== FORGE END ======================================== */

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
    }
}