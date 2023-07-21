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
package com.iohao.game.example.meter.login;

import com.iohao.game.common.kit.InternalKit;
import com.iohao.game.example.common.cmd.MeterLoginCmd;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class MeterLoginTcpSocketClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new InternalRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setJoinEnum(ExternalJoinEnum.TCP)
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }

    static class InternalRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = MeterLoginCmd.cmd;

            HelloReq helloReq = new HelloReq();
            helloReq.name = "abc12";

            ofCommand(MeterLoginCmd.login).callback(HelloReq.class, result -> {
                HelloReq value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("login").setRequestData(helloReq);

            // 一秒后，执行模拟请求;
            InternalKit.newTimeoutSeconds(task -> {
                // 执行请求
                for (int i = 0; i < 20; i++) {
                    ofRequestCommand(MeterLoginCmd.login).request();
                }
            });
        }
    }
}
