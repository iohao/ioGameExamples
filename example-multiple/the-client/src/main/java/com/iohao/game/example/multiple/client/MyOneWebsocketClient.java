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
package com.iohao.game.example.multiple.client;

import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.example.multiple.common.cmd.internal.WeatherCmd;
import com.iohao.game.example.multiple.common.data.TheLogin;
import com.iohao.game.example.multiple.common.data.TheUserInfo;
import com.iohao.game.example.multiple.common.data.Weather;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-03-30
 */
@Slf4j
public class MyOneWebsocketClient {

    public static void main(String[] args) throws Exception {

        TheLogin login = new TheLogin();
        login.jwt = "abc";

        var externalMessage = ClientCommandKit.createExternalMessage(
                // 路由, 对应服务端逻辑服的业务类路由地址
                WeatherCmd.cmd,
                WeatherCmd.login,
                login
        );

        ClientCommandKit.createClientCommand(externalMessage, TheUserInfo.class, 1500);


        Weather weather = new Weather();
        weather.name = "阿德拉";

        externalMessage = ClientCommandKit.createExternalMessage(
                // 路由, 对应服务端逻辑服的业务类路由地址
                WeatherCmd.cmd,
                WeatherCmd.hello,
                weather
        );

        ClientCommandKit.createClientCommand(externalMessage, Weather.class);

        // 启动客户端
        WebsocketClientKit.runClient();
    }
}
