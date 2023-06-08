/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.endpoint.room;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.command.BarSkeletonKit;
import com.iohao.game.common.kit.NetworkKit;
import com.iohao.game.example.endpoint.room.action.DemoEndPointRoomAction;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
public class DemoEndPointRoomServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        DebugInOut debugInOut = new DebugInOut();
        debugInOut.setPrintConsumer((msg, flowContext) -> {
            String tag = flowContext.option(FlowAttr.logicServerTag);
            String logicServerId = flowContext.option(FlowAttr.logicServerId);
            log.info("{}-{}", tag, logicServerId);
        });

        return BarSkeletonKit
                .newBuilder(DemoEndPointRoomAction.class)
                .addInOut(debugInOut)
                .build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        // 因为要在一个进程中启动多个相同的逻辑服
        // 不在这里设置房间逻辑服的信息了，在启动类中设置
        return null;
    }

    @Override
    public BrokerAddress createBrokerAddress() {
        // 类似 127.0.0.1 ，但这里是本机的 ip
        String localIp = NetworkKit.LOCAL_IP;
        // broker （游戏网关）默认端口
        int brokerPort = IoGameGlobalConfig.brokerPort;
        return new BrokerAddress(localIp, brokerPort);
    }
}
