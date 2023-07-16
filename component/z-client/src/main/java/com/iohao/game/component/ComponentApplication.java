/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.component;

import com.iohao.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.game.action.skeleton.kit.LogicServerCreateKit;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.component.chat.ChatLogicServer;
import com.iohao.game.component.login.LoginLogicServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
public class ComponentApplication {
    public static void main(String[] args) {
        LogicServerCreateKit.removeInOut(DebugInOut.class);

        ChatLogicServer chatLogicServer = new ChatLogicServer();
        LoginLogicServer loginLogicServer = new LoginLogicServer();

        // 游戏逻辑服列表
        List<AbstractBrokerClientStartup> logicServers = List.of(
                // 登录
                loginLogicServer
                , // 聊天
                chatLogicServer
        );

        ExternalServer externalServer = DefaultExternalServer
                .newBuilder(ExternalGlobalConfig.externalPort)
                .build();

        new NettyRunOne()
                // 游戏对外服
                .setExternalServer(externalServer)
                // 游戏逻辑服列表
                .setLogicServerList(logicServers)
                // 启动游戏服务器
                .startup();
    }
}
