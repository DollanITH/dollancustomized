package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.BlockRailBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = BlockRailBase.class)
public class MixinBlockRailBase {
    /**
     * @author jhxiaochen
     * @reason speed max
     */
    @Overwrite(remap = false)
    public float getRailMaxSpeed(World world, net.minecraft.entity.item.EntityMinecart cart, BlockPos pos)
        {
            return 0.7f;
        }

}
