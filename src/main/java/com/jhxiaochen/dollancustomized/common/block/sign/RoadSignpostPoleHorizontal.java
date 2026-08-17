package com.jhxiaochen.dollancustomized.common.block.sign;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.sign.boundingbox.RoadSignpostPoleHorizontalBoundingBox;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

public class RoadSignpostPoleHorizontal extends BlockHorizontal {

    
    public RoadSignpostPoleHorizontal() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.ROAD_Signs);
        this.setLightOpacity(0);
        this.setRegistryName(Reference.MOD_ID, "road_signpost_pole_horizontal");
        this.setUnlocalizedName("road_signpost_pole_horizontal");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return RoadSignpostPoleHorizontalBoundingBox.getDisplayBoundingBox(facing);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);

        // 添加所有碰撞箱
        AxisAlignedBB[] collisionBoxes = RoadSignpostPoleHorizontalBoundingBox.getAllCollisionBoxes(facing);
        for (AxisAlignedBB box : collisionBoxes) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }

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
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        Block block1 = DollanBlocks.road_signpost;
        Block block2 = DollanBlocks.road_signpost_half;
        if (heldItem.getItem() == Item.getItemFromBlock(block1)) {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                worldIn.setBlockState(pos, block1.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RoadSignpost.TYPE, RoadSignpost.EnumSignpostType.ON_POLE_HORIZONTAL),2);
                worldIn.playSound(null, pos, SoundType.METAL.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        if (heldItem.getItem() == Item.getItemFromBlock(block2)) {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                worldIn.setBlockState(pos, block2.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RoadSignpostHalf.TYPE, RoadSignpostHalf.EnumSignpostType.ON_POLE_HORIZONTAL),2);
                worldIn.playSound(null, pos, SoundType.METAL.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        IBlockState neighborBlock = world.getBlockState(pos.offset(facing.getOpposite()));

        if (neighborBlock.getBlock() instanceof RoadSignpostPoleLeft ||
            neighborBlock.getBlock() instanceof RoadSignpostPoleRight ||
            neighborBlock.getBlock() instanceof RoadSignpost ||
            neighborBlock.getBlock() == this) {
            EnumFacing neighborFacing = neighborBlock.getValue(FACING);
            return state.withProperty(FACING, neighborFacing);
        }
        return state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
}