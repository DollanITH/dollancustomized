package com.jhxiaochen.dollancustomized.common.block.sign;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.sign.boundingbox.RoadSignpostBoundingBox;
import com.jhxiaochen.dollancustomized.common.block.sign.boundingbox.RoadSignpostPoleHorizontalBoundingBox;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import java.util.Objects;

public class RoadSignpost extends BlockHorizontal {
    public static final PropertyEnum<EnumSignpostType> TYPE = PropertyEnum.create("type", EnumSignpostType.class);
    
    public RoadSignpost() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(TYPE, EnumSignpostType.NONE));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.ROAD_Signs);
        this.setLightOpacity(0);
        this.setRegistryName(Reference.MOD_ID , "road_signpost");
        this.setTranslationKey("road_signpost");
    }

    enum EnumSignpostType implements IStringSerializable
    {

        NONE("none"),
        ON_POLE_HORIZONTAL("on_pole_horizontal"),;
        private final String name;
        EnumSignpostType(String nameIn) {
            name = nameIn;
        }

        @Override
        public String getName() {
            return name;
        }

        public String toString() {
            return this.name;
        }

    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return RoadSignpostBoundingBox.getDisplayBoundingBox(facing);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);

        AxisAlignedBB[] collisionBoxes = RoadSignpostPoleHorizontalBoundingBox.getAllCollisionBoxes(facing);
        AxisAlignedBB box1 = RoadSignpostBoundingBox.getDisplayBoundingBox(facing);
        if (state.getValue(TYPE) != EnumSignpostType.NONE) {
            for (AxisAlignedBB box : collisionBoxes) {
                 addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
            }
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, box1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
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
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3)).withProperty(TYPE, (meta & 4) != 0 ? EnumSignpostType.ON_POLE_HORIZONTAL : EnumSignpostType.NONE);
    }

    public int getMetaFromState(IBlockState state) {
        int v = 0x0;
        if (state.getValue(TYPE) ==  EnumSignpostType.ON_POLE_HORIZONTAL) {
            v |= 4;
        }
        return (state.getValue(FACING)).getHorizontalIndex() | v;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, EnumSignpostType.NONE);
        return state;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        EntityPlayer player = worldIn.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 6.0D, false);
        boolean addedToInventory = Objects.requireNonNull(player).inventory.addItemStackToInventory(new ItemStack(this));
            if (state.getValue(TYPE) == EnumSignpostType.ON_POLE_HORIZONTAL) {
                worldIn.setBlockState(pos, DollanBlocks.road_signpost_pole_horizontal.getDefaultState().withProperty(FACING, state.getValue(FACING)), 11);
                if (addedToInventory) {
                    player.inventoryContainer.detectAndSendChanges();
                } else {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        Block block1 = DollanBlocks.road_signpost_pole_horizontal;
        if (heldItem.getItem() == Item.getItemFromBlock(block1)) {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RoadSignpost.TYPE, RoadSignpost.EnumSignpostType.ON_POLE_HORIZONTAL),2);
                worldIn.playSound(null, pos, SoundType.METAL.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }
/*
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block road_signpost = null;
        public static Block road_signpost_half = null;
        public static Block road_signpost_pole = null;
        public static Block road_signpost_pole_double = null;
        public static Block road_signpost_pole_horizontal = null;
        public static Block road_signpost_pole_left = null;
        public static Block road_signpost_pole_right = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(road_signpost = new RoadSignpost());
            event.getRegistry().register(road_signpost_half = new RoadSignpostHalf());
            event.getRegistry().register(road_signpost_pole = new RoadSignpostPole());
            event.getRegistry().register(road_signpost_pole_double = new RoadSignpostPoleDouble());
            event.getRegistry().register(road_signpost_pole_horizontal = new RoadSignpostPoleHorizontal());
            event.getRegistry().register(road_signpost_pole_left = new RoadSignpostPoleLeft());
            event.getRegistry().register(road_signpost_pole_right = new RoadSignpostPoleRight());
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(road_signpost)
                    .setRegistryName(road_signpost.getRegistryName()));
            event.getRegistry().register(new ItemBlock(road_signpost_half)
                    .setRegistryName(road_signpost_half.getRegistryName()));
            event.getRegistry().register(new ItemBlock(road_signpost_pole)
                    .setRegistryName(road_signpost_pole.getRegistryName()));
            event.getRegistry().register(new ItemBlock(road_signpost_pole_double)
                    .setRegistryName(road_signpost_pole_double.getRegistryName()));
            event.getRegistry().register(new ItemBlock(road_signpost_pole_horizontal)
                    .setRegistryName(road_signpost_pole_horizontal.getRegistryName()));
            event.getRegistry().register(new ItemBlock(road_signpost_pole_left)
                    .setRegistryName(road_signpost_pole_left.getRegistryName()));
            event.getRegistry().register(new ItemBlock(road_signpost_pole_right)
                    .setRegistryName(road_signpost_pole_right.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            DollanBlocks.SimpleBlock(road_signpost);
            DollanBlocks.SimpleBlock(road_signpost_half);
            DollanBlocks.SimpleBlock(road_signpost_pole);
            DollanBlocks.SimpleBlock(road_signpost_pole_double);
            DollanBlocks.SimpleBlock(road_signpost_pole_horizontal);
            DollanBlocks.SimpleBlock(road_signpost_pole_left);
            DollanBlocks.SimpleBlock(road_signpost_pole_right);
        }
    }*/
}