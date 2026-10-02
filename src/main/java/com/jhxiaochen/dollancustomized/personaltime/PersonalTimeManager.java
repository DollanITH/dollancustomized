package com.jhxiaochen.dollancustomized.personaltime;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家独立时间管理器。
 *
 * 思路（参照 PersonalTime / Icohedron）：
 * 每个玩家可以拥有自己独立的时间而不会影响服务器世界时间。
 * 实现方式是在每个玩家的 NetworkManager 通道上挂一个出站拦截器，
 * 拦截发给该玩家的 {@link SPacketTimeUpdate} 时间同步包，按玩家自己的偏移改写世界时间。
 * 服务器世界本身的 time/totalTime 完全不受影响，只改客户端看到的时间。
 */
public final class PersonalTimeManager {

    private static final Logger LOGGER = LogManager.getLogger("DollanCustomized.PersonalTime");

    /** 管道中拦截器 handler 的名字，避免重复添加。 */
    private static final String HANDLER_NAME = "dollancustomized_personaltime";

    /** 玩家 UUID -> 时间偏移（tick）。0 表示与服务器同步。 */
    private final Map<UUID, Long> timeOffsets = new ConcurrentHashMap<>();

    private static final PersonalTimeManager INSTANCE = new PersonalTimeManager();

    private PersonalTimeManager() {
    }

    public static PersonalTimeManager instance() {
        return INSTANCE;
    }

    /**
     * 注册事件监听（登录时挂拦截器）。应在 preInit 时调用。
     */
    public static void register() {
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    public long getOffset(UUID playerId) {
        return timeOffsets.getOrDefault(playerId, 0L);
    }

    public void setOffset(UUID playerId, long offset) {
        timeOffsets.put(playerId, offset);
    }

    public void resetOffset(UUID playerId) {
        timeOffsets.put(playerId, 0L);
    }

    /**
     * 玩家登录进入世界后，把时间包拦截器挂到其网络通道上。
     * 由于玩家新登录时偏移默认为 0（不影响任何包），此处的延迟添加不会造成错误。
     */
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            attachHandler((EntityPlayerMP) event.player);
        }
    }

    /**
     * 在玩家通道上添加出站拦截器（线程安全地调度到 Netty 事件循环线程执行）。
     */
    private void attachHandler(EntityPlayerMP player) {
        try {
            NetworkManager netManager = player.connection.netManager;
            Channel channel = netManager.channel();
            if (channel == null || !channel.isOpen()) {
                return;
            }
            channel.eventLoop().execute(() -> {
                if (channel.pipeline().get(HANDLER_NAME) == null) {
                    channel.pipeline().addBefore(
                            "packet_handler", HANDLER_NAME, new TimeRewriteHandler(player.getUniqueID()));
                }
            });
        } catch (Exception e) {
            LOGGER.error("Failed to attach personal time handler to player {}", player.getName(), e);
        }
    }

    /**
     * 出站包拦截器：改写发给对应玩家的 {@link SPacketTimeUpdate}。
     * 运行在通道的 Netty 事件循环线程上。
     */
    private final class TimeRewriteHandler extends ChannelOutboundHandlerAdapter {

        private final UUID playerId;

        TimeRewriteHandler(UUID playerId) {
            this.playerId = playerId;
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            if (msg instanceof SPacketTimeUpdate) {
                SPacketTimeUpdate packet = (SPacketTimeUpdate) msg;
                long offset = getOffset(playerId);
                if (offset != 0L) {
                    long totalWorldTime = packet.getTotalWorldTime();
                    long worldTime = packet.getWorldTime();

                    // 若 doDaylightCycle 为 false，服务端会用负数 worldTime 表示"时间冻结"，
                    // 此时按偏移相减以保持冻结语义；正常情况则直接相加。
                    long personalWorldTime = worldTime < 0 ? worldTime - offset : worldTime + offset;

                    // 重建包并保持原 totalWorldTime，只改写玩家看到的 worldTime。
                    msg = new SPacketTimeUpdate(totalWorldTime, personalWorldTime, true);
                }
            }
            super.write(ctx, msg, promise);
        }
    }
}
