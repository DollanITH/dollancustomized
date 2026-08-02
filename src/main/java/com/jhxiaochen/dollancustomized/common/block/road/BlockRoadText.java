package com.jhxiaochen.dollancustomized.common.block.road;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.client.gui.GuiRoadText;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRoadText extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<MarkType> TYPE = PropertyEnum.create("type", MarkType.class);
    
    // 微调了 Y 轴高度，方便 TESR 渲染时不穿模
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(-0.25D, 0.0D, -0.25D, 1.25D, 0.015D, 1.25D);
    protected static final AxisAlignedBB AABB_BOTTOM_DOWN = new AxisAlignedBB(-0.25D, -0.5D, -0.25D, 1.25D, 0.015D, 1.25D);

    public BlockRoadText() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(DollanCustomized.ROAD_Signs);
        this.setHardness(1.0F);
        this.setResistance(10F);
        this.setLightOpacity(0);
        this.setTickRandomly(true);
        this.hasTileEntity = true; // 声明拥有 TileEntity
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityRoadText();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // 【关键修复】必须确保仅在客户端（worldIn.isRemote）打开 GUI！
        if (worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityRoadText) {
                // 在客户端打开 GUI，Minecraft 会自动正确处理鼠标指针的释放
                Minecraft.getMinecraft().displayGuiScreen(new GuiRoadText((TileEntityRoadText) te));
            }
        }
        // 返回 true 表示交互成功
        return true;
    }


    // --- 核心新增：右键打开 GUI ---
    /*
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {

        // 必须在服务端执行打开 GUI 的逻辑 (或者使用 playerIn.getServer() != null 判断)
        if (!worldIn.isRemote) {
            // 检查方块下面是不是半砖 (根据你的 MarkType 逻辑，如果是普通方块直接打开)
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityRoadText) {
                // 打开 GUI：传入世界、玩家、GUI ID、坐标
                playerIn.openGui(DollanCustomized.instance, GuiHandler.ROAD_TEXT_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        // 返回 true 表示右键交互成功，阻止方块被放置其他物品
        return true;
    }*/

    // --- 核心新增：绑定 TileEntity ---
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
            return new TileEntityRoadText();
    }

    public enum MarkType implements IStringSerializable {
        FULL(0), SLAB(1);
        final int metadata;
        private static final MarkType[] METADATA_LOOKUP = new MarkType[values().length];
        MarkType(int metadataIn) { this.metadata = metadataIn; }
        public String toString() { return this.name().toLowerCase(); }
        public String getName() { return this.toString(); }
        public String getUnlocalizedName() { return this.toString(); }
        public static MarkType byMetadata(int metadata) {
            if (metadata < 0 || metadata >= METADATA_LOOKUP.length) metadata = 0;
            return METADATA_LOOKUP[metadata];
        }
        public int getMetadata() { return this.metadata; }
        static { for (MarkType varient : values()) METADATA_LOOKUP[varient.getMetadata()] = varient; }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(TYPE) == MarkType.FULL ? AABB_BOTTOM_HALF : AABB_BOTTOM_DOWN;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB; // 无碰撞箱
    }

    @Override
    public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT_MIPPED; }
    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }
    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) { return state.withProperty(FACING, rot.rotate(state.getValue(FACING))); }
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) { return state.withRotation(mirrorIn.toRotation(state.getValue(FACING))); }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(TYPE, (meta & 4) != 0 ? MarkType.SLAB : MarkType.FULL);
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        MarkType type = state.getValue(TYPE);
        int v = 0x0;
        if (type == MarkType.SLAB) v = 0x4;
        return state.getValue(FACING).getHorizontalIndex() | v;
    }

    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, FACING, TYPE); }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(TYPE, MarkType.FULL);
        if (SlabChange(world.getBlockState(pos.down()))) {
            state = state.withProperty(TYPE, MarkType.SLAB);
        }
        return state;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) { return
            side != EnumFacing.DOWN || super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.checkForDrop(worldIn,pos,state);        // 确保TileEntity存在
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
            worldIn.setTileEntity(pos, this.createNewTileEntity(worldIn, 0));
        }
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        return SlabChange(downState) || downState.isTopSolid() || downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        IBlockState downState = worldIn.getBlockState(pos.down());
        return SlabChange(downState) || downState.isTopSolid() || downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }

    // 优化：在方块改变或销毁时，清理 TileEntity，防止内存泄漏
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            IBlockState downState = worldIn.getBlockState(pos.down());
            // 1. 保存TileEntity的NBT数据
            NBTTagCompound nbt = new NBTTagCompound();
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityRoadText) {
                ((TileEntityRoadText) te).writeToNBT(nbt);
            }

            // 2. 改变方块状态
            IBlockState newState;
            if (state.getValue(TYPE) == MarkType.FULL && SlabChange(downState)) {
                newState = state.withProperty(TYPE, MarkType.SLAB);
            } else if (state.getValue(TYPE) == MarkType.SLAB && !SlabChange(downState) && downState.isTopSolid()) {
                newState = state.withProperty(TYPE, MarkType.FULL);
            } else {
                return;
            }

            worldIn.setBlockState(pos, newState);

            // 3. 重新设置TileEntity数据
            te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityRoadText) {
                ((TileEntityRoadText) te).readFromNBT(nbt);
                ((TileEntityRoadText) te).markDirty();
            }

            // 4. 通知客户端更新
            worldIn.notifyBlockUpdate(pos, state, newState, 3);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        // 1. 保存TileEntity的NBT数据
        NBTTagCompound nbt = new NBTTagCompound();
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityRoadText) {
            ((TileEntityRoadText) te).writeToNBT(nbt);
        }

        // 2. 改变方块状态
        IBlockState newState;
        if (state.getValue(TYPE) == MarkType.FULL && SlabChange(downState)) {
            newState = state.withProperty(TYPE, MarkType.SLAB);
        } else if (state.getValue(TYPE) == MarkType.SLAB && !SlabChange(downState) && downState.isTopSolid() && downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID) {
            newState = state.withProperty(TYPE, MarkType.FULL);
        } else if (!SlabChange(downState) && !downState.isTopSolid() && downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) != BlockFaceShape.SOLID) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            worldIn.notifyBlockUpdate(pos, state, worldIn.getBlockState(pos), 3);
            return;
        } else {
            return;
        }

        worldIn.setBlockState(pos, newState);

        // 3. 重新设置TileEntity数据
        te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityRoadText) {
            ((TileEntityRoadText) te).readFromNBT(nbt);
            ((TileEntityRoadText) te).markDirty();
        }

        // 4. 通知客户端更新
        worldIn.notifyBlockUpdate(pos, state, newState, 3);
    }



    public boolean SlabChange(IBlockState state){
        return state.getBlock() instanceof BlockAsphaltSlab || (state.getBlock() instanceof BlockSlab && !state.getBlock().isTopSolid(state)) || state.getBlock() instanceof BlockAsphaltSlab1 || state.getBlock() instanceof BlockAsphaltSlabHorizontal || state.getBlock() instanceof BlockAsphaltSlabHorizontal2;
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

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block block;
        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event){
            event.getRegistry().register(block = new BlockRoadText().setRegistryName("road_text_mark").setUnlocalizedName("RoadTextMark"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(new ItemBlock(block).setRegistryName("road_text_mark"));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:road_text_mark", "inventory"));
        }
    }
}
