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
package com.iohao.game.exchange.web;

import com.iohao.game.action.skeleton.core.BarMessageKit;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.action.skeleton.protocol.wrapper.WrapperKit;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.common.kit.RandomKit;
import com.iohao.game.exchange.common.ExchangeCmd;
import com.iohao.game.exchange.web.kit.MyKit;
import com.iohao.game.exchange.web.kit.UserFlowContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
@Slf4j
@RestController
@RequestMapping("gm")
public class GameManagerController {
    static final AtomicLong msgId = new AtomicLong();
    static final long userId = 1;

    @GetMapping("/notice")
    public String notice() {
        log.info("notice");

        long id = msgId.incrementAndGet();
        String msg = "GM web message " + id;
        var noticeCmd = ExchangeCmd.of(ExchangeCmd.notice);

        RequestMessage requestMessage = BarMessageKit.createRequestMessage(noticeCmd, StringValue.of(msg));
        requestMessage.getHeadMetadata().setUserId(userId);

        BrokerClient brokerClient = MyKit.brokerClient;
        brokerClient.getInvokeModuleContext().invokeModuleVoidMessage(requestMessage);

        return "notice: " + id;
    }

    @GetMapping("/recharge")
    public String recharge() {
        log.info("recharge");

        var rechargeCmd = ExchangeCmd.of(ExchangeCmd.recharge);
        long money = RandomKit.random(1, 1000);

        UserFlowContext userFlowContext = MyKit.ofUserFlowContext(userId);
        ResponseMessage responseMessage = userFlowContext.invokeModuleMessage(rechargeCmd, LongValue.of(money));
        if (responseMessage.hasError()) {
            return "recharge error";
        }

        String data = responseMessage.getString();
        return "recharge: " + data;
    }
}
