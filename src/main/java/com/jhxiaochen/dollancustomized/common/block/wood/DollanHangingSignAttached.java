package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.access.IAxis;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanHangingSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

import javax.annotation.Nullable;
import java.util.List;

public class DollanHangingSignAttached extends BlockContainer implements IAxis {

    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

    protected static final AxisAlignedBB BOX = new AxisAlignedBB(0.1875D, 0.0, 0.1875D, 0.8125D, 1.0, 0.8125D);
    protected static final AxisAlignedBB X_BOX = new AxisAlignedBB(0.0625D, 0.0, 0.4375D, 0.9375D, 0.625D, 0.5625D);
    protected static final AxisAlignedBB Z_BOX = new AxisAlignedBB(0.4375D, 0.0, 0.0625D, 0.5625D, 0.625D, 0.9375D);

    private Item item;
    public DollanHangingSignAttached(String name) {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(1.0F);
        this.setResistance(1.0F);
        this.setHarvestLevel("axe", 0);
        this.setLightOpacity(0);
        this.setUnlocalizedName(name);
        this.setRegistryName(Reference.MOD_ID, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, 0));
    }


    public void setItem(Item item) {
        if (this.item == null) this.item = item;
    }


    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ROTATION);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ROTATION, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ROTATION);
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
        int axis = state.getValue(ROTATION);
        if (axis == 0 || axis == 8)
            return X_BOX;
        if (axis == 4 || axis == 12)
            return Z_BOX;
        return BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, null);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
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
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        if (world.isRemote && !GuiScreen.isCtrlKeyDown()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player == null) {
                return new ItemStack(this.getItem());
            } else {
                TileEntityDollanHangingSign tile = getTileEntity(world, pos);
                if (tile == null) {
                    return new ItemStack(this.getItem());
                } else {
                    EnumFacing[] otherFacings = this.getOtherHorizontalFacings(player.getHorizontalFacing());

                    for (EnumFacing facing : otherFacings) {
                        int slot = this.getSlotFromFacings(state.getValue(ROTATION), facing, player);
                        if (slot != -1) {
                            ItemStack displayItem = tile.getDisplayItem(slot);
                            if (displayItem != null && !displayItem.isEmpty()) {
                                return displayItem;
                            }
                        }
                    }

                    return new ItemStack(this.getItem());
                }
            }
        } else {
            return new ItemStack(this.getItem());
        }
    }

    private EnumFacing[] getOtherHorizontalFacings(EnumFacing excludedFacing) {
        EnumFacing[] allHorizontal = new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
        EnumFacing[] result = new EnumFacing[3];
        int index = 0;

        for(EnumFacing facing : allHorizontal) {
            if (facing != excludedFacing) {
                result[index++] = facing;
            }
        }

        return result;
    }

    public Item getItem() {
        return this.item;
    }

    public static TileEntityDollanHangingSign getTileEntity(IBlockAccess world, BlockPos pos) {
        if (world != null && pos != null) {
            TileEntity tile = world.getTileEntity(pos);
            return tile instanceof TileEntityDollanHangingSign ? (TileEntityDollanHangingSign) tile : null;
        } else {
            return null;
        }
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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        IBlockState upBlockState = world.getBlockState(pos.up());
        Block upBlock = upBlockState.getBlock();
        String upBlockName = upBlock.getRegistryName().toString();

        if (
            upBlock == Blocks.END_ROD ||
            upBlockName.contains("wall") ||
            upBlockName.contains("fence") ||
            upBlockName.contains("chain") ||
            upBlockName.contains("hanging") ||
            upBlock instanceof net.minecraft.block.BlockPane
           )
        {
            return;
        }

        if (upBlockState.isSideSolid(world, pos.up(), EnumFacing.DOWN)) {
            return;
        }

        this.spawnAsEntity(world, pos, new ItemStack(item));
        world.setBlockToAir(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                   EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // 点击 上/下方
        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) return false;

        // 为蹲下
        if (playerIn.isSneaking()) return false;

        // 获取点击面
        int slot = this.getSlotFromFacings(state.getValue(ROTATION), facing, playerIn);
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