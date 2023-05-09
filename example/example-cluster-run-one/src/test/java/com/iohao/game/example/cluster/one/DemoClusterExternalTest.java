package com.iohao.game.example.cluster.one;

import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.client.external.ExternalServerBuilder;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.common.kit.NetworkKit;

import java.util.concurrent.TimeUnit;

/**
 * 游戏对外服
 *
 * @author 渔民小镇
 * @date 2022-05-16
 */
public class DemoClusterExternalTest {
    public static void main(String[] args) throws InterruptedException {

        // 游戏对外服端口
        int port = 10100;
//        ExternalServer externalServer = SimpleHelper.createExternalServer(ExternalJoinEnum.WEBSOCKET, port);

        String localIp = NetworkKit.LOCAL_IP;
        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port)
                // Broker （游戏网关）的连接地址
                .brokerAddress(new BrokerAddress(localIp, IoGameGlobalConfig.brokerPort));

        ExternalServer externalServer = builder.build();

        externalServer.startup();

        TimeUnit.SECONDS.sleep(1);

    }

}