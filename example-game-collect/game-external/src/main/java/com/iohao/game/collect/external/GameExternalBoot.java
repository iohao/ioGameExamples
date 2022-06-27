/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.client.external.ExternalServerBuilder;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalJoinEnum;

/**
 * 游戏对外服
 *
 * @author 渔民小镇
 * @date 2022-01-12
 */
public class GameExternalBoot {

    public ExternalServer createExternalServer(int externalPort) {
        // 端口
        int port = externalPort;
        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port)
                // websocket 方式连接
                .externalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // 开启心跳
//                .enableIdle()
                ;

        // 构建游戏对外服
        return builder.build();
    }

    public ExternalServer createExternalServer() {
        return this.createExternalServer(GameConfig.externalPort);
    }

    public static void main(String[] args) {
        GameExternalBoot gameExternalBoot = new GameExternalBoot();

        int externalPort = GameConfig.externalPort;
        ExternalServer externalServer = gameExternalBoot.createExternalServer(externalPort);
        // 启动游戏对外服
        externalServer.startup();

        System.out.println("对外服务器启动 ok! ");
    }
}
