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
package com.iohao.game.exchange.web;

import com.iohao.game.action.skeleton.core.BarMessageKit;
import com.iohao.game.action.skeleton.core.CmdInfo;
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
    static AtomicLong msgId = new AtomicLong();
    /** 为了方便测试，这里指定一个 userId 来模拟玩家 */
    final long userId = 1;

    @GetMapping("/notice")
    public String notice() {
        log.info("notice");
        // 模拟数据，GM 后台消息
        long id = msgId.incrementAndGet();
        String msg = "GM web msg " + id;

        // 路由、请求参数
        var noticeCmd = ExchangeCmd.of(ExchangeCmd.notice);
        // 使用协议碎片特性 https://www.yuque.com/iohao/game/ieimzn
        StringValue data = WrapperKit.of(msg);

        // 模拟请求
        RequestMessage requestMessage = BarMessageKit.createRequestMessage(noticeCmd, data);
        // 设置需要模拟的玩家
        requestMessage.getHeadMetadata().setUserId(userId);

        // 向逻辑服发送请求
        BrokerClient brokerClient = MyKit.brokerClient;
        InvokeModuleContext invokeModuleContext = brokerClient.getInvokeModuleContext();
        invokeModuleContext.invokeModuleVoidMessage(requestMessage);

        return "通知成功！ " + id;
    }

    @GetMapping("/recharge")
    public String recharge() {
        log.info("recharge");

        // 模拟数据，充值金额
        long money = RandomKit.random(1, 1000);

        // 路由、请求参数
        var rechargeCmd = ExchangeCmd.of(ExchangeCmd.recharge);
        // 使用协议碎片特性 https://www.yuque.com/iohao/game/ieimzn
        LongValue moneyData = WrapperKit.of(money);

        // 模拟玩家请求
        UserFlowContext userFlowContext = MyKit.ofUserFlowContext(userId);
        ResponseMessage responseMessage = userFlowContext.invokeModuleMessage(rechargeCmd, moneyData);

        if (responseMessage.hasError()) {
            return "充值失败";
        }

        StringValue data = responseMessage.getData(StringValue.class);
        log.info("web recharge result : {}", data.value);

        return "充值成功！" + data.value;
    }

    @GetMapping("/rechargeSync")
    public String rechargeSync() {
        log.info("rechargeAsync");

        // 模拟数据，充值金额
        long money = RandomKit.random(1, 1000);

        // 路由、请求参数
        var rechargeCmd = ExchangeCmd.of(ExchangeCmd.recharge);
        // 使用协议碎片特性 https://www.yuque.com/iohao/game/ieimzn
        LongValue moneyData = WrapperKit.of(money);

        // 模拟请求
        RequestMessage requestMessage = BarMessageKit.createRequestMessage(rechargeCmd, moneyData);
        // 设置需要模拟的玩家
        requestMessage.getHeadMetadata().setUserId(userId);

        // 向逻辑服发送请求
        BrokerClient brokerClient = MyKit.brokerClient;
        InvokeModuleContext invokeModuleContext = brokerClient.getInvokeModuleContext();
        ResponseMessage responseMessage = invokeModuleContext.invokeModuleMessage(requestMessage);

        if (responseMessage.hasError()) {
            return "充值失败";
        }

        StringValue data = responseMessage.getData(StringValue.class);
        log.info("web recharge result : {}", data.value);
        return "充值成功！async" + money;
    }
}
