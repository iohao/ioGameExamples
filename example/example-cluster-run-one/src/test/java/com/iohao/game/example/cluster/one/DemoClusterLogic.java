package com.iohao.game.example.cluster.one;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.example.cluster.one.server.DemoClusterLogicServer;
import com.iohao.game.simple.cluster.ClusterSimpleRunOne;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 游戏逻辑服
 *
 * @author 渔民小镇
 * @date 2022-05-16
 */
public class DemoClusterLogic {
    public static void main(String[] args) throws InterruptedException {

        // 逻辑服
        var demoLogicServer = new DemoClusterLogicServer();
        List<AbstractBrokerClientStartup> logicList = List.of(demoLogicServer);

        // 简单的启动器
        new ClusterSimpleRunOne()
                // 禁用本地集群
                .disableBrokerServerCluster()
                // 逻辑服列表
                .setLogicServerList(logicList)
                // 启动 对外服、网关、逻辑服
                .startup();

        TimeUnit.MINUTES.sleep(30);
    }

}