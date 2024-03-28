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
package com.iohao.game.exchange.logic;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.action.skeleton.protocol.wrapper.WrapperKit;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.exchange.common.ExchangeCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
@Slf4j
@ActionController(ExchangeCmd.cmd)
public class ExchangeAction {

    @ActionMethod(ExchangeCmd.loginVerify)
    public long loginVerify(String jwt, FlowContext flowContext) {

        // 为了方便测试，这里指定一个 userId
        long userId = 1;

        // （相当于顶号），强制断开之前的客户端连接，并让本次登录成功。
        ExternalCommunicationKit.forcedOffline(userId);

        // channel 中设置用户的真实 userId；
        UserIdSettingKit.settingUserId(flowContext, userId);

        return userId;
    }

    @ActionMethod(ExchangeCmd.recharge)
    public String internalRecharge(long money, FlowContext flowContext) {
        long userId = flowContext.getUserId();
        log.info("通过 GM 后台，给玩家 [{}] - 充值金额 [{}] ", userId, money);

        // 广播消息给玩家，通知充值成功
        // 使用协议碎片特性 https://www.yuque.com/iohao/game/ieimzn
        flowContext.broadcastMe(WrapperKit.of(money));

        return "玩家 %s ，充值金额 %s，成功！".formatted(userId, money);
    }

    @ActionMethod(ExchangeCmd.notice)
    public void internalNotice(String msg, FlowContext flowContext) {
        log.info("通过 GM 后台发送一条消息给玩家 [{}] - 消息内容 [{}]", flowContext.getUserId(), msg);
        // 广播消息给玩家
        // 使用协议碎片特性 https://www.yuque.com/iohao/game/ieimzn
        flowContext.broadcastMe(StringValue.of(msg));
    }
}