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
package com.iohao.game.example.endpoint;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.example.endpoint.animal.AnimalLogicServer;
import com.iohao.game.example.endpoint.match.DemoEndPointMatchServer;
import com.iohao.game.example.endpoint.match.action.DemoCmdForEndPointMatch;
import com.iohao.game.example.endpoint.room.DemoEndPointRoomServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;

import java.util.List;

/**
 * 动态绑定逻辑服节点
 *
 * @author 渔民小镇
 * @date 2022-05-28
 */
public class DemoEndPointApplication {
    public static void main(String[] args) {

        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);
        // 添加不需要登录也能访问的业务方法 (action)
        accessAuthenticationHook.addIgnoreAuthCmd(DemoCmdForEndPointMatch.cmd, DemoCmdForEndPointMatch.loginVerify);

        // 创建 2 个房间逻辑服
        DemoEndPointRoomServer roomServer1 = createRoomServer(1);
        DemoEndPointRoomServer roomServer2 = createRoomServer(2);

        // 创建 2 个动物逻辑服
        AnimalLogicServer animalServer1 = createAnimalServer(1);
        AnimalLogicServer animalServer2 = createAnimalServer(2);

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 匹配 - 逻辑服
                new DemoEndPointMatchServer(),
                // 2个房间逻辑服
                roomServer1,
                roomServer2,
                // 2个动物逻辑服
                animalServer1,
                animalServer2
        );

        // 游戏对外服端口
        int port = 10100;
        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        NettySimpleHelper.run(port, logicList);

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/idl1wm
         */
    }

    private static DemoEndPointRoomServer createRoomServer(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id("1-" + id)
                // 逻辑服名字
                .appName("demo象棋房间逻辑服-" + id)
                // 同类型
                .tag("endPointRoomLogic");

        // 创建房间逻辑服
        DemoEndPointRoomServer roomLogicServer = new DemoEndPointRoomServer();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        roomLogicServer.setBrokerClientBuilder(brokerClientBuilder);
        return roomLogicServer;
    }

    private static AnimalLogicServer createAnimalServer(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id("2-" + id)
                // 逻辑服名字
                .appName("动物逻辑服-" + id)
                // 同类型
                .tag("animal");

        // 创建房间逻辑服
        var logicServer = new AnimalLogicServer();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        logicServer.setBrokerClientBuilder(brokerClientBuilder);
        return logicServer;
    }
}
