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
package com.iohao.game.example.multiple.one;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.example.multiple.broker.MyBrokerServer;
import com.iohao.game.example.multiple.external.MyExternalServer;
import com.iohao.game.example.multiple.weather.WeatherLogicStartup;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettyRunOne;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-03-30
 */
public class MyOneApplication {
    public static void main(String[] args) {

        // 游戏对外服
        ExternalServer externalServer = new MyExternalServer()
                .createExternalServer(ExternalGlobalConfig.externalPort);

        // 游戏网关
        BrokerServer brokerServer = new MyBrokerServer()
                .createBrokerServer();

        // 游戏逻辑服
        WeatherLogicStartup weatherLogicStartup = new WeatherLogicStartup();
        List<AbstractBrokerClientStartup> logicServerList = List.of(weatherLogicStartup);

        new NettyRunOne()
                .setExternalServer(externalServer)
                .setBrokerServer(brokerServer)
                .setLogicServerList(logicServerList)
                .startup();
    }
}
