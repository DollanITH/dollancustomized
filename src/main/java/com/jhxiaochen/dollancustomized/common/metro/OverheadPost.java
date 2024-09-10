package com.jhxiaochen.dollancustomized.common.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class OverheadPost extends Block {
    private static final AxisAlignedBB BOX_1 = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 1, 0.625);
    private static final AxisAlignedBB BOX_2 = new AxisAlignedBB(0.625, 0, 0.40625, 0.6875, 1, 0.59375);
    private static final AxisAlignedBB BOX_3 = new AxisAlignedBB(0.3125, 0, 0.40625, 0.375, 1, 0.59375);
    private static final AxisAlignedBB BOX_4 = new AxisAlignedBB(0.40625, 0, 0.625, 0.59375, 1, 0.6875);
    private static final AxisAlignedBB BOX_5 = new AxisAlignedBB(0.40625, 0, 0.3125, 0.59375, 1, 0.375);
    public OverheadPost(String name) {
        super(Material.IRON, MapColor.IRON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(4.0F);
        this.setResistance(6.0F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.3125,0,0.3125,0.6875,1,0.6875);
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_3);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_4);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOX_5);
    }
}

