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
package com.iohao.game.spring.logic.interaction.same.room;

import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;

/**
 * @author 渔民小镇
 * @date 2022-07-30
 */
public class SameRoomLogicClientApplication {
    public static void main(String[] args) {
        SameRoomLogicClient roomLogicClient1 = createRoomLogicClient(1);
        SameRoomLogicClient roomLogicClient2 = createRoomLogicClient(2);

        BrokerClientApplication.start(roomLogicClient1);
        BrokerClientApplication.start(roomLogicClient2);
    }

    public static SameRoomLogicClient createRoomLogicClient(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id(String.valueOf(id))
                // 逻辑服名字
                .appName("spring 房间的游戏逻辑服-" + id)
                // 同类型标签
                .tag("roomLogic");

        // 创建房间的游戏逻辑服
        SameRoomLogicClient sameRoomLogicClient = new SameRoomLogicClient();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        sameRoomLogicClient.setBrokerClientBuilder(brokerClientBuilder);
        return sameRoomLogicClient;
    }
}
