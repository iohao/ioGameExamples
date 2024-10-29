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
package com.iohao.game.example.multiple.eventbus.user;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.example.multiple.eventbus.common.TheBusLogin;
import com.iohao.game.example.multiple.eventbus.common.UserCmd;
import com.iohao.game.example.multiple.eventbus.common.event.EmailAnyMessage;
import com.iohao.game.example.multiple.eventbus.common.event.EmptyEventMessage;
import com.iohao.game.example.multiple.eventbus.common.event.UserLoginEventMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@Slf4j
@ActionController(UserCmd.cmd)
public class UserAction {
    @ActionMethod(UserCmd.login)
    public TheBusLogin login(TheBusLogin login, FlowContext flowContext) {
        int userId = Math.abs(login.jwt.hashCode());

        flowContext.setUserId(userId);

        return login;
    }

    /**
     * fire
     * <pre>
     *     UserLoginEventMessage 发布后，会被 UserEventBusSubscriber、EmailEventBusSubscriber 接收。
     * </pre>
     *
     * @param flowContext flowContext
     * @return s
     */
    @ActionMethod(UserCmd.fireEvent)
    public String fireEventUser(FlowContext flowContext) {
        long userId = flowContext.getUserId();

        log.info("fire : {} ", userId);
        // 事件源
        var message = new UserLoginEventMessage(userId);
        // 发布事件
        flowContext.fire(message);

        return "fireEventUser";
    }

    /**
     * 当发布的事件源没有任何订阅者订阅时，会被 EventBusListener.emptySubscribe 监听。
     * 开发者也可自定义一个 EventBusListener
     *
     * @param flowContext flowContext
     * @return s
     */
    @ActionMethod(UserCmd.fireEventEmpty)
    public String fireEventUserEmpty(FlowContext flowContext) {

        long userId = flowContext.getUserId();

        log.info("fireEventUserEmpty 发送一个事件, 该事件没有任何订阅者");

        var eventMessage = new EmptyEventMessage(userId);

        flowContext.fire(eventMessage);

        return "fireEventUserEmpty";
    }

    /**
     * <a href="https://www.yuque.com/iohao/game/gmxz33#uzdgY">fireAny - 文档</a>
     * <p>
     * 使用场景:
     * <pre>
     *     假设现在有一个发放奖励的邮件逻辑服，我们启动了两个（或者说多个）邮件逻辑服实例来处理业务。
     *     当我们使用 fireAny 方法发送事件时，只会给其中一个实例发送事件。
     * </pre>
     *
     * @param flowContext flowContext
     * @return s
     */
    @ActionMethod(UserCmd.fireAnyMail)
    public String fireAnyMail(FlowContext flowContext) {
        long userId = flowContext.getUserId();

        log.info("fireEventAny");

        var eventMessage = new EmailAnyMessage(userId);

        flowContext.fireAny(eventMessage);

        return "fireEventAny";
    }

    @ActionMethod(UserCmd.fireSyncMail)
    public String fireSyncMail(FlowContext flowContext) {
        long userId = flowContext.getUserId();

        log.info("fireSyncMail");

        var eventMessage = new EmailAnyMessage(userId);

        flowContext.fireSync(eventMessage);

        return "fireSyncMail";
    }

    private static void extracted(FlowContext flowContext, UserLoginEventMessage userLoginEventMessage) throws InterruptedException {
        System.out.println();
        log.info("fireMeSync--------------");
        flowContext.fireMeSync(userLoginEventMessage);

        System.out.println();
        log.info("fireLocalSync--------------");
        flowContext.fireLocalSync(userLoginEventMessage);

        System.out.println();
        log.info("fireMe--------------");
        flowContext.fireMe(userLoginEventMessage);

        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println();
        log.info("fireLocal--------------");
        flowContext.fireLocal(userLoginEventMessage);
    }
}
