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
package com.iohao.game.example.hook;

import com.iohao.game.example.hook.action.DemoCmdForHookRoom;
import com.iohao.game.example.hook.custom.DemoIdleHook;
import com.iohao.game.example.hook.custom.DemoUserHook;
import com.iohao.game.example.hook.server.DemoHookRoomServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
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
        accessAuthenticationHook.addIgnoreAuthCmd(DemoCmdForHookRoom.cmd, DemoCmdForHookRoom.loginVerify);

        // 游戏对外服端口
        int port = ExternalGlobalConfig.externalPort;
        ExternalServer externalServer = createExternalServer(port);

        // 简单的启动器
        new NettyRunOne()
                // 游戏对外服
                .setExternalServer(externalServer)
                // 游戏逻辑服列表
                .setLogicServerList(List.of(new DemoHookRoomServer()))
                // 启动 对外服、网关、逻辑服
                .startup();

        /*
         * 该示例文档地址
         * https://iohao.github.io/game/docs/external/idle
         * https://iohao.github.io/game/docs/external/user_hook
         */
    }

    private static ExternalServer createExternalServer(int port) {
        // 游戏对外服 - 构建器
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(port);
        // ExternalCore 设置项
        var setting = builder.setting();

        // 设置 自定义 用户上线、下线的钩子
        setting.setUserHook(new DemoUserHook());

        // 心跳相关
        IdleProcessSetting idleProcessSetting = new IdleProcessSetting()
                // 心跳整体时间为 5 秒
                .setIdleTime(5)
                // 设置 自定义心跳钩子事件回调
                .setIdleHook(new DemoIdleHook());
        setting.setIdleProcessSetting(idleProcessSetting);

        // 构建游戏对外服
        return builder.build();
    }
}
