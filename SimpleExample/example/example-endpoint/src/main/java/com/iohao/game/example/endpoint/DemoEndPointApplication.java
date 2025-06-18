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

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 匹配 - 逻辑服
                new DemoEndPointMatchServer(),
                // 2个房间逻辑服
                roomServer1, roomServer2
        );

        // 启动 对外服、网关服、逻辑服
        NettySimpleHelper.run(ExternalGlobalConfig.externalPort, logicList);
    }

    private static DemoEndPointRoomServer createRoomServer(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id("1-" + id)
                // 逻辑服名字
                .appName("RoomLogic-" + id)
                // 设置同类型。注意，这个 tag 很重要，表示同类型的游戏逻辑服。
                .tag("endPointRoomLogic");

        // 创建房间逻辑服
        DemoEndPointRoomServer roomLogicServer = new DemoEndPointRoomServer();
        // 手动指定房间逻辑服的信息。
        roomLogicServer.setBrokerClientBuilder(brokerClientBuilder);
        return roomLogicServer;
    }
}
