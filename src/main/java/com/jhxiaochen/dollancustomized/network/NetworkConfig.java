package com.jhxiaochen.dollancustomized.network;

/**
 * 网络配置类
 * 用于管理网络包的调试和性能信息
 */
public class NetworkConfig {
    /**
     * 是否启用网络包调试日志
     */
    public static boolean ENABLE_DEBUG_LOG = true;

    /**
     * 最大字符串长度限制（防止恶意数据包过大）
     */
    public static int MAX_STRING_LENGTH = 1024;

    /**
     * 每次网络通信的最大重试次数
     */
    public static int MAX_RETRY_COUNT = 3;

    /**
     * 网络超时时间（毫秒）
     */
    public static int NETWORK_TIMEOUT = 5000;

    /**
     * 记录网络包发送信息
     */
    public static void logPacketSent(String packetType, String direction, int size) {
        if (ENABLE_DEBUG_LOG) {
            System.out.println("[Network] " + packetType + " sent " + direction +
                " (size: " + size + " bytes)");
        }
    }

    /**
     * 记录网络包接收信息
     */
    public static void logPacketReceived(String packetType, String direction) {
        if (ENABLE_DEBUG_LOG) {
            System.out.println("[Network] " + packetType + " received " + direction);
        }
    }
}