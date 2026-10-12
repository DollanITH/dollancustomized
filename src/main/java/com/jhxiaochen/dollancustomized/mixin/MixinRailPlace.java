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
 * 让"<b>孤轨</b>的默认方向"跟随真正的放置者朝向，而不是恒定 {@code NORTH_SOUTH}。
 * 用于在 1.12.2 上复刻高版本（1.19+）"按玩家朝向决定铁轨初始方向"的手感。
 *
 * <h2>place() 内的方向判定结构（已逐条核对字节码）</h2>
 * <pre>
 *  7/8/9/10 = hasNeighborRail(north/south/west/east)
 *  dir (local 11) 初始为 null
 *
 *   89: (!east &amp;&amp; !west) &amp;&amp; (north || south)   → NORTH_SOUTH   // ★ 南北直线
 *  114: (!north &amp;&amp; !south) &amp;&amp; (west || east)   → EAST_WEST     // 东西直线
 *  ... 之后是坡道/交叉等组合判定（走别的常量，不是 NORTH_SOUTH）...
 *  509: if (dir == null) dir = NORTH_SOUTH;                      // 兜底
 *  514: updateConnectedRails(dir);                               // 用 dir 写回状态
 * </pre>
 *
 * <h2>★ 两条分支的语义必须严格区分</h2>
 * <ul>
 *   <li><b>ordinal 0（字节码 89）＝ 南北直线</b>。条件是
 *       {@code (!east &amp;&amp; !west) &amp;&amp; (north || south)} ——
 *       <b>南北方向确实有邻居铁轨</b>。原版在这里给出 NORTH_SOUTH 是<b>连接推导的结果</b>，
 *       不是"随便选一个默认值"。<b>绝不能改成玩家朝向</b>：否则一条本该南北相连的
 *       铁轨会被摆成东西向，直接断链。</li>
 *   <li><b>ordinal 3（字节码 509）＝ 兜底</b>。条件是 {@code dir == null}，
 *       即前面所有分支都<b>没有</b>给出方向 —— 只有真正孤轨才会走到这里。
 *       <b>这里才是"按玩家朝向"的正确位置。</b></li>
 * </ul>
 *
 * <p>放置者朝向的来源见 {@link RailPlacementTracker}：由 {@code getStateForPlacement}
 * 登记、{@code place()} 里取用，因此服务端与客户端能算出同一结果。
 */
@Mixin(BlockRailBase.Rail.class)
public abstract class MixinRailPlace {

    @Shadow @Final private World world;
    @Shadow @Final private BlockPos pos;
    @Shadow @Final private IBlockState state;
    @Shadow @Final private BlockRailBase block;

    /** 轨道是否允许变成上下坡；坡道判定与它相关，取不到记录时要据此让位给原版。 */
    @Shadow @Final private boolean canMakeSlopes;

    /**
     * 兜底默认方向（ordinal 3，字节码 509：{@code if (dir == null) dir = NORTH_SOUTH;}）。
     *
     * <p><b>这是唯一应该按放置者朝向决定方向的位置</b> —— 只有四邻全无铁轨的孤轨
     * 才会让 {@code dir} 保持 {@code null} 并落到这里。
     *
     * <p>注意 ordinal <b>0</b>（字节码 89）刻意<b>不</b>重定向：那是南北直线分支，
     * 其 NORTH_SOUTH 是连接推导的结果，改动它会破坏连接。
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
     * 依据 {@link RailPlacementTracker} 里登记的放置者朝向给出孤轨默认方向。
     *
     * <p>两种情况让位给原版 {@code NORTH_SOUTH}：
     * <ul>
     *   <li>上下方存在铁轨（可能构成坡道）—— 原版坡道判定只在 {@code dir == NORTH_SOUTH}
     *       时进入，这里若返回 {@code EAST_WEST} 就等于把坡道能力关掉了；</li>
     *   <li>取不到放置者记录 —— 命令放置、结构生成、记录过期等。</li>
     * </ul>
     */
    private BlockRailBase.EnumRailDirection dollan$resolveDefault() {
        // 先无条件取走记录：无论后面走哪条分支，都不能把它留给下一次放置。
        EnumFacing.Axis axis = RailPlacementTracker.takeAxis(this.world, this.pos);

        // 上下方有轨道 -> 可能是坡道，必须交回原版 NORTH_SOUTH 让坡道判定继续。
        if (this.canMakeSlopes
                && (BlockRailBase.isRailBlock(this.world, this.pos.up())
                        || BlockRailBase.isRailBlock(this.world, this.pos.down()))) {
            return BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        if (axis == EnumFacing.Axis.X) {
            return BlockRailBase.EnumRailDirection.EAST_WEST;
        }

        if (axis == EnumFacing.Axis.Z) {
            return BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        // 没有放置者记录：沿用当前状态里已有的 shape（来自 getStateForPlacement），
        // 也就是不要覆盖它已经算好的结果。
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