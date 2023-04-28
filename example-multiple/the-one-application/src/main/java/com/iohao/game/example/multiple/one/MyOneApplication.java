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
package com.iohao.game.example.multiple.one;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.example.multiple.broker.MyBrokerServer;
import com.iohao.game.example.multiple.external.MyExternalServer;
import com.iohao.game.example.multiple.weather.WeatherLogicStartup;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-03-30
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyOneApplication {
    public static void main(String[] args) {
        int externalPort = 10100;
        // 游戏对外服
        ExternalServer externalServer = new MyExternalServer()
                .createExternalServer(externalPort);

        // 游戏网关
        BrokerServer brokerServer = new MyBrokerServer().createBrokerServer();

        // 游戏逻辑服
        WeatherLogicStartup weatherLogicStartup = new WeatherLogicStartup();
        List<AbstractBrokerClientStartup> logicServerList = List.of(weatherLogicStartup);

        NettyRunOne simpleRunOne = new NettyRunOne()
                .setExternalServer(externalServer)
                .setBrokerServer(brokerServer)
                .setLogicServerList(logicServerList);

        simpleRunOne.startup();


    }
}
