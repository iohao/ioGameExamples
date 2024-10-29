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
package com.iohao.game.example.multiple.eventbus.email.subscriber;

import com.iohao.game.action.skeleton.eventbus.EventSubscribe;
import com.iohao.game.action.skeleton.eventbus.EventBusSubscriber;
import com.iohao.game.action.skeleton.eventbus.ExecutorSelector;
import com.iohao.game.example.multiple.eventbus.common.event.EmailMethodEventMessage;
import com.iohao.game.example.multiple.eventbus.common.event.EmailSimpleEventMessage;
import com.iohao.game.example.multiple.eventbus.common.event.UserLoginEventMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@Slf4j
@EventBusSubscriber
public class EmailEventBusSubscriber {
    @EventSubscribe
    public void mail(UserLoginEventMessage message) {
        long userId = message.getUserId();
        log.info("event - 玩家[{}]登录，发放 email 奖励", userId);
    }

    @EventSubscribe(ExecutorSelector.methodExecutor)
    public void methodExecutor(EmailMethodEventMessage message) {
        long userId = message.getUserId();
        log.info("event - methodExecutor 线程执行 [{}]", userId);
    }

    @EventSubscribe(ExecutorSelector.simpleExecutor)
    public void simpleExecutor(EmailSimpleEventMessage message) {
        long userId = message.getUserId();
        log.info("event - simpleExecutor 线程执行 [{}]", userId);
    }
}
