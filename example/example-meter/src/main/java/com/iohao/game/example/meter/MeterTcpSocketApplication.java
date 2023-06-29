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
package com.iohao.game.example.meter;


import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.example.meter.server.MeterAction;
import com.iohao.game.example.meter.server.MeterExternalBizHandler;
import com.iohao.game.example.meter.server.MeterLogicServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.micro.PipelineContext;
import com.iohao.game.external.core.netty.DefaultExternalCoreSetting;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.handler.SocketUserSessionHandler;
import com.iohao.game.external.core.netty.micro.TcpMicroBootstrapFlow;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2022-10-02
 */
@Slf4j
public class MeterTcpSocketApplication {
    public static void main(String[] args) {

        ExternalServer externalServer = getExternalServer();

        // 简单的启动器
        new NettyRunOne()
                // 游戏对外服
                .setExternalServer(externalServer)
                // 游戏逻辑服列表
                .setLogicServerList(List.of(new MeterLogicServer()))
                // 启动 游戏对外服、游戏网关服、游戏逻辑服
                .startup();

        scheduled();
    }

    private static void scheduled() {
        ExecutorKit.newSingleScheduled("meter")
                .scheduleAtFixedRate(() -> {
                    System.out.println();
                    log.info("MeterAction.longAdder : {}", MeterAction.longAdder);
                    log.info("MyExternalBizHandler.userIdAdder : {}", MeterExternalBizHandler.userIdAdder);

//                    long countOnline = UserSessions.me().countOnline();
//                    log.info("countOnline : {}", countOnline);
                }, 1, 5, TimeUnit.SECONDS);
    }

    private static ExternalServer getExternalServer() {
        // 游戏对外服端口
        int port = 10100;

        // 需要登录才能访问业务方法
        ExternalGlobalConfig.accessAuthenticationHook.setVerifyIdentity(true);

        DefaultExternalServerBuilder builder = DefaultExternalServer
                .newBuilder(port)
                .externalJoinEnum(ExternalJoinEnum.TCP);

        DefaultExternalCoreSetting setting = builder.setting();

        // 给服务器做一些业务编排
        setting.setMicroBootstrapFlow(new TcpMicroBootstrapFlow() {
            @Override
            public void pipelineCustom(PipelineContext context) {

                // 管理 UserSession 的 Handler
                context.addLast("UserSessionHandler", SocketUserSessionHandler.me());

                context.addLast("MyExternalBizHandler", MeterExternalBizHandler.me());
            }
        });

        return builder.build();
    }
}
