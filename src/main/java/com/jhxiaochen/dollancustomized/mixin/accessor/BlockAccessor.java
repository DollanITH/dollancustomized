package com.jhxiaochen.dollancustomized.mixin.accessor;

import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(net.minecraft.block.Block.class)
public interface BlockAccessor {
    @Invoker("setDefaultState")
    void dollan$setDefaultState(IBlockState state);
}
