/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.ws.verify;

import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.processor.listener.BrokerClientListener;
import com.iohao.game.bolt.broker.core.message.BrokerClientModuleMessage;
import com.iohao.game.example.ws.verify.external.MyWebSocketVerifyHandler;
import com.iohao.game.example.ws.verify.server.WsVerifyLogicServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.broker.client.ExternalBrokerClientStartup;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.handler.ws.WebSocketVerifyHandler;
import com.iohao.game.external.core.netty.micro.WebSocketMicroBootstrapFlow;
import com.iohao.game.external.core.netty.simple.NettyRunOne;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-08-04
 */
public class WsVerifyApplication {
    public static void main(String[] args) {

        // 游戏对外服列表
        var externalServer = createExternalServer();

        new NettyRunOne()
                .setExternalServer(externalServer)
                .setLogicServerList(List.of(new WsVerifyLogicServer()))
                .startup();
    }

    static ExternalServer createExternalServer() {
        int port = 10100;
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(port);

//        extractedBrokerClientListener(builder, port);

        // 设置 MicroBootstrapFlow 类，并重写 createVerifyHandler 方法
        builder.setting().setMicroBootstrapFlow(new WebSocketMicroBootstrapFlow() {
            @Override
            protected WebSocketVerifyHandler createVerifyHandler() {
                return new MyWebSocketVerifyHandler();
            }
        });

        return builder.build();
    }

    private static void extractedBrokerClientListener(DefaultExternalServerBuilder builder, int port) {
        /*
         * 自定义游戏对外服 ExternalBrokerClientStartup 部分
         * https://www.yuque.com/iohao/game/wotnhl#x7n94
         */
        builder.externalBrokerClientStartup(new ExternalBrokerClientStartup() {
            @Override
            public BrokerClientBuilder createBrokerClientBuilder() {
                // 使用原有游戏对外服的一些配置
                BrokerClientBuilder brokerClientBuilder = super.createBrokerClientBuilder();

                // 添加 BrokerClient 监听
                brokerClientBuilder.addListener(new BrokerClientListener() {
                    @Override
                    public void registerBefore(BrokerClientModuleMessage moduleMessage, BrokerClient client) {
                        // 设置一些自定义属性
                        moduleMessage.addHeader("externalPort", port);
                    }
                });

                return brokerClientBuilder;
            }
        });
    }
}
