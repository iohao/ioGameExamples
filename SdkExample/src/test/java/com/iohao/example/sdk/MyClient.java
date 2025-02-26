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
package com.iohao.example.sdk;

import com.iohao.example.sdk.logic.action.SdkCmd;
import com.iohao.example.sdk.logic.data.LoginVerifyMessage;
import com.iohao.example.sdk.logic.data.UserMessage;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2025-01-23
 */
public final class MyClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new SdkInputCommandRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                // 连接 ip，默认不填也是这个值
                .setConnectAddress("127.0.0.1")
                .idle(5)
                .startup();
    }

    @Slf4j
    static class SdkInputCommandRegion extends AbstractInputCommandRegion {

        @Override
        public void initInputCommand() {
            extractedLogin();

            // 设置模拟请求登录域的主路由
            this.inputCommandCreate.cmd = SdkCmd.cmd;

            this.ofCommand(SdkCmd.loginVerify).setTitle("登录").setRequestData(() -> {
                LoginVerifyMessage message = new LoginVerifyMessage();
                message.jwt = "1";
                return message;
            }).callback(result -> {
                UserMessage message = result.getValue(UserMessage.class);
                this.clientUser.setUserId(message.userId);
                log.info("message : {}", message);
            });

            this.ofCommandInt(SdkCmd.intValue).setTitle("intValue").setRequestData(() -> {
                // int or IntValue.of(1)
                return 1;
            }).callback(result -> {
                log.info("{}", result.getInt());
            });

            this.ofCommandInt(SdkCmd.longValue).setTitle("longValue").setRequestData(() -> {
                // long or LongValue.of(1)
                return 1L;
            }).callback(result -> {
                log.info("{}", result.getLong());
            });

            this.ofCommandInt(SdkCmd.boolValue).setTitle("boolValue").setRequestData(() -> {
                // bool or BoolValue.of(true)
                return true;
            }).callback(result -> {
                log.info("{}", result.getBoolean());
            });

            this.ofCommandInt(SdkCmd.stringValue).setTitle("stringValue").setRequestData(() -> {
                // String or StringValue.of("abc")
                return "abc";
            }).callback(result -> {
                log.info("{}", result.getString());
            });

            this.ofCommandInt(SdkCmd.listInt).setTitle("listInt").setRequestData(() -> {
                // List<Integer> or IntValueList.of(List.of(1,2))
                return List.of(1, 2);
            }).callback(result -> {
                log.info("{}", result.listInt());
            });

            this.ofCommandInt(SdkCmd.listLong).setTitle("listLong").setRequestData(() -> {
                // List<Long> or LongValueList.of(List.of(1L,2L))
                return List.of(1L, 2L);
            }).callback(result -> {
                log.info("{}", result.listLong());
            });

            this.ofCommandInt(SdkCmd.listBool).setTitle("listBool").setRequestData(() -> {
                // List<Boolean> or BoolValueList.of(List.of(true, false))
                return List.of(true, false);
            }).callback(result -> {
                log.info("{}", result.listBoolean());
            });

            this.ofCommandInt(SdkCmd.listString).setTitle("listString").setRequestData(() -> {
                // List<String> or StringValueList.of(List.of("a", "b"))
                return List.of("a", "b");
            }).callback(result -> {
                log.info("{}", result.listString());
            });
        }

        private void extractedLogin() {
            TaskKit.runOnceSecond(() -> {
                // login
                this.executeCommand(SdkCmd.loginVerify);
            });

            TaskKit.runOnce(() -> {
                // test
                this.executeCommand(SdkCmd.intValue);
                this.executeCommand(SdkCmd.longValue);
                this.executeCommand(SdkCmd.boolValue);
                this.executeCommand(SdkCmd.stringValue);

                this.executeCommand(SdkCmd.listInt);
                this.executeCommand(SdkCmd.listLong);
                this.executeCommand(SdkCmd.listBool);
                this.executeCommand(SdkCmd.listString);

            }, 2, TimeUnit.SECONDS);
        }
    }
}


