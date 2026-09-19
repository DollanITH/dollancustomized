package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.config.DollanConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.world.GameType;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, priority = 1001)
public class MixinMinecraft {

    @Shadow
    public EntityPlayerSP player;

    /** 记录进入旁观模式前的游戏模式ID（0=生存 1=创造 2=冒险 3=旁观），-1 表示未记录 */
    @Unique
    private int previousGameModeId = -1;

    @Redirect(method = "processKeyF3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;canUseCommand(ILjava/lang/String;)Z"), require = 0)
    public boolean processKeyF3(EntityPlayerSP instance, int permLevel, String commandName) {
        return true;
    }

    @Inject(method = "processKeyF3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCreative()Z"), cancellable = true)
    public void processKeyF3(int auxKey, CallbackInfoReturnable<Boolean> cir) {
        if (!DollanConfig.CLIENT_CONFIG.ENABLE_ENHANCED_F3N) {
            return;
        }

        if (auxKey != 49) {
            cir.cancel();
        }

        // 用 playerController 里记录的客户端权威游戏模式判断当前状态
        PlayerControllerMP controller = Minecraft.getMinecraft().playerController;
        int currentModeId = controller != null ? controller.getCurrentGameType().getID() : 0;

        if (currentModeId == 3 || player.isSpectator()) {
            // 当前处于旁观模式 -> 切回进入旁观前的游戏模式
            // 若没记录过（比如用命令/死亡进的旁观），则默认回生存
            if (previousGameModeId == -1) {
                return;
            }
            int restoreModeId = Math.max(previousGameModeId, 0);
            GameType type = GameType.getByID(restoreModeId);
            player.sendChatMessage("/gamemode " + type.getName());
            previousGameModeId = 1;
            cir.setReturnValue(true);
        } else {
            // 其它模式（生存/创造/冒险）-> 记住当前模式，再切到旁观
            previousGameModeId = currentModeId;
            player.sendChatMessage("/gamemode spectator");
            cir.setReturnValue(true);
        }
    }

    @Redirect(
            method = "createDisplay",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V")
    )
    private void dollan$setTitle(String title) {
        Display.setTitle("DollanITH Client V" + Reference.VERSION);
    }

}
