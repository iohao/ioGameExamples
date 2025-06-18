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
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.exchange.common.ExchangeAttachment;
import com.iohao.game.exchange.common.ExchangeCmd;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
@Slf4j
@ActionController(ExchangeCmd.cmd)
public class ExchangeAction {
    static final Faker faker = new Faker(Locale.CHINA);
    static final Name name = faker.name();

    @ActionMethod(ExchangeCmd.loginVerify)
    public long loginVerify(String jwt, FlowContext flowContext) {

        long userId = 1;

        ExternalCommunicationKit.forcedOffline(userId);

        flowContext.bindingUserId(userId);

        ExchangeAttachment attachment = new ExchangeAttachment();
        attachment.userId = userId;
        attachment.nickname = name.fullName();
        flowContext.updateAttachment(attachment);

        return userId;
    }

    @ActionMethod(ExchangeCmd.recharge)
    public String internalRecharge(long money, FlowContext flowContext) {
        long userId = flowContext.getUserId();
        log.info("userId:{}, recharge:{}", userId, money);
        flowContext.broadcastMe(LongValue.of(money));

        return "userId:%s, recharge %s".formatted(userId, money);
    }

    @ActionMethod(ExchangeCmd.notice)
    public void internalNotice(String message, FlowContext flowContext) {
        long userId = flowContext.getUserId();
        log.info("userId:{}, message:{}", userId, message);

        flowContext.broadcastMe(StringValue.of(message));
    }
}