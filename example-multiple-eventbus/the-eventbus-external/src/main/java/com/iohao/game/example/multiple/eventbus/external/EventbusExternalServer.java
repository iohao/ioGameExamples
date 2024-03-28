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
package com.iohao.game.example.multiple.eventbus.external;

import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
public class EventbusExternalServer {
    public ExternalServer createExternalServer(int externalPort) {

        // 游戏对外服 - 构建器
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(externalPort)
                // websocket 方式连接；如果不设置，默认也是这个配置
                .externalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // Broker （游戏网关）的连接地址；如果不设置，默认也是这个配置
                .brokerAddress(new BrokerAddress("127.0.0.1", IoGameGlobalConfig.brokerPort));

        // 构建游戏对外服
        return builder.build();
    }

    public static void main(String[] args) {
        IoGameGlobalConfig.openTraceId = true;

        // 游戏对外服端口
        ExternalServer externalServer = new EventbusExternalServer()
                .createExternalServer(ExternalGlobalConfig.externalPort);

        externalServer.startup();

        // 游戏网关
//        new NettyRunOne()
//                .setExternalServer(externalServer)
//                .startup();
    }

}
