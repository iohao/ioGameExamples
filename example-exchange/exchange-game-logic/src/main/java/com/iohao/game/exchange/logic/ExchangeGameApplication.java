/*
 * ioGame
 * Copyright (C) 2021 - 2024  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
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
package com.iohao.game.exchange.logic;

import com.iohao.game.exchange.common.ExchangeCmd;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.simple.NettyRunOne;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
public class ExchangeGameApplication {
    public static void main(String[] args) {
        // 游戏逻辑服对外开放的端口
        int externalPort = 10100;
        // 游戏对外服
        ExternalServer externalServer = createExternalServer(externalPort);

        // 多服单进程的方式部署（类似单体应用）
        new NettyRunOne()
                // 游戏对外服
                .setExternalServer(externalServer)
                // 游戏逻辑服列表
                .setLogicServerList(List.of(new ExchangeGameLogicServer()))
                // 启动 游戏对外服、游戏网关、游戏逻辑服
                .startup();
    }

    public static ExternalServer createExternalServer(int externalPort) {
        // 路由访问权限的控制；https://www.yuque.com/iohao/game/nap5y8p5fevhv99y
        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);
        accessAuthenticationHook.addIgnoreAuthCmd(ExchangeCmd.cmd, ExchangeCmd.loginVerify);

        // 拒绝玩家访问权限的控制 https://www.yuque.com/iohao/game/nap5y8p5fevhv99y#f1LhQ
        accessAuthenticationHook.addRejectionCmd(ExchangeCmd.cmd, ExchangeCmd.recharge);
        accessAuthenticationHook.addRejectionCmd(ExchangeCmd.cmd, ExchangeCmd.notice);

        // 游戏对外服 - 构建器；https://www.yuque.com/iohao/game/ea6geg
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(externalPort);
        // 构建游戏对外服
        return builder.build();
    }
}
