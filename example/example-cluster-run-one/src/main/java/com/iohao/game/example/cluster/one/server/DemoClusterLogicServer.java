/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.game.example.cluster.one.server;

import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.example.cluster.one.action.DemoClusterAction;
import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.flow.internal.DebugInOut;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.common.kit.NetworkKit;

/**
 * demo 逻辑服
 *
 * @author 渔民小镇
 * @date 2022-05-15
 */
public class DemoClusterLogicServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        var config = new BarSkeletonBuilderParamConfig()
                // 扫描 action 类所在包
                .scanActionPackage(DemoClusterAction.class);

        // 业务框架构建器
        var builder = config.createBuilder();

        // 添加控制台输出插件
        builder.addInOut(new DebugInOut());

        return builder.build();
    }

    @Override
    public BrokerAddress createBrokerAddress() {
        String localIp = NetworkKit.LOCAL_IP;
        int brokerPort = IoGameGlobalConfig.brokerPort;
        return new BrokerAddress(localIp, brokerPort);
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("demo cluster 游戏逻辑服");
        return builder;
    }
}
