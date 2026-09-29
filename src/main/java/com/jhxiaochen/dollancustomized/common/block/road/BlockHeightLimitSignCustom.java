package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.client.gui.GuiHandler;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.block.state.IBlockState;
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
        // 服务端通过 openGui 打开 GUI：方法体不引用任何客户端类（GuiHeightLimitSignCustom/GuiScreen），
        // 避免服务端加载本方块类时验证方法字节码而触发 NoClassDefFoundError: GuiScreen。
        // 客户端由 GuiHandler.getClientGuiElement（IGuiHandler 接口方法自带 @SideOnly，已被 stub）创建 GUI。
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityHeightLimitSignCustom) {
                player.openGui(DollanCustomized.instance, GuiHandler.HEIGHT_LIMIT_CUSTOM_GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
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
