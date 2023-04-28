/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.example.hook.custom;

import com.iohao.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.game.external.core.hook.IdleHook;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.message.ExternalMessageCmdCode;
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
public class DemoIdleHook implements IdleHook<IdleStateEvent> {
    @Override
    public boolean callback(UserSession userSession, IdleStateEvent event) {
        IdleState state = event.state();

        if (state == IdleState.READER_IDLE) {
            /* 读超时 */
            log.debug("READER_IDLE 读超时");
        } else if (state == IdleState.WRITER_IDLE) {
            /* 写超时 */
            log.debug("WRITER_IDLE 写超时");
        } else if (state == IdleState.ALL_IDLE) {
            /* 总超时 */
            log.debug("ALL_IDLE 总超时");
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
