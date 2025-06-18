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
package com.iohao.game.example.ws.verify;

import com.iohao.game.example.common.msg.HelloMessage;
import com.iohao.game.example.ws.verify.action.WsVerifyCmd;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-08-04
 */
public class WsClient {
    public static void main(String[] args) throws InterruptedException {

        String websocketVerify = "?token=abc&name=aaaa";

        new ClientRunOne()
                .setWebsocketVerify(websocketVerify)
                // 请求消息编排
                .setInputCommandRegions(List.of(new WsInputCommandRegion()))
                .startup();

        TimeUnit.SECONDS.sleep(1);
    }

    @Slf4j
    static class WsInputCommandRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            this.inputCommandCreate.cmd = WsVerifyCmd.cmd;

            ofListen(result -> {
                Object value = result.getValue(HelloMessage.class);
                log.info("listen value : {}", value);
            }, WsVerifyCmd.login, "监听登录消息");
        }
    }
}
