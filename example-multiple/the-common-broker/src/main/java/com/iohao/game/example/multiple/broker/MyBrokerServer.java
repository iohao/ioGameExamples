/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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
