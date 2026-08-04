package com.jhxiaochen.dollancustomized.common.block.wood.jacaranda;

import com.jhxiaochen.dollancustomized.common.block.wood.DollanSapling;
import com.jhxiaochen.dollancustomized.world.gen.DollanTreeGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class JacarandaSapling extends DollanSapling {

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

        // 使用枫木生成器
        boolean isBigTree = rand.nextInt(6) == 0;
        WorldGenerator worldgenerator = DollanTreeGenerator.createGenerator(isBigTree, rand);

        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
        worldIn.setBlockState(pos, iblockstate2, 4);

        if (!worldgenerator.generate(worldIn, rand, pos)) {
            worldIn.setBlockState(pos, state, 4);
        }
    }


}
