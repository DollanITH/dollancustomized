package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JacarandaLog extends BlockLog {

    public static final PropertyEnum<EnumAxis> AXIS = PropertyEnum.create("axis", EnumAxis.class);
    public static Block jacaranda_log;
    public static Block jacaranda_wood;
    public static Block stripped_jacaranda_log;
    public static Block stripped_jacaranda_wood;

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS);
    }

    public JacarandaLog(Block stripped) {
        super();
        this.stripped = stripped;
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.Y));
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(2.0F).setResistance(5.0F);
        this.setSoundType(DollanSounds.CHERRY_WOOD);
    }

    public JacarandaLog() {
        super();
        this.stripped = null;
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.Y));
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setHardness(2.0F).setResistance(5.0F);
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_WOOD_BREAK,
                                        DollanSounds.BLOCK_CHERRY_WOOD_STEP,
                                        DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                                        DollanSounds.BLOCK_CHERRY_WOOD_HIT,
                                        DollanSounds.BLOCK_CHERRY_WOOD_FALL));
    }

    private final Block stripped;

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 0:
                iblockstate = iblockstate.withProperty(AXIS, EnumAxis.Y);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(AXIS, EnumAxis.X);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(AXIS, EnumAxis.Z);
                break;
        }

        return iblockstate;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i;

        switch (state.getValue(AXIS))
        {
            case X:
                i = 1;
                break;
            case Z:
                i = 2;
                break;
            case Y:
            default:
                i = 0;
        }

        return i;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        // 检查是否是斧头
        if (stack.getItem() instanceof ItemAxe && hand == EnumHand.MAIN_HAND) {
            // 如果是原木（不是去皮的），可以去皮
            if (stripped != null) {
                // 替换方块为去皮原木
                world.setBlockState(pos, stripped.getDefaultState().withProperty(AXIS, state.getValue(AXIS)), 3);

                // 添加声音效果
                world.playSound(null, pos, DollanSounds.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

                int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);

                boolean damage = true;
                if (efficiencyLevel > 0) {
                    if (Math.random() * 10 < efficiencyLevel) {
                        damage = false;
                    }
                }

                if (damage) {
                    stack.damageItem(1, player);
                }


                return true;
            }
        }

        return false;
    }

    public enum EnumAxis implements IStringSerializable
    {
        X("x"),
        Y("y"),
        Z("z");

        private final String name;

        EnumAxis(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public static EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Z:
                    return Z;
                case Y:
                default:
                    return Y;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch (state.getValue(AXIS))
                {
                    case X:
                        return state.withProperty(AXIS, EnumAxis.Z);
                    case Z:
                        return state.withProperty(AXIS, EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {

            // 注册去皮蓝花楹木原木
            stripped_jacaranda_log = new JacarandaLog();
            event.getRegistry().register(stripped_jacaranda_log.setUnlocalizedName("stripped_jacaranda_log").setRegistryName(Reference.MOD_ID, "stripped_jacaranda_log"));

            // 注册去皮蓝花楹木
            stripped_jacaranda_wood = new JacarandaLog();
            event.getRegistry().register(stripped_jacaranda_wood.setUnlocalizedName("stripped_jacaranda_wood").setRegistryName(Reference.MOD_ID, "stripped_jacaranda_wood"));

            // 注册蓝花楹木原木
            jacaranda_log = new JacarandaLog(stripped_jacaranda_log);
            event.getRegistry().register(jacaranda_log.setUnlocalizedName("jacaranda_log").setRegistryName(Reference.MOD_ID, "jacaranda_log"));

            // 注册蓝花楹木
            jacaranda_wood = new JacarandaLog(stripped_jacaranda_wood);
            event.getRegistry().register(jacaranda_wood.setUnlocalizedName("jacaranda_wood").setRegistryName(Reference.MOD_ID, "jacaranda_wood"));

        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(new ItemBlock(jacaranda_log).setRegistryName(jacaranda_log.getRegistryName()));
            event.getRegistry().register(new ItemBlock(jacaranda_wood).setRegistryName(jacaranda_wood.getRegistryName()));
            event.getRegistry().register(new ItemBlock(stripped_jacaranda_log).setRegistryName(stripped_jacaranda_log.getRegistryName()));
            event.getRegistry().register(new ItemBlock(stripped_jacaranda_wood).setRegistryName(stripped_jacaranda_wood.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event) {
            DollanBlocks.SimpleBlock(jacaranda_log);
            DollanBlocks.SimpleBlock(jacaranda_wood);
            DollanBlocks.SimpleBlock(stripped_jacaranda_wood);
            DollanBlocks.SimpleBlock(stripped_jacaranda_log);
        }
    }
}