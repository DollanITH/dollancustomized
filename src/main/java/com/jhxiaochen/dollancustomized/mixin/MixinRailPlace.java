package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.helper.PlacementContextHolder;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRailBase.Rail.class)
public abstract class MixinRailPlace {

    @Shadow @Final private World world;
    @Shadow @Final private BlockPos pos;
    @Shadow private IBlockState state;

    @Inject(method = "place", at = @At("RETURN"))
    public void dollan$fixFacing(boolean powered, boolean initialPlacement,
                                 CallbackInfoReturnable<BlockRailBase.Rail> cir) {
        if (!initialPlacement) { PlacementContextHolder.clear(); return; }

        // 孤轨检查
        for (EnumFacing f : EnumFacing.HORIZONTALS) {
            if (world.getBlockState(pos.offset(f)).getBlock() instanceof BlockRailBase) return;
        }

        EnumFacing pf = PlacementContextHolder.get();
        PlacementContextHolder.clear();
        if (pf == null || pf.getAxis() != EnumFacing.Axis.X) return;

        IBlockState cur = world.getBlockState(pos);
        for (IProperty<?> p : cur.getPropertyKeys()) {
            if (p.getValueClass() == BlockRailBase.EnumRailDirection.class) {
                @SuppressWarnings("unchecked")
                IProperty<BlockRailBase.EnumRailDirection> sp =
                        (IProperty<BlockRailBase.EnumRailDirection>) p;
                world.setBlockState(pos, cur.withProperty(sp,
                        BlockRailBase.EnumRailDirection.EAST_WEST), 2);
                System.out.println("[RailMixin] fixed to EAST_WEST");
                break;
            }
        }
    }

}
