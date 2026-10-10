package com.jhxiaochen.dollancustomized.world.level;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorldProviderFlatDim extends WorldProvider {

    @Override
    public void init() {
        this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
        this.hasSkyLight = true;
        this.doesWaterVaporize = false;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorFlat(
                this.world,
                this.world.getSeed(),
                this.world.getWorldInfo().isMapFeaturesEnabled(),
                FlatGeneratorInfo.getDefaultFlatGenerator().toString()
        );
    }

    @Override
    public DimensionType getDimensionType() {
        return DollanCustomized.FLAT_DIM;
    }

    @Nullable
    @Override
    public String getSaveFolder() {
        return "formwork";
    }

    @Override
    public boolean canDoRainSnowIce(@Nonnull Chunk chunk) {
        return false;
    }

    @Override
    public void calculateInitialWeather() {
    }

    @Override
    public void updateWeather() {
        world.getWorldInfo().setCleanWeatherTime(world.getWorldInfo().getCleanWeatherTime());
    }

    @Override
    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
        super.setAllowedSpawnTypes(false, true);
    }

    @Override
    public WorldBorder createWorldBorder() {
        WorldBorder border = super.createWorldBorder();
        border.setCenter(0.0, 0.0);
        border.setSize(6000);
        return border;
    }

    @Override
    public float getCloudHeight() {
        return this.getActualHeight();
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos) {
        return WorldSleepResult.DENY;
    }

    @Override
    public long getWorldTime() {
        return 6000;
    }

    @Override
    public boolean canMineBlock(EntityPlayer player, BlockPos pos) {
        // 1. 获取目标方块的 Y 坐标
        int blockY = pos.getY();

        // 2. 如果方块在 Y=0，禁止挖掘
        if (blockY == 0) {
            return false; // 返回 false 意味着“这个方块不能被这个工具挖掘”
        }

        // 3. 其他情况正常执行
        return super.canMineBlock(player, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getHorizon() {
        return 0.0D;
    }
}
