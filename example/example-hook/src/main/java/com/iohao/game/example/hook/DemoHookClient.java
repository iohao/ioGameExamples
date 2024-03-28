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
package com.iohao.game.example.hook;

import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.example.hook.action.DemoCmdForHookRoom;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class DemoHookClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new InternalRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                // 开启心跳，每 3 秒向服务器发送一次心跳消息
                .idle(3)
                .startup();
    }

    static class InternalRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = DemoCmdForHookRoom.cmd;

            // 登录请求
            ofCommand(DemoCmdForHookRoom.loginVerify).setTitle("登录验证").setRequestData(() -> {
                DemoLoginVerify loginVerify = new DemoLoginVerify();
                loginVerify.jwt = "abc";
                return loginVerify;
            }).callback(result -> {
                DemoUserInfo value = result.getValue(DemoUserInfo.class);
                log.info("value : {}", value);
                this.clientUser.setUserId(value.id);
                this.clientUser.callbackInputCommandRegion();
            });

            // 一秒后，执行模拟请求;
            TaskKit.runOnceSecond(() -> {
                // 执行请求
                ofRequestCommand(DemoCmdForHookRoom.loginVerify).execute();
            });

            // hello
            ofCommand(DemoCmdForHookRoom.hello).setTitle("hello").callback(result -> {
                var value = result.getLong();
                log.info("hello : {}", value);
            });
        }
    }
}
