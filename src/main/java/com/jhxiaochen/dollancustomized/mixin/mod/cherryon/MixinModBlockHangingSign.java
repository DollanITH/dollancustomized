package com.jhxiaochen.dollancustomized.mixin.mod.cherryon;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 修复 Cherry_on (suikecherry) ModBlockHangingSign#getStateFromMeta 的非法 facing 崩溃。
 *
 * <p>原实现等价于：
 * {@code return this.getDefaultState().withProperty(FACING, EnumFacing.byMetadata(meta & 7));}
 * 而 {@code byMetadata} 取值数组为 {DOWN, UP, NORTH, SOUTH, WEST, EAST}，FACING 属性只允许
 * 水平四向 [north, south, west, east]。当 {@code meta & 7} 为 0/1/6/7 时会算出 DOWN/UP，
 * {@code withProperty} 直接抛 {@link IllegalArgumentException}。
 *
 * <p>触发链：Chisels-and-Bits 在区块数据序列化（SPacketChunkData → TileEntityBlockChiseled
 * → NBTBlobConverter → StateLookup.getStateById）时把非法 meta 传入，导致服务端 tick 崩溃
 * （crash-2026-10-04_20.30.19-server.txt）。
 *
 * <p>此处 HEAD 拦截非法 meta，直接返回默认状态。默认状态的 FACING 在 Cherry_on 构造函数中
 * 固定为 NORTH（已由字节码确认），天然合法，杜绝异常外抛；合法 meta（2-5）原样放行，行为不变。
 *
 * <p>使用字符串 targets，避免编译期依赖 Cherry_on jar（libs 下的 flatDir 依赖无法被
 * Unimined 的 ModRemapper 处理为 MCP 命名空间）。
 */
@Mixin(targets = "suike.suikecherry.block.ModBlockHangingSign", priority = 999)
public abstract class MixinModBlockHangingSign {

    /**
     * 目标方法名使用 SRG 名 func_176203_a（= getStateFromMeta）：Cherry_on 不在构建依赖中，
     * TinyRemapper 无法把 MCP 名映射为 SRG，而运行时（dev 与服务器）mods 目录下的 Cherry_on
     * 始终是原始 SRG jar，因此必须直接写 SRG 名才能命中目标方法。
     */
    @Inject(method = "func_176203_a", at = @At("HEAD"), cancellable = true, require = 0)
    private void dollan$clampInvalidMeta(int meta, CallbackInfoReturnable<IBlockState> cir) {
        int m = meta & 7;
        // byMetadata: 0→DOWN 1→UP 6→(6%6)→DOWN 7→(7%6)→UP，均非法；钳制为默认状态（FACING=NORTH）
        if (m == 0 || m == 1 || m == 6 || m == 7) {
            cir.setReturnValue(((Block) (Object) this).getDefaultState());
        }
    }
}
