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
package com.iohao.game.example.multiple.broker;

import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.bolt.broker.server.BrokerServerBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-03-30
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyBrokerServer {
    public BrokerServer createBrokerServer() {
        // broker （游戏网关）默认端口 10200
        return createBrokerServer(IoGameGlobalConfig.brokerPort);
    }

    public BrokerServer createBrokerServer(int port) {
        // Broker Server （游戏网关服） 构建器
        BrokerServerBuilder brokerServerBuilder = BrokerServer.newBuilder()
                // broker （游戏网关）端口
                .port(port);

        // 构建游戏网关
        return brokerServerBuilder.build();
    }

    public static void main(String[] args) throws InterruptedException {
        BrokerServer brokerServer = new MyBrokerServer().createBrokerServer();
        // 启动 游戏网关
        brokerServer.startup();

        TimeUnit.SECONDS.sleep(1);
    }
}
