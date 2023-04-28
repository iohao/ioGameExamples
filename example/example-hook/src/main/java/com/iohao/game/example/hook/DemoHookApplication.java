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
package com.iohao.game.example.hook;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.example.hook.action.DemoCmdForHookRoom;
import com.iohao.game.example.hook.custom.DemoIdleHook;
import com.iohao.game.example.hook.custom.DemoUserHook;
import com.iohao.game.example.hook.server.DemoHookRoomServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.netty.NettyExternalCoreSetting;
import com.iohao.game.external.core.netty.NettyExternalServer;
import com.iohao.game.external.core.netty.NettyExternalServerBuilder;
import com.iohao.game.external.core.netty.simple.NettyRunOne;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
public class DemoHookApplication {
    public static void main(String[] args) {

        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);
        // 添加不需要登录也能访问的业务方法 (action)
        accessAuthenticationHook.addIgnoreAuthenticationCmd(DemoCmdForHookRoom.cmd, DemoCmdForHookRoom.loginVerify);

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 匹配 - 逻辑服
                new DemoHookRoomServer()
        );

        // 游戏对外服端口
        int port = 10100;
        ExternalServer externalServer = createExternalServer(port);

        // 简单的启动器
        new NettyRunOne()
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
                .setIdleHook(new DemoIdleHook());

        // 游戏对外服 - 构建器
        NettyExternalServerBuilder externalServerBuilder = NettyExternalServer.newBuilder(port);
        // ExternalCore 设置项
        NettyExternalCoreSetting setting = externalServerBuilder.setting();
        // 心跳相关
        setting.setIdleProcessSetting(idleProcessSetting)
                // 设置 自定义 用户上线、下线的钩子
                .setUserHook(new DemoUserHook());

        // 构建游戏对外服
        return externalServerBuilder.build();
    }
}
