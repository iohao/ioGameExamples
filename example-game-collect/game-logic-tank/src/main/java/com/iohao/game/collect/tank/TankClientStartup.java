/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.tank;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.BrokerGlobalConfig;
import com.iohao.game.collect.common.GameBarSkeletonConfig;
import com.iohao.game.collect.common.GameCodeEnum;
import com.iohao.game.collect.tank.action.TankAction;
import com.iohao.game.collect.tank.config.TankKit;
import com.iohao.game.collect.tank.send.TankSend;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.common.kit.NetworkKit;

/**
 * @author 渔民小镇
 * @date 2022-01-14
 */
public class TankClientStartup extends AbstractBrokerClientStartup {

    public static void main(String[] args) {
        BrokerClientApplication.start(new TankClientStartup());
    }

    @Override
    public BarSkeleton createBarSkeleton() {

        BarSkeletonBuilderParamConfig config = new BarSkeletonBuilderParamConfig()
                // 扫描 TankAction.class 所在包
                .addActionController(TankAction.class)
                // 推送消息-用于文档的生成
                .addActionSend(TankSend.class)
                // 错误码-用于文档的生成
                .addErrorCode(GameCodeEnum.values());

        BarSkeletonBuilder builder = GameBarSkeletonConfig.createBuilder(config);

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("坦克逻辑服");
        return builder;
    }

    @Override
    public BrokerAddress createBrokerAddress() {
        // 类似 127.0.0.1 ，但这里是本机的 ip
        String localIp = NetworkKit.LOCAL_IP;
        // broker （游戏网关）默认端口
        int brokerPort = BrokerGlobalConfig.brokerPort;
        return new BrokerAddress(localIp, brokerPort);
    }

    @Override
    public void startupSuccess(BrokerClient brokerClient) {
        TankKit.brokerClient = brokerClient;
    }
}
