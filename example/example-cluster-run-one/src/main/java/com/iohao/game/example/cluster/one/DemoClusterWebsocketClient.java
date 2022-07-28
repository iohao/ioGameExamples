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
package com.iohao.game.example.cluster.one;

import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.example.cluster.one.action.DemoClusterCmd;
import com.iohao.game.example.common.msg.HelloReq;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟游戏客户端 websocket
 *
 * @author 渔民小镇
 * @date 2022-05-15
 */
@Slf4j
public class DemoClusterWebsocketClient {


    public static void main(String[] args) throws Exception {

        HelloReq helloReq = new HelloReq();
        helloReq.setName("塔姆");
        ExternalMessage externalMessageHere = ClientCommandKit.createExternalMessage(
                DemoClusterCmd.cmd,
                DemoClusterCmd.here,
                helloReq
        );

        ClientCommandKit.createClientCommand(externalMessageHere, HelloReq.class);

        // 启动客户端
        WebsocketClientKit.runClient();
    }
}
