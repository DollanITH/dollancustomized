package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    // 创建一个网络通道，通道名需要是小写字母，通常用 modid:subchannel 格式
    public static final SimpleNetworkWrapper DOLLAN_NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    // 用于记录包的 ID，每注册一个包 ID 递增
    private static int packetId = 0;

    public static void registerMessages() {
        // 注册我们的道路文字网络包
        // 参数依次为：处理类、消息类、包ID、处理端（客户端还是服务端）
        // 因为是 GUI 发送给服务端的，所以接收端是 Side.SERVER
        DOLLAN_NETWORK.registerMessage(MessageRoadText.Handler.class, MessageRoadText.class, packetId++, Side.SERVER);

        // 注册自定义限高路牌网络包
        DOLLAN_NETWORK.registerMessage(MessageHeightLimitSignCustom.Handler.class, MessageHeightLimitSignCustom.class, packetId++, Side.SERVER);

        // 如果以后你还需要服务端发给客户端的包，可以这样写：
        // INSTANCE.registerMessage(某Message.Handler.class, 某Message.class, packetId++, Side.CLIENT);
    }
}
