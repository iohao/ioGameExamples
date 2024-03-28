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
package com.iohao.game.example.multiple.eventbus.client;

import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.multiple.eventbus.common.EmailCmd;
import com.iohao.game.example.multiple.eventbus.common.TheBusLogin;
import com.iohao.game.example.multiple.eventbus.common.UserCmd;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@Slf4j
public class EventbusOneClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new UserRegion()
                , new EmailRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                // 连接 ip，默认不填也是这个值
                .setConnectAddress("127.0.0.1")
                .startup();
    }

    static class UserRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = UserCmd.cmd;

            // 一秒后，执行模拟请求;
            TaskKit.runOnceMillis(() -> {
                // 执行请求
                ofRequestCommand(UserCmd.login).execute();
            }, 1);

            ofCommand(UserCmd.login).setTitle("login").setRequestData(() -> {
                var login = new TheBusLogin();
                login.jwt = "abc";
                return login;
            }).callback(result -> {
                var value = result.getValue(TheBusLogin.class);
                log.info("value : {}", value);

                ofRequestCommand(UserCmd.fireEvent).execute();
            });

            ofCommand(UserCmd.fireEvent).setTitle("fireEvent").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });

            ofCommand(UserCmd.fireEventEmpty).setTitle("fireEventEmpty 测试空订阅者").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });

            ofCommand(UserCmd.fireAnyMail).setTitle("fireAnyMail 向其中一个 email 逻辑服发布事件（启动两个 email 实例）").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });

            ofCommand(UserCmd.fireSyncMail).setTitle("fireSyncMail 向两个 email 逻辑服发布事件（启动两个 email 实例）").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });
        }
    }

    static class EmailRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = EmailCmd.cmd;

            ofCommand(EmailCmd.fireEventMethod).setTitle("fireEventMethod 测试执行器策略选择，使用 methodExecutor 策略").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });

            ofCommand(EmailCmd.fireEventOrder).setTitle("fireEventOrder 测试订阅者的执行顺序（优先级）").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });
        }
    }

}
