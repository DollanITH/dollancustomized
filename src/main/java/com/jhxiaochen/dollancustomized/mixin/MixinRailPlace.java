package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.common.rail.RailPlacementTracker;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 让"孤轨/兜底的默认方向"跟随真正的放置者朝向，而不是恒定 NORTH_SOUTH。
 *
 * <h2>背景</h2>
 * {@code Block#getStateForPlacement(..., EntityLivingBase placer)} 能拿到真实放置者，
 * DollanCustomized 也已重写它（MixinBlockRail / MixinBlockRailDetector / MixinBlockRailPowered）
 * 把 shape 设为东西向或南北向。但放下之后原版会走
 * {@code BlockRailBase.onBlockAdded -> new Rail(...).place(true, true)}，而 {@code place()}
 * 的默认方向逻辑<b>只看四个邻居有没有铁轨，不知道放置者是谁</b>，于是刚设好的 shape 立刻被覆盖。
 *
 * <h2>place() 内的 NORTH_SOUTH 读取点（按方法内出现顺序）</h2>
 * <pre>
 *  ordinal 0 : 89  getstatic NORTH_SOUTH   if (!north &amp;&amp; !south &amp;&amp; !east &amp;&amp; !west) dir = NORTH_SOUTH;  // 孤轨
 *  ordinal 1 : 241 getstatic NORTH_SOUTH   if (!north &amp;&amp; south || ...) dir = NORTH_SOUTH;            // 连接判定
 *  ordinal 2 : 397 getstatic NORTH_SOUTH   if (dir == NORTH_SOUTH) { ...canMakeSlopes... }           // 比较，非赋值
 *  ordinal 3 : 509 getstatic NORTH_SOUTH   if (dir == null) dir = NORTH_SOUTH;                       // 兜底
 * </pre>
 *
 * <p><b>原实现只重定向了 ordinal = 3（兜底），而孤轨走的是 ordinal = 0</b> —— 单独放一根铁轨时
 * {@code dir} 在那一步已被赋成 NORTH_SOUTH（非 null），永远到不了兜底分支，所以修复实际不生效。
 * 这里同时接管 ordinal 0 与 ordinal 3：孤轨用放置者朝向，兜底也保持一致。
 *
 * <p>ordinal 1 与 2 刻意不动，它们参与真实连接判定与坡道判断。
 */
@Mixin(BlockRailBase.Rail.class)
public abstract class MixinRailPlace {

    @Shadow @Final private World world;
    @Shadow @Final private BlockPos pos;
    @Shadow @Final private IBlockState state;
    @Shadow @Final private BlockRailBase block;

    /**
     * 孤轨默认方向（ordinal 0）。
     *
     * <p>取不到放置者记录时回落到 {@code this.state} 当前的 shape —— 那是
     * {@code getStateForPlacement} 刚按朝向设好的值，比恒定 NORTH_SOUTH 更贴近意图。
     */
    @Redirect(
            method = "place",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/BlockRailBase$EnumRailDirection;NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;",
                    ordinal = 0,
                    opcode = Opcodes.GETSTATIC)
    )
    private BlockRailBase.EnumRailDirection dollan$defaultDirectionForIsolatedRail() {
        return dollan$resolveDefault();
    }

    /**
     * 兜底默认方向（ordinal 3，{@code if (dir == null) dir = NORTH_SOUTH;}）。
     * 与 ordinal 0 用同一套逻辑，避免两条路径结果不一致。
     */
    @Redirect(
            method = "place",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/BlockRailBase$EnumRailDirection;NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;",
                    ordinal = 3,
                    opcode = Opcodes.GETSTATIC)
    )
    private BlockRailBase.EnumRailDirection dollan$defaultDirectionFallback() {
        return dollan$resolveDefault();
    }

    /**
     * 依据 {@link RailPlacementTracker} 里登记的放置者朝向给出默认方向。
     *
     * <p>放在服务端与客户端都会执行；服务端只要在 {@code getStateForPlacement} 阶段登记过，
     * 就能算出与客户端一致的结果，不会再出现"客户端显示一个方向、服务端同步成另一个方向"。
     */
    private BlockRailBase.EnumRailDirection dollan$resolveDefault() {
        EnumFacing.Axis axis = RailPlacementTracker.takeAxis(this.world, this.pos);

        if (axis == EnumFacing.Axis.X) {
            return BlockRailBase.EnumRailDirection.EAST_WEST;
        }

        if (axis == EnumFacing.Axis.Z) {
            return BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        // 没有放置者记录（命令放置、结构生成、记录过期等）：沿用当前状态里已有的 shape，
        // 该值来自 getStateForPlacement，通常已按朝向设好。
        return dollan$currentShape();
    }

    /** 读取当前状态里的轨道方向；拿不到时退回 NORTH_SOUTH。 */
    private BlockRailBase.EnumRailDirection dollan$currentShape() {
        if (this.state == null || this.block == null) {
            return BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        IProperty<BlockRailBase.EnumRailDirection> property = this.block.getShapeProperty();

        if (property == null || !this.state.getProperties().containsKey(property)) {
            return BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        BlockRailBase.EnumRailDirection current = this.state.getValue(property);

        return current == null ? BlockRailBase.EnumRailDirection.NORTH_SOUTH : current;
    }
}
