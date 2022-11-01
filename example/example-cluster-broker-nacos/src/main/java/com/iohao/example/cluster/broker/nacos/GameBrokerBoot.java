/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.example.cluster.broker.nacos;


import com.iohao.game.bolt.broker.cluster.BrokerCluster;
import com.iohao.game.bolt.broker.cluster.BrokerClusterManagerBuilder;
import com.iohao.game.bolt.broker.core.common.BrokerGlobalConfig;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.bolt.broker.server.BrokerServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 游戏代理启动
 *
 * @author fangwei
 * @date 2022/09/21
 */
@Slf4j
@Component
public class GameBrokerBoot {

    /**
     * 创建代理服务器
     */
    public BrokerServer createBrokerServer(List<String> ips) {
        final List<String> seedAddress = ips.stream().map(s -> s + ":" + BrokerGlobalConfig.gossipListenPort).collect(Collectors.toList());
        int gossipListenPort = BrokerGlobalConfig.gossipListenPort;
        int port = BrokerGlobalConfig.brokerPort;
        return createBrokerServer(seedAddress, gossipListenPort, port);
    }

    /**
     * 创建代理服务器
     *
     * @param seedAddress      种子地址
     * @param gossipListenPort 八卦端口
     * @param port             端口
     */
    private BrokerServer createBrokerServer(List<String> seedAddress, int gossipListenPort, int port) {
        BrokerServer brokerServer = createBrokerServerForCluster(seedAddress, gossipListenPort, port);
        return brokerServer;
    }

    /**
     * 创建代理服务器集群
     *
     * @param seedAddress      种子地址
     * @param gossipListenPort 八卦端口
     * @param port             端口
     * @return {@link BrokerServer}
     */
    private BrokerServer createBrokerServerForCluster(
            List<String> seedAddress, int gossipListenPort, int port) {

        BrokerClusterManagerBuilder brokerClusterManagerBuilder =
                BrokerCluster.newBrokerClusterManagerBuilder()
                        .gossipListenPort(gossipListenPort)
                        .seedAddress(seedAddress);

        BrokerServerBuilder brokerServerBuilder =
                BrokerServer.newBuilder()
                        // broker 端口（游戏网关端口）
                        .port(port)
                        // 集群的管理构建器，如果不设置，表示不需要集群
                        .brokerClusterManagerBuilder(brokerClusterManagerBuilder);

        // Bolt Broker Server （游戏网关）
        return brokerServerBuilder.build();
    }

}
