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
package com.iohao.game.example.hook;

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.example.hook.action.DemoCmdForHookRoom;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.message.ExternalMessageCmdCode;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

import java.util.concurrent.TimeUnit;

/**
 * 模拟游戏客户端
 *
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
public class DemoWebsocketClientForHook {

    public static void main(String[] args) throws Exception {

        initLoginCommand();

        // 启动客户端
        WebSocketClient webSocketClient = WebsocketClientKit.runClient();

        ExecutorKit.newSingleScheduled("idle").scheduleAtFixedRate(() -> {
            ExternalMessage externalMessage = new ExternalMessage();
            externalMessage.setCmdCode(ExternalMessageCmdCode.idle);
            log.info("idle : ");
            webSocketClient.send(DataCodecKit.encode(externalMessage));
        }, 1, 3, TimeUnit.SECONDS);
    }

    private static void initLoginCommand() {

        // 登录请求
        DemoLoginVerify loginVerify = new DemoLoginVerify();
        loginVerify.jwt = "abc";

        ExternalMessage externalMessageLogin = ClientCommandKit.createExternalMessage(
                DemoCmdForHookRoom.cmd,
                DemoCmdForHookRoom.loginVerify,
                loginVerify
        );

        ClientCommandKit.createClientCommand(externalMessageLogin, DemoUserInfo.class, 1500);



    }
}
