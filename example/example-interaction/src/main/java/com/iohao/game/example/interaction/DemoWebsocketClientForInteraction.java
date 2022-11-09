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
package com.iohao.game.example.interaction;

import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.example.interaction.fight.action.DemoCmdForFight;
import com.iohao.game.example.interaction.msg.DemoFightMsg;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

/**
 * 模拟游戏客户端
 *
 * @author 渔民小镇
 * @date 2022-03-24
 */
@Slf4j
public class DemoWebsocketClientForInteraction {

    public static void main(String[] args) throws Exception {

//        initClientCommands();

        // 启动客户端
        WebSocketClient webSocketClient = WebsocketClientKit.runClient();

        ExternalMessage externalMessage = ClientCommandKit.createExternalMessage(
                DemoCmdForFight.cmd,
                DemoCmdForFight.fight
        );

        byte[] bytes = ProtoKit.toBytes(externalMessage);
        for (int i = 0; i < 50; i++) {
            webSocketClient.send(bytes);
        }

    }


    private static void initClientCommands() {
        // 请求、响应
        ExternalMessage externalMessageHere = ClientCommandKit.createExternalMessage(
                DemoCmdForFight.cmd,
                DemoCmdForFight.fight
        );

        ClientCommandKit.createClientCommand(externalMessageHere, DemoFightMsg.class);

    }

}
