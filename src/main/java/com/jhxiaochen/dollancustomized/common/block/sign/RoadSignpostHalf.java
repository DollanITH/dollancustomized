package com.jhxiaochen.dollancustomized.common.block.sign;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.sign.boundingbox.RoadSignpostHalfBoundingBox;
import com.jhxiaochen.dollancustomized.common.block.sign.boundingbox.RoadSignpostPoleBoundingBox;
import com.jhxiaochen.dollancustomized.common.block.sign.boundingbox.RoadSignpostPoleHorizontalBoundingBox;
import com.jhxiaochen.dollancustomized.init.blocks.DollanBlocks;
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

public class RoadSignpostHalf extends BlockHorizontal {
    public static final PropertyEnum<EnumSignpostType> TYPE = PropertyEnum.create("type", EnumSignpostType.class);

    public RoadSignpostHalf() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(TYPE, EnumSignpostType.NONE));
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.ROAD_Signs);
        this.setLightOpacity(0);
        this.setRegistryName(Reference.MOD_ID, "road_signpost_half");
        this.setUnlocalizedName("road_signpost_half");
    }

    enum EnumSignpostType implements IStringSerializable
    {
        NONE("none"),
        ON_POLE_HORIZONTAL("on_pole_horizontal"),
        ON_POLE("on_pole");
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
        return RoadSignpostHalfBoundingBox.getDisplayBoundingBox(facing);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        EnumFacing facing = state.getValue(FACING);

        AxisAlignedBB[] collisionboxes = RoadSignpostPoleHorizontalBoundingBox.getAllCollisionBoxes(facing);
        AxisAlignedBB collisionBoxes1 = RoadSignpostPoleBoundingBox.getDisplayBoundingBox(facing);
        AxisAlignedBB box1 = RoadSignpostHalfBoundingBox.getDisplayBoundingBox(facing);
        if (state.getValue(TYPE) == EnumSignpostType.ON_POLE_HORIZONTAL) {
            for (AxisAlignedBB box : collisionboxes) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
            }
        } else if (state.getValue(TYPE) == EnumSignpostType.ON_POLE) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, collisionBoxes1);
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, box1);
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
        EnumSignpostType type = EnumSignpostType.NONE;
        if ((meta & 4) != 0) {
            type = EnumSignpostType.ON_POLE_HORIZONTAL;
        } else if ((meta & 8) != 0) {
            type = EnumSignpostType.ON_POLE;
        }
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(TYPE, type);
    }

    public int getMetaFromState(IBlockState state) {
        int v = 0x0;
        if (state.getValue(TYPE) == EnumSignpostType.ON_POLE_HORIZONTAL) {
            v |= 4;
        } else if (state.getValue(TYPE) == EnumSignpostType.ON_POLE) {
            v |= 8;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = DollanBlocks.road_signpost_pole;
        Block block2 = DollanBlocks.road_signpost_pole_horizontal;
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem.getItem() == Item.getItemFromBlock(block)) {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RoadSignpostHalf.TYPE, RoadSignpostHalf.EnumSignpostType.ON_POLE),2);
                worldIn.playSound(null, pos, SoundType.METAL.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }

        if (heldItem.getItem() == Item.getItemFromBlock(block2)) {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RoadSignpostHalf.TYPE, RoadSignpostHalf.EnumSignpostType.ON_POLE_HORIZONTAL),2);
                worldIn.playSound(null, pos, SoundType.METAL.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
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
        } else if (state.getValue(TYPE) == EnumSignpostType.ON_POLE) {
            worldIn.setBlockState(pos, DollanBlocks.road_signpost_pole.getDefaultState().withProperty(FACING, state.getValue(FACING)), 11);
            if (addedToInventory) {
                player.inventoryContainer.detectAndSendChanges();
            } else {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}