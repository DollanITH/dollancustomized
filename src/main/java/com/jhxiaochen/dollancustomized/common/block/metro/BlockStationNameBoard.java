package com.jhxiaochen.dollancustomized.common.block.metro;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.client.gui.GuiHandler;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStationNameBoard extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    // 贴在墙上的碰撞箱 - 窄而薄，增大到原来的三倍
    protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.1D, 0.1D, 0.95D, 0.9D, 0.9D, 1.0D);
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.1D, 0.1D, 0.0D, 0.9D, 0.9D, 0.05D);
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.95D, 0.1D, 0.1D, 1.0D, 0.9D, 0.9D);
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.1D, 0.1D, 0.05D, 0.9D, 0.9D);

    public BlockStationNameBoard() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.METRO_TAB);
        this.setHardness(2.0F);
        this.setResistance(10F);
        this.setLightOpacity(0);
        this.setTickRandomly(true);
        this.hasTileEntity = true; // 声明拥有 TileEntity
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityStationNameBoard();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // 服务端通过 openGui 打开 GUI：方法体不引用任何客户端类（GuiStationNameBoard/GuiScreen），
        // 避免服务端加载本方块类时验证方法字节码而触发 NoClassDefFoundError: GuiScreen。
        // 客户端由 GuiHandler.getClientGuiElement（IGuiHandler 接口方法自带 @SideOnly，已被 stub）创建 GUI。
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityStationNameBoard) {
                playerIn.openGui(DollanCustomized.instance, GuiHandler.STATION_NAME_BOARD_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        // 返回 true 表示交互成功
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityStationNameBoard();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        switch (facing) {
            case NORTH: return AABB_NORTH;
            case SOUTH: return AABB_SOUTH;
            case WEST: return AABB_WEST;
            case EAST: return AABB_EAST;
            default: return AABB_NORTH;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB; // 无碰撞箱
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
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
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        // 放置时自动朝向玩家
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        // 对于贴墙方块，只渲染朝向玩家的面
        if (state.getBlock() == this) {
            EnumFacing facing = state.getValue(FACING);
            // 只渲染正面（朝向玩家的面）和两个侧面
            return side == facing || side == facing.rotateY() || side == facing.rotateYCCW();
        }
        return true; // 其他方块正常渲染
    }


    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.checkForDrop(worldIn,pos,state);        // 确保TileEntity存在
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
            worldIn.setTileEntity(pos, this.createNewTileEntity(worldIn, 0));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        // 检查是否能放置在墙上
        for (EnumFacing facing : EnumFacing.values()) {
            if (isSideSolid(worldIn.getBlockState(pos.offset(facing.getOpposite())), worldIn, pos.offset(facing.getOpposite()), facing.getOpposite()) || isFullCube(worldIn.getBlockState(pos.offset(facing.getOpposite())))) {
                return true;
            }
            if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() instanceof BlockGlass) {
                return true;
            }
            if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() instanceof BlockStainedGlass) {
                return true;
            }
        }
        return false;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() instanceof BlockGlass) {
            return true;
        }
        if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() instanceof BlockStainedGlass) {
            return true;
        }
        return iblockstate.isSideSolid(worldIn, blockpos, facing.getOpposite());
    }

    // 优化：在方块改变或销毁时，清理 TileEntity，防止内存泄漏
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        // 检查是否还贴在墙上
        EnumFacing facing = state.getValue(FACING);
        if (!canPlaceAt(worldIn, pos, facing)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            worldIn.notifyBlockUpdate(pos, state, worldIn.getBlockState(pos), 3);
        }
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING))) {
            return true;
        } else {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
            return false;
        }
    }

    // 优化：拥有 TileEntity 的方块必须重写此方法，否则比较器等机制会崩溃
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

}
