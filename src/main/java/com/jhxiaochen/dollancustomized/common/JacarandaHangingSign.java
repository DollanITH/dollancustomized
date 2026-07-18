package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class JacarandaHangingSign extends Block {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    // 边界框定义
    protected static final AxisAlignedBB X_IN_WALL_BOX = new AxisAlignedBB(0.0, 0.875D, 0.375D, 1.0, 1.0, 0.625D);
    protected static final AxisAlignedBB Z_IN_WALL_BOX = new AxisAlignedBB(0.375D, 0.875D, 0.0, 0.625D, 1.0, 1.0);
    protected static final AxisAlignedBB X_BOX = new AxisAlignedBB(0.0625D, 0.0, 0.4375D, 0.9375D, 0.625D, 0.5625D);
    protected static final AxisAlignedBB Z_BOX = new AxisAlignedBB(0.4375D, 0.0, 0.0625D, 0.5625D, 0.625D, 0.9375D);

    private boolean inWall;

    public JacarandaHangingSign(String name) {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
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
        AxisAlignedBB box = null;
        switch ((EnumFacing) state.getValue(FACING)) {
            case NORTH:
                box = X_IN_WALL_BOX;
                break;
            case SOUTH:
                box = X_IN_WALL_BOX;
                break;
            case WEST:
                box = Z_IN_WALL_BOX;
                break;
            case EAST:
            default:
                box = Z_IN_WALL_BOX;
                break;
        }
        addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
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
        return new net.minecraft.tileentity.TileEntitySign();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(this));
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (!player.capabilities.isCreativeMode) {
            this.spawnAsEntity(world, pos, new ItemStack(this));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if (!world.getBlockState(pos.offset(enumfacing.getOpposite())).isNormalCube()) {
            this.spawnAsEntity(world, pos, new ItemStack(this));
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                   EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            return tileentity instanceof net.minecraft.tileentity.TileEntitySign ?
                   ((net.minecraft.tileentity.TileEntitySign)tileentity).executeCommand(playerIn) : false;
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_hanging_sign = null;
        public static Block jacaranda_hanging_sign_wall = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(
                    jacaranda_hanging_sign = new JacarandaHangingSign("jacaranda_hanging_sign"));
            event.getRegistry().register(
                    jacaranda_hanging_sign_wall = new JacarandaHangingSign("jacaranda_hanging_sign_wall"));
        }

    }
}