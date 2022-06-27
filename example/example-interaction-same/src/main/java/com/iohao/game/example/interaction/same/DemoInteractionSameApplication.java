/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.interaction.same;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.client.external.config.ExternalGlobalConfig;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.example.interaction.same.hall.DemoSameHallLogicServer;
import com.iohao.game.example.interaction.same.room.DemoSameRoomLogicServer;
import com.iohao.game.simple.SimpleHelper;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-05-22
 */
public class DemoInteractionSameApplication {
    public static void main(String[] args) {
        // 注意，这个是临时测试用的，设置为 false 表示不用登录就可以访问逻辑服的方法
        ExternalGlobalConfig.verifyIdentity = false;

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
        int port = 10100;
        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        SimpleHelper.run(port, logicList);

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/rf9rb9
         */
    }

    private static DemoSameRoomLogicServer createRoomServer(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id(String.valueOf(id))
                // 逻辑服名字
                .appName("demo房间逻辑服-" + id)
                // 同类型
                .tag("roomLogic");

        // 创建房间逻辑服
        DemoSameRoomLogicServer roomLogicServer = new DemoSameRoomLogicServer();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        roomLogicServer.setBrokerClientBuilder(brokerClientBuilder);
        return roomLogicServer;
    }
}
