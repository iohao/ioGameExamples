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
package com.iohao.game.example.hook;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.client.external.bootstrap.heart.IdleProcessSetting;
import com.iohao.game.bolt.broker.client.external.session.UserSessions;
import com.iohao.game.example.hook.custom.DemoIdleHook;
import com.iohao.game.example.hook.custom.DemoUserHook;
import com.iohao.game.example.hook.server.DemoHookRoomServer;
import com.iohao.game.simple.SimpleRunOne;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
public class DemoHookApplication {
    public static void main(String[] args) {
        // 设置 自定义 用户上线、下线的钩子
        UserSessions.me().setUserHook(new DemoUserHook());

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 匹配 - 逻辑服
                new DemoHookRoomServer()
        );

        // 游戏对外服端口
        int port = 10100;
        ExternalServer externalServer = createExternalServer(port);

        // 简单的启动器
        new SimpleRunOne()
                // 对外服
                .setExternalServer(externalServer)
                // 逻辑服列表
                .setLogicServerList(logicList)
                // 启动 对外服、网关、逻辑服
                .startup();

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/lxqbnb
         */
    }

    private static ExternalServer createExternalServer(int port) {
        // 心跳相关设置
        IdleProcessSetting idleProcessSetting = new IdleProcessSetting()
                // 设置 自定义心跳钩子事件回调
                .idleHook(new DemoIdleHook());

        // 游戏对外服 - 构建器，设置并构建
        return ExternalServer.newBuilder(port)
                // 开启心跳机制
                .enableIdle(idleProcessSetting)
                // 构建对外服
                .build();
    }
}
