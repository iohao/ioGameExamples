/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.old.external;

import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.example.old.external.action.OldHello;
import com.iohao.game.external.core.message.ExternalMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟游戏客户端 websocket
 *
 * @author 渔民小镇
 * @date 2022-05-18
 */
@Slf4j
public class OldWebsocketClient {

    public static void main(String[] args) throws Exception {

        initCommand();

        // 启动客户端
        WebsocketClientKit.runClient();
    }

    private static void initCommand() {

        // 登录请求
        OldHello oldHello = new OldHello();
        oldHello.name = "卡莉丝塔";

        ExternalMessage message = ClientCommandKit.createExternalMessage(
                1,
                1,
                oldHello
        );

        ClientCommandKit.createClientCommand(message, OldHello.class);
    }


}
