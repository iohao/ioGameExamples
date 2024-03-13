/*
 * ioGame
 * Copyright (C) 2021 - 2024  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.exchange.client;

import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.exchange.common.ExchangeCmd;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
@Slf4j
public class ExchangeClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();
        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(List.of(new ExchangeInputCommandRegion()))
                .startup();
    }

    static class ExchangeInputCommandRegion extends AbstractInputCommandRegion {

        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = ExchangeCmd.cmd;

            ofCommand(ExchangeCmd.loginVerify).setTitle("登录").setRequestData(() -> {
                // jwt
                return StringValue.of("1");
            }).callback(result -> {
                long userId = result.getLong();
                log.info("value : {}", userId);

                this.clientUser.setUserId(userId);
                this.clientUser.callbackInputCommandRegion();
            });

            this.ofListen(result -> {
                long money = result.getLong();
                log.info("userId : {} ，充值金额 money : {}", this.userId, money);
            }, ExchangeCmd.recharge, "充值通知");

            this.ofListen(result -> {
                String value = result.getString();
                log.info("value : {}", value);
            }, ExchangeCmd.notice, "GM 后台消息");

            // 延迟执行模拟请求;
            TaskKit.newTimeout(timeout -> {
                // 执行请求
                ofRequestCommand(ExchangeCmd.loginVerify).execute();
            }, 100, TimeUnit.MILLISECONDS);
        }
    }
}
