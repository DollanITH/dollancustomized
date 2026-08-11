package com.jhxiaochen.dollancustomized.common.block.sign;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RoadSignpost extends BlockHorizontal {

    
    public RoadSignpost() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.ROAD_Signs);
        this.setLightOpacity(0);
        this.setRegistryName(Reference.MOD_ID , "road_signpost");
        this.setUnlocalizedName("road_signpost");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return RoadSignpostBoundingBox.getDisplayBoundingBox(facing);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
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
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        return state;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        if (side == EnumFacing.UP) {
            return false;
        }
        return super.canPlaceBlockOnSide(world, pos, side);
    }

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }

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
    }
}