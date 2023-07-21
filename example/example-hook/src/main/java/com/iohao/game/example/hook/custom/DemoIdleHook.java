/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.hook.custom;

import com.iohao.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.message.ExternalMessageCmdCode;
import com.iohao.game.external.core.netty.hook.SocketIdleHook;
import com.iohao.game.external.core.session.UserSession;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义 心跳钩子事件回调 示例
 * <pre>
 *     给逻辑服发送一个请求
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
public class DemoIdleHook implements SocketIdleHook {
    @Override
    public boolean callback(UserSession userSession, IdleStateEvent event) {
        IdleState state = event.state();

        if (state == IdleState.READER_IDLE) {
            /* 读超时 */
            log.debug("READER_IDLE 读超时 DemoIdleHook");
        } else if (state == IdleState.WRITER_IDLE) {
            /* 写超时 */
            log.debug("WRITER_IDLE 写超时 DemoIdleHook");
        } else if (state == IdleState.ALL_IDLE) {
            /* 总超时 */
            log.debug("ALL_IDLE 总超时 DemoIdleHook");
        }

        // 给（真实）用户发送一条消息
        ExternalMessage externalMessage = ExternalKit.createExternalMessage();
        externalMessage.setCmdCode(ExternalMessageCmdCode.idle);
        // 错误码
        externalMessage.setResponseStatus(ActionErrorEnum.idleErrorCode.getCode());
        // 错误消息
        externalMessage.setValidMsg(ActionErrorEnum.idleErrorCode.getMsg() + " : " + state.name());

        // 通知客户端，触发了心跳事件
        userSession.writeAndFlush(externalMessage);
        return true;
    }
}
