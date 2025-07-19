/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.game.example.multiple.client;

import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.multiple.common.cmd.internal.WeatherCmd;
import com.iohao.game.example.multiple.common.data.TheLogin;
import com.iohao.game.example.multiple.common.data.TheUserInfo;
import com.iohao.game.example.multiple.common.data.Weather;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class MyOneClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new InternalRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                .setConnectAddress("")
                .startup();
    }

    static class InternalRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = WeatherCmd.cmd;

            ofCommand(WeatherCmd.login).setTitle("login").setRequestData(() -> {
                TheLogin login = new TheLogin();
                login.jwt = "abc";
                return login;
            }).callback(result -> {
                TheUserInfo value = result.getValue(TheUserInfo.class);
                log.info("value : {}", value);
                ofRequestCommand(WeatherCmd.hello).execute();
            });

            ofCommand(WeatherCmd.hello).setTitle("hello").setRequestData(() -> {
                Weather weather = new Weather();
                weather.name = "阿德拉";
                return weather;
            }).callback(result -> {
                Weather value = result.getValue(Weather.class);
                log.info("value : {}", value);
            });

            // 一秒后，执行模拟请求;
            TaskKit.runOnceSecond(() -> {
                // 执行请求
                ofRequestCommand(WeatherCmd.login).execute();
            });

            TaskKit.runInterval(() -> {
                // execute
                ofRequestCommand(WeatherCmd.hello).execute();
            }, 5, TimeUnit.SECONDS);
        }
    }

}
