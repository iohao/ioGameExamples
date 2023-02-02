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
package com.iohao.example.jsr.javax;

import com.iohao.example.jsr.javax.pb.JsrJavaxPb;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.example.common.cmd.JsrJavaxCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-28
 */
@Slf4j
public class JsrJavaxWebsocketClient {
    public static void main(String[] args) throws Exception {
        // 登录请求
        JsrJavaxPb jsrJakartaPb = new JsrJavaxPb();
        jsrJakartaPb.email = "shenjkjavax.com";

        log.info("JsrJavaxPb : {}", jsrJakartaPb);

        ExternalMessage externalMessage = ClientCommandKit.createExternalMessage(
                JsrJavaxCmd.cmd,
                JsrJavaxCmd.jsrJavax,
                jsrJakartaPb
        );

        ClientCommandKit.createClientCommand(externalMessage, JsrJavaxPb.class);

        // 启动客户端
        WebsocketClientKit.runClient();
    }
}
