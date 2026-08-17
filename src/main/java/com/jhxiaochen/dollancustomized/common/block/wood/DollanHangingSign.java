package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.access.ICardinal;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityDollanHangingSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import suike.suikecherry.tileentity.HangingSignTileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class DollanHangingSign extends BlockContainer implements ICardinal {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    // 边界框定义
    protected static final AxisAlignedBB X_IN_WALL_BOX = new AxisAlignedBB(0.0, 0.875D, 0.375D, 1.0, 1.0, 0.625D);
    protected static final AxisAlignedBB Z_IN_WALL_BOX = new AxisAlignedBB(0.375D, 0.875D, 0.0, 0.625D, 1.0, 1.0);
    protected static final AxisAlignedBB X_BOX = new AxisAlignedBB(0.0625D, 0.0, 0.4375D, 0.9375D, 0.625D, 0.5625D);
    protected static final AxisAlignedBB Z_BOX = new AxisAlignedBB(0.4375D, 0.0, 0.0625D, 0.5625D, 0.625D, 0.9375D);

    private final boolean inWall;
    private final Item    item;
    public DollanHangingSign(String name, Item item) {
        super(Material.WOOD);
        this.item = item;
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(1.0F);
        this.setResistance(1.0F);
        this.setHarvestLevel("axe", 0);
        this.setLightOpacity(0);
        this.setUnlocalizedName(name);
        this.setRegistryName(Reference.MOD_ID, name);
        this.inWall = name.endsWith("wall");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public boolean isInWall() {
        return this.inWall;
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
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
    public boolean canRenderInLayer(IBlockState state, net.minecraft.util.BlockRenderLayer layer) {
        return layer == net.minecraft.util.BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch ((EnumFacing) state.getValue(FACING)) {
            case NORTH:
            default:
                return X_IN_WALL_BOX.union(X_BOX);
            case SOUTH:
                return X_IN_WALL_BOX.union(X_BOX);
            case WEST:
                return Z_IN_WALL_BOX.union(Z_BOX);
            case EAST:
                return Z_IN_WALL_BOX.union(Z_BOX);
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
        switch ((EnumFacing) state.getValue(FACING)) {
            case NORTH:
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_IN_WALL_BOX.union(X_BOX));
                break;
            case SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, X_IN_WALL_BOX.union(X_BOX));
                break;
            case WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_IN_WALL_BOX.union(Z_BOX));
                break;
            case EAST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, Z_IN_WALL_BOX.union(Z_BOX));
                break;
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        EnumFacing facing = state.getValue(FACING);
        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
            if (face == EnumFacing.EAST || face == EnumFacing.WEST) {
                return BlockFaceShape.SOLID;
            }
        } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
            if (face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
                return BlockFaceShape.SOLID;
            }
        }
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        EnumFacing facing = state.getValue(FACING);
        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
            if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH) {
                return true;
            }
        } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
            if (side == EnumFacing.EAST || side == EnumFacing.WEST) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityDollanHangingSign();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        if (GuiScreen.isCtrlKeyDown()) return new ItemStack(item);

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityDollanHangingSign) {
            TileEntityDollanHangingSign tile = (TileEntityDollanHangingSign) tileentity;
            int hasItemSide = tile.getDisplayedSides();
            ItemStack itemStack = new ItemStack(item);

            if (hasItemSide == 0 || hasItemSide == 1) {
                itemStack = tile.getDisplayItem(hasItemSide);
            } else if (hasItemSide == 2) {
                itemStack = tile.getDisplayItem(tile.getAndToggleLastPickedSide());
            }
            return itemStack;
        }
        return new ItemStack(item);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(item));
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntityDollanHangingSign) {
            TileEntityDollanHangingSign tile = (TileEntityDollanHangingSign) tileentity;
            drops.add(tile.getItemHandler().getStackInSlot(0));
            drops.add(tile.getItemHandler().getStackInSlot(1));
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (!player.capabilities.isCreativeMode) {
            Block.spawnAsEntity(world, pos, new ItemStack(item));
        }
        if (te instanceof TileEntityDollanHangingSign) {
            TileEntityDollanHangingSign tile = (TileEntityDollanHangingSign) te;
            Block.spawnAsEntity(world, pos, tile.getItemHandler().getStackInSlot(0));
            Block.spawnAsEntity(world, pos, tile.getItemHandler().getStackInSlot(1));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (!this.inWall) {
            IBlockState upBlockState = world.getBlockState(pos.up());
            Block upBlock = upBlockState.getBlock();
            if (upBlock instanceof DollanHangingSign) {
                return;
            }
            if (upBlock.getRegistryName().toString().contains("hanging")) {
                int meta = upBlock.getMetaFromState(upBlockState);
                if (meta != 0 && meta != 4 && meta != 8 && meta != 12) {
                    HangingSignTileEntity tile = (HangingSignTileEntity) world.getTileEntity(pos);
                    if (tile != null) {
                        Block.spawnAsEntity(world, pos, tile.getItemHandler( ).getStackInSlot(0));
                        Block.spawnAsEntity(world, pos, tile.getItemHandler( ).getStackInSlot(1));
                    }
                    world.destroyBlock(pos, true);
                }
            }
            else if (!upBlockState.isSideSolid(world, pos.up(), EnumFacing.DOWN)) {

                HangingSignTileEntity tile = (HangingSignTileEntity) world.getTileEntity(pos);
                if (tile != null) {
                    Block.spawnAsEntity(world, pos, tile.getItemHandler( ).getStackInSlot(0));
                    Block.spawnAsEntity(world, pos, tile.getItemHandler( ).getStackInSlot(1));
                }
                world.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                   EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // 点击 上/下方
        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) return false;

        // 为蹲下
        if (playerIn.isSneaking()) return false;

        // 获取点击面
        int slot = this.getSlotFromFacings(state.getValue(FACING), facing);
        if (slot == -1) return false;

        // 获取存储物品的 TileEntity
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityDollanHangingSign) {
            TileEntityDollanHangingSign tile = (TileEntityDollanHangingSign) tileentity;

            // 检查是否涂蜡
            if (tile.isWaxed(slot)) {
                playerIn.sendMessage(new net.minecraft.util.text.TextComponentString("这一面已经被涂蜡，无法编辑"));
                return false;
            }

            // 如果这一面有物品，移除展示的物品
            if (tile.hasDisplayItems(slot)) {
                return tile.removeDisplayItems(worldIn, pos, state, playerIn, slot);
            }

            // 获取手持物
            ItemStack heldItem = playerIn.getHeldItem(hand);

            // 空手点击
            if (heldItem.isEmpty()) {
                // 编辑文字
                return tile.editText(slot, playerIn);
            }
            // 这一面什么都没有
            else if (!tile.hasAny(slot)) {
                // 展示物品
                return tile.displayItems(worldIn, pos, state, playerIn, heldItem, slot);
            }

            // 尝试打蜡
            if (tile.tryWaxed(slot, heldItem)) {
                // 减少物品栏物品
                if (!playerIn.capabilities.isCreativeMode) heldItem.shrink(1);
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDollanHangingSign();
    }

}