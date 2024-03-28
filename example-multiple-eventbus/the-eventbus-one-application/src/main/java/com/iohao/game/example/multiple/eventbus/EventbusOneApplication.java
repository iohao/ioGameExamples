/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
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
package com.iohao.game.example.multiple.eventbus;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.example.multiple.eventbus.broker.EventbusBrokerServer;
import com.iohao.game.example.multiple.eventbus.external.EventbusExternalServer;
import com.iohao.game.example.multiple.eventbus.user.UserLogicStartup;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@Slf4j
public class EventbusOneApplication {
    public static void main(String[] args) {

        // true 表示开启 traceId 特性
        IoGameGlobalConfig.openTraceId = true;

        int externalPort = ExternalGlobalConfig.externalPort;
        // 游戏对外服
        ExternalServer externalServer = new EventbusExternalServer()
                .createExternalServer(externalPort);

        // 游戏网关
        BrokerServer brokerServer = new EventbusBrokerServer()
                .createBrokerServer();

        // user 游戏逻辑服
        var userLogicStartup = new UserLogicStartup();
        List<AbstractBrokerClientStartup> logicServerList = List.of(userLogicStartup);

        new NettyRunOne()
                .setExternalServer(externalServer)
                .setBrokerServer(brokerServer)
                .setLogicServerList(logicServerList)
                .startup();

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/gmxz33
         */
    }
}
