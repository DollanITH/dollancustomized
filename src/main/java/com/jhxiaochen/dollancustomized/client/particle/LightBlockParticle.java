package com.jhxiaochen.dollancustomized.client.particle;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.BlockLightBlock;
import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

/**
 * 光源方块显形粒子：参照 LightAndShadow 的 ParticleLight。
 * 手持光源方块时，在已放置的光源方块位置渲染一个 1 格大的 billboard，
 * 纹理直接复用物品材质 textures/items/light_NN.png（按方块自身光照等级选择，
 * 不新增/修改任何材质），恒定全亮、不随世界光照变化。
 */
@SideOnly(Side.CLIENT)
public class LightBlockParticle extends ParticleBlockConditional {

    public LightBlockParticle(World world, double x, double y, double z) {
        super(world, x, y, z, Item.getItemFromBlock(DollanBlocks.light_block));
    }

    @Override
    protected @Nullable ResourceLocation getTexture() {
        if (world == null) return null;
        IBlockState state = world.getBlockState(new BlockPos(posX, posY, posZ));
        if (state.getBlock() != DollanBlocks.light_block) return null;
        int level = state.getValue(BlockLightBlock.LEVEL);
        return new ResourceLocation(Reference.MOD_ID,
                "textures/items/light_" + String.format("%02d", level) + ".png");
    }

    @Override
    protected boolean isValidBlock() {
        if (world == null) return false;
        return world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == DollanBlocks.light_block;
    }
}
