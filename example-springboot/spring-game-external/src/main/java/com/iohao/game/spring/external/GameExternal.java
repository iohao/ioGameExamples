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
package com.iohao.game.spring.external;

import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.client.external.ExternalServerBuilder;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalJoinEnum;
import com.iohao.game.bolt.broker.client.external.config.ExternalGlobalConfig;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.BrokerGlobalConfig;

/**
 * @author 渔民小镇
 * @date 2022-07-09
 */
public class GameExternal {
    public ExternalServer createExternalServer(int externalPort) {

        extractedIgnore();

        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(externalPort)
                // websocket 方式连接
                .externalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // Broker （游戏网关）的连接地址；如果不设置，默认也是这个配置
                .brokerAddress(new BrokerAddress("127.0.0.1", BrokerGlobalConfig.brokerPort));

        // 构建游戏对外服
        return builder.build();
    }

    private void extractedIgnore() {
        /*
         * 注意，这个配置这里是错误的，实际上是需要配置在对外服的，因为是对外服在控制访问验证
         * 但为了方便演示，就配置在这里了；
         * 因为这个综合示例登录与
         */
        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);
        /*
         * 由于 accessAuthenticationHook.verifyIdentity = true; 时，需要玩家登录才可以访问业务方法 （action）
         *
         * 这里可以配置一些忽略访问限制的路由
         * 这里配置的路由，表示不登录也可以进行访问
         */
        // see SpringCmdModule.HallCmd.cmd，SpringCmdModule.HallCmd.loginVerify
        accessAuthenticationHook.addIgnoreAuthenticationCmd(3, 1);
    }
}
