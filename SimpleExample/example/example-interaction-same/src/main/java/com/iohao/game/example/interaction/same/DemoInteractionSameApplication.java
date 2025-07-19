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
package com.iohao.game.example.interaction.same;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.example.interaction.same.hall.DemoSameHallLogicServer;
import com.iohao.game.example.interaction.same.room.DemoSameRoomLogicServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;

import java.util.List;
import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2022-05-22
 */
public class DemoInteractionSameApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        IoGameGlobalConfig.openTraceId = true;

//        IoGameGlobalConfig.timeoutMillis = 15 * 1000;

        // 创建 3 个房间逻辑服
        DemoSameRoomLogicServer roomServer1 = createRoomServer(1);
        DemoSameRoomLogicServer roomServer2 = createRoomServer(2);
        DemoSameRoomLogicServer roomServer3 = createRoomServer(3);

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 大厅 - 逻辑服
                new DemoSameHallLogicServer(),
                // 3个房间逻辑服
                roomServer1,
                roomServer2,
                roomServer3
        );

        // 游戏对外服端口
        int port = ExternalGlobalConfig.externalPort;
        // 启动 对外服、网关服、逻辑服
        NettySimpleHelper.run(port, logicList);
    }

    private static DemoSameRoomLogicServer createRoomServer(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id(String.valueOf(id))
                // 逻辑服名字
                .appName("DemoSameRoomLogicServer-" + id)
                // 同类型
                .tag("DemoSameRoomLogicServer");

        // 创建房间逻辑服
        DemoSameRoomLogicServer roomLogicServer = new DemoSameRoomLogicServer();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        roomLogicServer.setBrokerClientBuilder(brokerClientBuilder);
        return roomLogicServer;
    }
}
