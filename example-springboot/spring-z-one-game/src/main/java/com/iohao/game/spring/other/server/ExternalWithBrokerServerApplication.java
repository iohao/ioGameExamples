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
package com.iohao.game.spring.other.server;

import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.client.external.config.ExternalGlobalConfig;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.simple.SimpleRunOne;
import com.iohao.game.spring.broker.GameBrokerBoot;
import com.iohao.game.spring.external.GameExternal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * 游戏对外服和游戏网关的启动
 *
 * @author 渔民小镇
 * @date 2022-09-05
 */
public class ExternalWithBrokerServerApplication {
    public static void main(String[] args) {


        // 对外开放的端口
        int externalPort = 10100;
        // 游戏对外服
        ExternalServer externalServer = new GameExternal().createExternalServer(externalPort);

        // broker （游戏网关）
        BrokerServer brokerServer = new GameBrokerBoot().createBrokerServer();

        // 多服单进程的方式部署（类似单体应用）
        new SimpleRunOne()
                // broker （游戏网关）
                .setBrokerServer(brokerServer)
                // 游戏对外服
                .setExternalServer(externalServer)
                // 启动 游戏对外服、游戏网关、游戏逻辑服
                .startup();

        // 先关闭访问验证
        ExternalGlobalConfig.accessAuthenticationHook.setVerifyIdentity(false);
    }
}
