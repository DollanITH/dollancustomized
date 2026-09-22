package com.jhxiaochen.dollancustomized.mixin.mod.sakura;

import cn.mcmod.sakura.block.tree.BlockUmeLeave;
import cn.mcmod.sakura.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockUmeLeave.class)
public abstract class MixinBlockUmeLeave extends Block {

    @Shadow
    public abstract boolean isMaxAge(IBlockState state);

    public MixinBlockUmeLeave(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (this.isMaxAge(state)) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemLoader.FOODSET, 1, 166));
            super.breakBlock(worldIn, pos, state);
        }

        super.breakBlock(worldIn, pos, state);
        ci.cancel();
    }
}
