package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import com.jhxiaochen.dollancustomized.particles.JacarandaParticleHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class JacarandaLeaves extends BlockLeaves {

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        // 模仿高版本樱花树叶的粒子效果

        // 基础花瓣飘落（较低概率）
        if (rand.nextInt(200) == 0) {
            generateJacarandaParticles(worldIn, pos, rand, 1);
        }

        // 风吹时的花瓣飘落（中等概率）
        if (rand.nextInt(100) == 0) {
            generateJacarandaParticles(worldIn, pos, rand, 2);
        }

        // 风暴时的花瓣飘落（高概率）
        if (rand.nextInt(30) == 0) {
            generateJacarandaParticles(worldIn, pos, rand, 3);
        }

        // 特殊事件：节日花瓣雨（极低概率）
        if (rand.nextInt(1000) == 0) {
            for (int i = 0; i < 5; i++) {
                generateJacarandaParticles(worldIn, pos, rand, 4);
            }
        }
    }

    private void generateJacarandaParticles(World worldIn, BlockPos pos, Random rand, int intensity) {
        int particleCount = 0;
        double baseYSpeed = 0.0D;

        switch (intensity) {
            case 1: // 平缓飘落
                particleCount = 1;
                baseYSpeed = -0.01D;
                break;
            case 2: // 风吹效果
                particleCount = 2;
                baseYSpeed = -0.02D;
                break;
            case 3: // 暴风雨效果
                particleCount = 3;
                baseYSpeed = -0.03D;
                break;
            case 4: // 花瓣雨
                particleCount = 4;
                baseYSpeed = -0.015D;
                break;
        }

        for (int i = 0; i < particleCount; i++) {
            // 在树叶方块内生成粒子
            double x = pos.getX() + 0.3D + rand.nextDouble() * 0.4D;
            double y = pos.getY() + 0.1D + rand.nextDouble() * 0.8D;
            double z = pos.getZ() + 0.3D + rand.nextDouble() * 0.4D;

            // 根据强度调整粒子行为
            double xSpeed = (rand.nextDouble() - 0.5D) * 0.02D * intensity;
            double ySpeed = baseYSpeed - rand.nextDouble() * 0.01D * intensity;
            double zSpeed = (rand.nextDouble() - 0.5D) * 0.02D * intensity;

            // 添加随机性使粒子更自然
            xSpeed += (rand.nextDouble() - 0.5D) * 0.005D;
            zSpeed += (rand.nextDouble() - 0.5D) * 0.005D;

            // 生成自定义粒子
            if (worldIn.isRemote) {
                // 使用自定义粒子系统
                JacarandaParticleHelper.spawnJacarandaParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            } else {
                // 服务端使用普通粒子作为备选
                EnumParticleTypes particleType = getJacarandaParticleType(rand, intensity);
                worldIn.spawnParticle(particleType, x, y, z, xSpeed, ySpeed, zSpeed);
            }
        }
    }

    private EnumParticleTypes getJacarandaParticleType(Random rand, int intensity) {
        // 蓝花楹使用紫色系粒子
        if (intensity == 1) {
            // 平缓模式：使用淡紫色粒子
            return rand.nextBoolean() ? EnumParticleTypes.TOWN_AURA : EnumParticleTypes.VILLAGER_HAPPY;
        } else if (intensity == 4) {
            // 花瓣雨：使用最美丽的粒子
            return EnumParticleTypes.VILLAGER_HAPPY;
        } else {
            // 其他模式：混合使用
            int choice = rand.nextInt(3);
            switch (choice) {
                case 0: return EnumParticleTypes.TOWN_AURA;
                case 1: return EnumParticleTypes.VILLAGER_HAPPY;
                default: return EnumParticleTypes.CRIT;
            }
        }
    }

    public JacarandaLeaves() {
        super();
        this.setCreativeTab(DollanCustomized.WOOD_TAB);
        this.setUnlocalizedName("jacaranda_leaves");
        this.setRegistryName(Reference.MOD_ID, "jacaranda_leaves");
        this.setSoundType(new SoundType(1.0F, 1.0F,
                                        DollanSounds.BLOCK_CHERRY_LEAVES_BREAK,
                                        DollanSounds.BLOCK_CHERRY_LEAVES_STEP,
                                        DollanSounds.BLOCK_CHERRY_LEAVES_PLACE,
                                        DollanSounds.BLOCK_CHERRY_LEAVES_HIT,
                                        DollanSounds.BLOCK_CHERRY_LEAVES_FALL));
        this.getDefaultState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true));
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
        return rand.nextFloat() < 6 ? null : Items.STICK;
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

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Registry {
        public static Block jacaranda_leaves =  null;

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(jacaranda_leaves = new JacarandaLeaves());
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event){
            event.getRegistry().register(new ItemBlock(jacaranda_leaves).setRegistryName(jacaranda_leaves.getRegistryName()));
        }

        @SubscribeEvent
        public static void registerModel(ModelRegistryEvent event){
            ModelLoader.setCustomStateMapper(jacaranda_leaves, (new StateMap.Builder()).ignore(JacarandaLeaves.CHECK_DECAY, JacarandaLeaves.DECAYABLE).build());
        }}
}
