package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.ExtendedPlayer;
import com.jhxiaochen.dollancustomized.common.JacarandaStandingSign;
import com.jhxiaochen.dollancustomized.common.JacarandaWallSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemJacarandaSign extends Item {
    public ItemJacarandaSign() {
        super();
        this.maxStackSize = 16;
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setUnlocalizedName("jacaranda_sign");
        this.setRegistryName("jacaranda_sign");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        boolean flag = iblockstate.getBlock().isReplaceable(worldIn, pos);

        if (facing != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP)) {
            pos = pos.offset(facing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack)) {
                if (worldIn.isRemote) {
                    // 客户端：直接返回成功
                    return EnumActionResult.SUCCESS;
                } else {
                    // 服务端：放置方块
                    pos = flag ? pos.down() : pos;

                    if (facing == EnumFacing.UP) {
                        int i = MathHelper.floor((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                        worldIn.setBlockState(pos, JacarandaStandingSign.jacaranda_standing_sign.getDefaultState().withProperty(BlockStandingSign.ROTATION, Integer.valueOf(i)), 11);
                    } else {
                        worldIn.setBlockState(pos, JacarandaWallSign.Registry.jacaranda_wall_sign.getDefaultState().withProperty(BlockWallSign.FACING, facing), 11);
                    }

                    TileEntity tileentity = worldIn.getTileEntity(pos);
                    if (tileentity instanceof TileEntityJacarandaSign && !ItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack )) {
                        // 模仿原版openEditSign方法，但使用自定义网络包
                        ExtendedPlayer.openJacarandaSignGui((EntityPlayerMP) player, pos, tileentity);
                    }

                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        } else {
            return EnumActionResult.FAIL;
        }
    }
        public static Item jacaranda_sign = null;
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {


        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            jacaranda_sign = new ItemJacarandaSign();
            event.getRegistry().register(jacaranda_sign);
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(jacaranda_sign, 0, new ModelResourceLocation(jacaranda_sign.getRegistryName(), "inventory"));
        }
    }
}