package com.jhxiaochen.dollancustomized.mixin.mod.futuremc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thedarkcolour.futuremc.client.ClientEvents;
import thedarkcolour.futuremc.network.NetworkHandler;

/**
 * 将 FutureMC 的 F3+F4 游戏模式切换器从"发送 GameModeSwitchPacket 自定义包"
 * 改为"发送 /gamemode &lt;模式&gt; 聊天指令"——与 1.12.2 原版 F3+N 的机制完全一致。
 *
 * <p>为什么这样改：原包路径在服务端 Netty IO 线程直接调用 EntityPlayerMP.setGameType，
 * SpongeForge 环境触发 CauseStackManager 主线程断言，导致玩家掉线。改为指令输入后
 * 包路径整体消失，服务端只收到一条普通聊天指令（主线程执行），从根上消除这一类问题，
 * 同时也不再依赖 FutureMC 服务端 handler 的任何逻辑。
 *
 * <p>同时绕过 FutureMC 客户端侧的权限等级门控（getPermissionLevel() &gt;= 2 才发送），
 * 本修复不做任何权限检查；是否允许由服务端指令权限体系决定。
 *
 * <p>注意：本 mixin 引用客户端专用类（ClientEvents / EntityPlayerSP / Minecraft），
 * 必须注册在 mixin 配置的 "client" 数组中，仅客户端加载，不可放入 "mixins" 双侧列表。
 */
@Mixin(ClientEvents.class)
public abstract class MixinClientEventsGameModeSwitch {

    /**
     * 绕过 FutureMC 客户端侧的权限等级检查：原逻辑是
     * {@code player.getPermissionLevel() >= 2} 才发送切换请求，这里恒定返回 2
     * （即始终放行，不做任何权限检查；是否允许由服务端指令权限体系决定）。
     */
    @Redirect(method = "onKeyInput(Lnet/minecraftforge/fml/common/gameevent/InputEvent$KeyInputEvent;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;getPermissionLevel()I"))
    private static int dollancustomized$skipPermissionCheck(EntityPlayerSP player) {
        return 2;
    }

    /**
     * 把发送 {@code GameModeSwitchPacket} 改为发送 {@code /gamemode <模式>} 聊天指令，
     * 与原版 F3+N（Minecraft 类中 sendChatMessage("/gamemode creative")）走同一条路径。
     */
    @Redirect(method = "onKeyInput(Lnet/minecraftforge/fml/common/gameevent/InputEvent$KeyInputEvent;)V",
            at = @At(value = "INVOKE", target = "Lthedarkcolour/futuremc/network/NetworkHandler;sendGameModeSwitch(Lnet/minecraft/world/GameType;)V"), require = 0)
    private static void dollancustomized$sendGameModeCommand(NetworkHandler networkHandler, GameType gameType) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player != null) {
            player.sendChatMessage("/gamemode " + gameType.name().toLowerCase());
        }
    }
}
