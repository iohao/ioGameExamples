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
package com.iohao.game.spring.external;

import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.micro.PipelineContext;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.micro.WebSocketMicroBootstrapFlow;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 渔民小镇
 * @date 2022-07-09
 */
public class GameExternal {
    public static void main(String[] args) {
        // 对外开放的端口
        int externalPort = 10100;

        // 构建游戏对外服
        ExternalServer externalServerWebSocket = new GameExternal()
                .createExternalServer(externalPort);

        // 启动游戏对外服
        externalServerWebSocket.startup();
    }

    public ExternalServer createExternalServer(int externalPort) {
        return createExternalServer(externalPort, ExternalJoinEnum.WEBSOCKET);
    }

    public ExternalServer createExternalServer(int externalPort, ExternalJoinEnum joinEnum) {

        // true 表示开启 traceId 特性；https://www.yuque.com/iohao/game/zurusq
        IoGameGlobalConfig.openTraceId = true;

        // 路由访问权限控制；https://www.yuque.com/iohao/game/nap5y8p5fevhv99y
        extractedIgnore();

        // 游戏对外服 - 构建器；https://www.yuque.com/iohao/game/ea6geg
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(externalPort)
                // 连接方式；如果不设置，默认是 webSocket
                .externalJoinEnum(joinEnum)
                // Broker （游戏网关）的连接地址；如果不设置，默认也是这个配置
                .brokerAddress(new BrokerAddress("127.0.0.1", IoGameGlobalConfig.brokerPort));


        builder.setting().setMicroBootstrapFlow(new WebSocketMicroBootstrapFlow() {
            @Override
            public void pipelineFlow(PipelineContext pipelineContext) {
//                pipelineContext.addLast("logging", new LoggingHandler(LogLevel.INFO));

                super.pipelineFlow(pipelineContext);
            }
        });

        // 构建游戏对外服
        return builder.build();
    }

    private void extractedIgnore() {
        /*
         * 注意，权限相关验证配置在游戏对外服是正确的，因为是游戏对外服在控制访问验证
         * see https://www.yuque.com/iohao/game/tywkqv#qEvtB
         */
        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);
        /*
         * 由于 accessAuthenticationHook.verifyIdentity = true; 时，需要玩家登录才可以访问业务方法 （action）
         *
         * 在这可以配置一些忽略访问限制的路由。
         * 这里配置的路由，表示不登录也可以进行访问
         * 现在忽略的 3-1，是登录 action 的路由，所以当我们访问 3-1 路由时，可以不登录。
         * 忽略的路由可以添加多个。
         */
        // see HallCmdModule.cmd，HallCmdModule.loginVerify
        accessAuthenticationHook.addIgnoreAuthCmd(3, 1);
        accessAuthenticationHook.addIgnoreAuthCmd(3, 8);
        accessAuthenticationHook.addIgnoreAuthCmd(3);
        accessAuthenticationHook.addIgnoreAuthCmd(6);
    }
}
