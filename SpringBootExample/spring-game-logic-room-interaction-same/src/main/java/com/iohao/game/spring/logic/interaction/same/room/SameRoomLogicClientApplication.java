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
        SameRoomLogic roomLogicClient1 = createRoomLogicClient(1);
        SameRoomLogic roomLogicClient2 = createRoomLogicClient(2);

        BrokerClientApplication.start(roomLogicClient1);
        BrokerClientApplication.start(roomLogicClient2);
    }

    public static SameRoomLogic createRoomLogicClient(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id(String.valueOf(id))
                // 逻辑服名字
                .appName("spring 房间的游戏逻辑服-" + id)
                // 同类型标签
                .tag("roomLogic");

        // 创建房间的游戏逻辑服
        SameRoomLogic sameRoomLogic = new SameRoomLogic();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        sameRoomLogic.setBrokerClientBuilder(brokerClientBuilder);
        return sameRoomLogic;
    }
}
