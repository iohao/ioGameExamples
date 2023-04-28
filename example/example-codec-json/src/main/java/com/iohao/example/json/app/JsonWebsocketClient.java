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
package com.iohao.example.json.app;

import com.iohao.game.action.skeleton.core.IoGameGlobalSetting;
import com.iohao.game.action.skeleton.core.codec.JsonDataCodec;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.example.common.cmd.JsonCmd;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.external.core.message.ExternalMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-11-24
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonWebsocketClient {
    public static void main(String[] args) throws Exception {
        // 使用 json 编解码
        IoGameGlobalSetting.setDataCodec(new JsonDataCodec());

        // 请求构建
        initClientCommands();

        // 启动客户端
        WebsocketClientKit.runClient();
    }

    private static void initClientCommands() {
        int cmd = JsonCmd.cmd;

        HelloReq helloReq = new HelloReq();
        helloReq.name = "塔姆";

        // 请求、响应
        ExternalMessage externalMessageHere = ClientCommandKit.createExternalMessage(
                cmd,
                JsonCmd.hello,
                helloReq
        );

        ClientCommandKit.createClientCommand(externalMessageHere, HelloReq.class);


        JsonMsg jsonMsg = new JsonMsg();
        // 请求、响应
        externalMessageHere = ClientCommandKit.createExternalMessage(
                cmd,
                JsonCmd.jsonMsg,
                jsonMsg
        );

        ClientCommandKit.createClientCommand(externalMessageHere, JsonMsg.class);

    }
}
