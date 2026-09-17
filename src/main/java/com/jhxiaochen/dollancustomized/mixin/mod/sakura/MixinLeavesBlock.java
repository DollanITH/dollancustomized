package com.jhxiaochen.dollancustomized.mixin.mod.sakura;

import cn.mcmod.sakura.block.tree.BlockUmeLeave;
import com.jhxiaochen.dollancustomized.mixin.accessor.BlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {
        "cn.mcmod.sakura.block.tree.BlockMapleLeaveGreen",
        "cn.mcmod.sakura.block.tree.BlockMapleLeaveYellow",
        "cn.mcmod.sakura.block.tree.BlockMapleLeaveOrange",
        "cn.mcmod.sakura.block.tree.BlockMapleLeaveRed",
        "cn.mcmod.sakura.block.tree.BlockUmeLeave"
})
public class MixinLeavesBlock {

    @Inject(method = "<init>", at = @At(value = "TAIL"), require = 0)
    private void afterConstructor(CallbackInfo ci) {
        Block self = (Block)(Object) this;
        BlockStateContainer blockStateContainer = self.getBlockState();
        IBlockState newState;
        if (!(self instanceof BlockUmeLeave)){
            newState = blockStateContainer.getBaseState()
                    .withProperty(BlockLeaves.CHECK_DECAY, true)
                    .withProperty(BlockLeaves.DECAYABLE, true);
        } else {
            newState = blockStateContainer.getBaseState()
                    .withProperty(BlockUmeLeave.AGE, 0)
                    .withProperty(BlockLeaves.CHECK_DECAY, true)
                    .withProperty(BlockLeaves.DECAYABLE, true);
        }
        ((BlockAccessor) self).dollan$setDefaultState(newState);
    }
}
