package com.jhxiaochen.dollancustomized.mixin;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockRailBase.Rail.class)
public abstract class MixinRailPlace {

    @Shadow @Final private World world;
    @Shadow @Final private BlockPos pos;
    @Shadow private IBlockState state;

    @Shadow
    @Final
    private boolean canMakeSlopes;

    @Shadow
    @Final
    private BlockRailBase block;
/*
    @Inject(method = "place", at = @At("RETURN"), cancellable = true)
    public void dollan$fixFacing(boolean powered, boolean initialPlacement,
                                 CallbackInfoReturnable<BlockRailBase.Rail> cir) {
        if (!initialPlacement) { PlacementContextHolder.clear(); return; }

        IBlockState cur = world.getBlockState(pos);

        // 孤轨检查 - 检查所有方向的轨道连接
        for (EnumFacing f : EnumFacing.values()) {
            if (world.getBlockState(pos.offset(f)).getBlock() instanceof BlockRailBase) {
                return;
            }
            if (BlockRailBase.isRailBlock(world, pos.offset(f).up())) {
                return;
            }
        }

        EnumFacing pf = PlacementContextHolder.get();
        PlacementContextHolder.clear();
        if (pf == null || pf.getAxis() != EnumFacing.Axis.X) return;

        for (IProperty<?> p : cur.getPropertyKeys()) {
            if (p.getValueClass() == BlockRailBase.EnumRailDirection.class) {
                @SuppressWarnings("unchecked")
                IProperty<BlockRailBase.EnumRailDirection> sp =
                        (IProperty<BlockRailBase.EnumRailDirection>) p;
                world.setBlockState(pos, cur.withProperty(sp,
                        BlockRailBase.EnumRailDirection.EAST_WEST), 2);
                cir.setReturnValue((BlockRailBase.Rail) (Object) this);
                cir.cancel();
                break;
            }
        }
    }*/

    /**
     * 目标代码（place() 末尾的兜底逻辑）：
     *     if (blockrailbase$enumraildirection == null)
     *     {
     *         blockrailbase$enumraildirection = EnumRailDirection.NORTH_SOUTH;
     *     }
     *
     * 编译后为：
     *     aload   blockrailbase$enumraildirection
     *     ifnonnull L
     *     getstatic EnumRailDirection.NORTH_SOUTH   <-- place() 中第 4 次读取该字段(ordinal = 3)
     *     astore   blockrailbase$enumraildirection
     *   L:
     *
     * 用 @Redirect 重定向最后一次 getstatic NORTH_SOUTH，
     * 把“无相邻轨道时的默认方向”从 NORTH_SOUTH 改掉。
     */
    @Redirect(
            method = "place",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/BlockRailBase$EnumRailDirection;NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;",
                    ordinal = 3,
                    opcode = Opcodes.GETSTATIC)
    )
    private BlockRailBase.EnumRailDirection dollan$defaultDirection() {
        EntityLivingBase placer = Minecraft.getMinecraft().player;
        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
            return BlockRailBase.EnumRailDirection.EAST_WEST;
        }
        return this.state.getValue(this.block.getShapeProperty());
    }
}
