package com.jhxiaochen.dollancustomized.common.block.wood.jacaranda;

import com.jhxiaochen.dollancustomized.DollanConfig;
import com.jhxiaochen.dollancustomized.client.particle.ParticleManager;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanLeaves;
import com.jhxiaochen.dollancustomized.registry.blocks.WoodBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

    public class JacarandaLeaves extends DollanLeaves {
        public JacarandaLeaves() {
            super(WoodBlocks.jacaranda_sapling);
        }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        // 只在客户端生成粒子
        if (!worldIn.isRemote || !DollanConfig.ENABLE_JACARANDA_LEAVES) {
            return;
        }

        // 使用新的粒子管理系统生成蓝花楹花瓣
        ParticleManager.spawnJacarandaParticles(worldIn, pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() + 0.5, rand);
    }

}
