package com.iohao.game.example.cluster.one;

import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.common.kit.NetworkKit;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.netty.DefaultExternalServer;

import java.util.concurrent.TimeUnit;

/**
 * 游戏对外服
 *
 * @author 渔民小镇
 * @date 2022-05-16
 */
public class DemoClusterExternal {
    public static void main(String[] args) throws InterruptedException {
        DemoClusterConfig.defaultConfig();

        // 游戏对外服端口
        String localIp = NetworkKit.LOCAL_IP;

        int externalCorePort = 10100;
        // 创建游戏对外服构建器
        var builder = DefaultExternalServer
                // 游戏对外服端口；与真实玩家建立连接的端口
                .newBuilder(externalCorePort)
                // 连接方式 WebSocket；默认不填写也是这个值
                .externalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // 与 Broker （游戏网关）的连接地址 ；
                .brokerAddress(new BrokerAddress(localIp, IoGameGlobalConfig.brokerPort));

        // 构建、启动
        ExternalServer externalServer = builder.build();
        externalServer.startup();

        TimeUnit.SECONDS.sleep(1);

    }

}