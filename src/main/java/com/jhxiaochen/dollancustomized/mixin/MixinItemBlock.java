package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.helper.PlacementContextHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlock.class)
    public class MixinItemBlock {

        @Shadow
        @Final
        protected Block block;

        @Shadow
        public Block getBlock() {
            return this.getBlockRaw() == null ? null : this.getBlockRaw().delegate.get();
        }

        @Shadow
        private Block getBlockRaw() {
            return this.block;
        }

        @Inject(method = "onItemUse", at = @At("HEAD"))
        public void dollan$captureFacing(EntityPlayer player, World worldIn, BlockPos pos,
                                         EnumHand hand, EnumFacing facing,
                                         float hitX, float hitY, float hitZ,
                                         CallbackInfoReturnable<EnumActionResult> cir) {
            if (this.getBlock() instanceof BlockRailBase) {
                PlacementContextHolder.set(player.getHorizontalFacing());
            } else {
                PlacementContextHolder.clear();
            }
        }
    }