package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class JacarandaSign extends BlockContainer {

    protected static final AxisAlignedBB SIGN_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    public JacarandaSign() {
        super(Material.WOOD);
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
        this.setHardness(1.0F);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SIGN_AABB;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean canSpawnInBlock()
    {
        return true;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityJacarandaSign();
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return com.jhxiaochen.dollancustomized.common.item.ItemJacarandaSign.Registry.jacaranda_sign;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(com.jhxiaochen.dollancustomized.common.item.ItemJacarandaSign.Registry.jacaranda_sign);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityJacarandaSign) {
                // 如果玩家按住Shift键，打开GUI
                if (playerIn.isSneaking()) {
                    // 发送打开GUI的包
                    if (NetworkManager.isServerPlayer(playerIn)) {
                        NetworkManager.openSignGui((net.minecraft.entity.player.EntityPlayerMP) playerIn, pos);
                    }
                    return true;
                } else {
                    // 否则执行命令
                    return ((TileEntityJacarandaSign)tileentity).executeCommand(playerIn);
                }
            }
            // 兼容性检查，如果是普通的TileEntitySign，也支持命令执行
            if (tileentity instanceof TileEntitySign) {
                // 将调用转给原版TileEntitySign的executeCommand方法
                try {
                    java.lang.reflect.Method executeCommand = tileentity.getClass().getMethod("executeCommand", EntityPlayer.class);
                    return (Boolean) executeCommand.invoke(tileentity, playerIn);
                } catch (Exception e) {
                    return false;
                }
            }
            return false;
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return !this.hasInvalidNeighbor(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_sign = null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(
                    jacaranda_sign = new JacarandaSign().setRegistryName(Reference.MOD_ID, "jacaranda_sign").setUnlocalizedName("jacaranda_sign"));
        }

    }
}
