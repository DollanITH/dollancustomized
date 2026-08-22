package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.client.gui.GuiHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHeightLimitSignCustom extends BlockRoadSign {

    public BlockHeightLimitSignCustom() {
        super("road_sign_height_limit_custom");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
                                    EntityPlayer player, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        // 【关键】必须确保仅在客户端（world.isRemote）打开 GUI！
        if (world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityHeightLimitSignCustom) {
                // 在客户端打开 GUI，Minecraft 会自动正确处理鼠标指针的释放
                Minecraft.getMinecraft().displayGuiScreen(new GuiHeightLimitSignCustom((TileEntityHeightLimitSignCustom) te));
            }
        }
        // 返回 true 表示交互成功
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityHeightLimitSignCustom();
    }

}