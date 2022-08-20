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
package com.iohao.game.example.interaction.same;

import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.example.interaction.same.hall.action.DemoCmdForHall;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟游戏客户端
 *
 * @author 渔民小镇
 * @date 2022-05-22
 */
@Slf4j
public class DemoWebsocketClientForInteractionSame {

    public static void main(String[] args) throws Exception {

        initClientCommands();

        // 启动客户端
        WebsocketClientKit.runClient();
    }

    private static void initClientCommands() {

        // 请求、响应
        ExternalMessage externalMessageHere = ClientCommandKit.createExternalMessage(
                DemoCmdForHall.cmd,
                DemoCmdForHall.count
        );

        ClientCommandKit.createClientCommand(externalMessageHere);


        // 请求、响应
//        ExternalMessage externalMessageTest = ClientCommandKit.createExternalMessage(
//                DemoCmdForHall.cmd,
//                DemoCmdForHall.testCount
//        );

//        log.info("externalMessageTest : ");
//
//        ClientCommandKit.createClientCommand(externalMessageTest, IntPb.class);
    }
}
