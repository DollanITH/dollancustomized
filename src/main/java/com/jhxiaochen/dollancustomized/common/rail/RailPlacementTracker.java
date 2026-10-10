package com.jhxiaochen.dollancustomized.common.rail;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 记录"这个铁轨是谁放的、放的时候朝向哪"，供 {@code BlockRailBase.Rail#place} 推导默认方向。
 *
 * <p>为什么需要它：{@code Block#getStateForPlacement(..., EntityLivingBase placer)} 确实能拿到
 * 真实放置者，DollanCustomized 也已经重写了它（MixinBlockRail / Detector / Powered），把 shape
 * 设成朝北/朝南/朝东/朝西。但紧接着原版流程会走到
 * {@code BlockRailBase.onBlockAdded -> new Rail(...).place(true, true)}，
 * 而 {@code place()} 内部的默认方向逻辑<b>只看四个邻居有没有铁轨</b>，完全不知道放置者是谁：
 *
 * <pre>
 *   if (!north && !south && !east && !west) dir = NORTH_SOUTH;   // 孤轨 → 永远南北
 *   ...
 *   if (dir == null) dir = NORTH_SOUTH;                          // 兜底
 *   updateConnectedRails(dir);                                   // 用 dir 覆盖回方块状态
 * </pre>
 *
 * <p>于是"按朝向决定方向"在放下的一瞬间就被 {@code place()} 覆盖掉了。这里用一个短时效的
 * 侧表把放置者朝向带进 {@code place()}，让两边都能算出同一个结果（服务端与客户端一致），
 * 不依赖 {@code Minecraft.getMinecraft().player}（那是客户端专用类，服务端会崩）。
 *
 * <p>线程安全：服务器主线程放置 + 集成服务端；用 {@link WeakHashMap} 以 {@link World} 为键，
 * 世界卸载后可回收，不造成跨存档泄漏。
 */
public final class RailPlacementTracker {

    /** 一次注册的有效期（毫秒）。原版放置流程是同一 tick 内的同步调用，500ms 足够宽松。 */
    private static final long TTL_MS = 500L;

    private static final Map<World, Entry> TRACKED = new WeakHashMap<>();

    private static final class Entry {
        final BlockPos pos;
        final EnumFacing horizontal;
        final long stamp;

        Entry(BlockPos pos, EnumFacing horizontal, long stamp) {
            this.pos = pos;
            this.horizontal = horizontal;
            this.stamp = stamp;
        }
    }

    private RailPlacementTracker() {
    }

    /**
     * 在 {@code getStateForPlacement} 里登记放置者朝向。
     *
     * @param placer 可能为 null（例如命令/结构生成），此时不登记，行为退回原版。
     */
    public static void record(World world, BlockPos pos, EntityLivingBase placer) {
        if (world == null || pos == null || placer == null) {
            return;
        }

        synchronized (TRACKED) {
            TRACKED.put(world, new Entry(pos.toImmutable(), placer.getHorizontalFacing(), System.currentTimeMillis()));
        }
    }

    /**
     * 在 {@code Rail#place} 里取出放置者朝向对应的轨道方向。
     *
     * @return 对应方向；没有记录（或已过期、坐标不匹配）时返回 {@code null}，调用方应沿用原版默认值。
     */
    public static EnumFacing.Axis takeAxis(World world, BlockPos pos) {
        if (world == null || pos == null) {
            return null;
        }

        synchronized (TRACKED) {
            Entry entry = TRACKED.get(world);

            if (entry == null) {
                return null;
            }

            // 一次性消费：无论是否匹配都清掉，避免影响下一次放置。
            TRACKED.remove(world);

            if (!entry.pos.equals(pos) || System.currentTimeMillis() - entry.stamp > TTL_MS) {
                return null;
            }

            // 与既有 getStateForPlacement 保持同一套换算：取反后判断东西向。
            EnumFacing direction = entry.horizontal.getOpposite();

            return direction.getAxis();
        }
    }
}
