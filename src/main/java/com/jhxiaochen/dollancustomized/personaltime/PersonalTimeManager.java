package com.jhxiaochen.dollancustomized.personaltime;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.world.World;
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
 * 在每个玩家的 NetworkManager 通道上挂出站拦截器，拦截发给该玩家的
 * {@link SPacketTimeUpdate} 时间同步包，把时间改成"服务器时间 + 玩家偏移"。
 * 服务器世界本身的 time/totalTime 完全不受影响，只改客户端看到的时间。
 *
 * 注意：这里【不能】调用 {@link SPacketTimeUpdate} 的 getTotalWorldTime()/getWorldTime() getter——
 * 在 Cleanroom 运行时中，这些方法名在 searge 重映射下会指向不存在的方法（NoSuchMethodError）。
 * 因此改为直接从玩家当前世界读取时间（World.getTotalWorldTime/getWorldTime 在运行时已验证可用），
 * 再叠加偏移重建时间包。
 */
public final class PersonalTimeManager {

    private static final Logger LOGGER = LogManager.getLogger("DollanCustomized.PersonalTime");

    private static final String HANDLER_NAME = "dollancustomized_personaltime";

    private final Map<UUID, Long> timeOffsets = new ConcurrentHashMap<>();

    private static final PersonalTimeManager INSTANCE = new PersonalTimeManager();

    private PersonalTimeManager() {
    }

    public static PersonalTimeManager instance() {
        return INSTANCE;
    }

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

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            attachHandler((EntityPlayerMP) event.player);
        }
    }

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
                            "packet_handler", HANDLER_NAME, new TimeRewriteHandler(player));
                }
            });
        } catch (Exception e) {
            LOGGER.error("Failed to attach personal time handler to player {}", player.getName(), e);
        }
    }

    private static final class TimeRewriteHandler extends ChannelOutboundHandlerAdapter {

        private final UUID playerId;
        private final EntityPlayerMP player;

        TimeRewriteHandler(EntityPlayerMP player) {
            this.playerId = player.getUniqueID();
            this.player = player;
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            if (msg instanceof SPacketTimeUpdate) {
                long offset = PersonalTimeManager.instance().getOffset(playerId);
                if (offset != 0L) {
                    rewrite(ctx, msg, promise, offset);
                    return;
                }
            }
            super.write(ctx, msg, promise);
        }

        private void rewrite(ChannelHandlerContext ctx, Object msg, ChannelPromise promise, long offset) throws Exception {
            try {
                World world = player.getServerWorld();
                if (world != null) {
                    long totalWorldTime = world.getTotalWorldTime();
                    long worldTime = world.getWorldTime();
                    long personalWorldTime = worldTime + offset;
                    msg = new SPacketTimeUpdate(totalWorldTime, personalWorldTime, true);
                    super.write(ctx, msg, promise);
                    return;
                }
            } catch (Exception e) {
                LOGGER.error("Failed to rewrite personal time packet for player {}", player.getName(), e);
            }
            // 兜底：按原样放行，避免因改写失败断开连接
            super.write(ctx, msg, promise);
        }
    }
}
