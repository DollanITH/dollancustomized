package com.jhxiaochen.dollancustomized.common.block.wood;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.wood.jacaranda.JacarandaLeaves;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DollanLeaves extends BlockLeaves {
    public Block sapling;
    public DollanLeaves(Block sapling) {
        super();
        this.sapling = sapling;
        this.setDefaultState(this.getDefaultState()
                                     .withProperty(CHECK_DECAY, Boolean.TRUE)
                                     .withProperty(DECAYABLE, Boolean.TRUE));
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {
        if (worldIn.rand.nextInt(chance) == 0)
        {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(DECAYABLE, (meta & 1) == 0)
                .withProperty(CHECK_DECAY, (meta & 2) != 0);
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(DECAYABLE) ? 0 : 1) |
                (state.getValue(CHECK_DECAY) ? 2 : 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // 高版本叶子掉落逻辑：6.25%概率获得树苗，87.5%概率获得木棍，6.25%无掉落
        float chance = rand.nextFloat();
        if (chance < 0.0625F) {
            // 6.25% 概率获得树苗
            return Item.getItemFromBlock(sapling);
        } else if (chance < 0.9375F) {
            // 87.5% 概率获得木棍
            return Items.STICK;
        }
        // 6.25% 概率无掉落
        return null;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }

    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
            spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1, 0));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState()
                .withProperty(CHECK_DECAY, false)
                .withProperty(DECAYABLE, false);
    }

    public static Block jacaranda_leaves = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {


        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_leaves = new JacarandaLeaves()
                    .setSoundType(DollanSounds.CHERRY_LEAVES)
                    .setUnlocalizedName("jacaranda_leaves")
                    .setRegistryName(Reference.MOD_ID, "jacaranda_leaves"));
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(jacaranda_leaves)
                                                 .setRegistryName(jacaranda_leaves.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            DollanBlocks.SimpleBlock(jacaranda_leaves);
            ModelLoader.setCustomStateMapper(jacaranda_leaves, (new StateMap.Builder()).ignore(JacarandaLeaves.CHECK_DECAY, JacarandaLeaves.DECAYABLE).build());
        }
    }
}
