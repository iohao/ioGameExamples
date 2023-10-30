/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.one;

import com.iohao.game.common.kit.InternalKit;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.example.one.action.DemoCmd;
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
public class DemoClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new InternalRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }

    static class InternalRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = DemoCmd.cmd;

            // 登录请求
            ofCommand(DemoCmd.loginVerify).setTitle("登录").setRequestData(() -> {
                DemoLoginVerify loginVerify = new DemoLoginVerify();
                loginVerify.jwt = "abc";
                return loginVerify;
            }).callback(result -> {
                DemoUserInfo value = result.getValue(DemoUserInfo.class);
                log.info("value : {}", value);
                // 登录成功后，发起请求
                ofRequestCommand(DemoCmd.here).execute();
                ofRequestCommand(DemoCmd.jackson).execute();
            });

            // here
            ofCommand(DemoCmd.here).setTitle("here").setRequestData(() -> {
                HelloReq helloReq = new HelloReq();
                helloReq.name = "塔姆";
                return helloReq;
            }).callback(result -> {
                HelloReq value = result.getValue(HelloReq.class);
                log.info("value : {}", value);
            });

            ofCommand(DemoCmd.jackson).setTitle("jackson").setRequestData(() -> {
                HelloReq helloReq = new HelloReq();
                helloReq.name = "塔姆";
                return helloReq;
            }).callback(result -> {
                HelloReq value = result.getValue(HelloReq.class);
                log.info("value : {}", value);
            });

            // 一秒后，执行模拟请求;
            InternalKit.newTimeoutSeconds(task -> {
                // 执行请求
                ofRequestCommand(DemoCmd.loginVerify).execute();
            });
        }
    }
}
