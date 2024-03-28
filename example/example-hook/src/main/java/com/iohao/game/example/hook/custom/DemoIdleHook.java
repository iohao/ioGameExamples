/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.game.action.skeleton.protocol.BarMessage;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.common.kit.TimeKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.external.core.message.ExternalCodecKit;
import com.iohao.game.external.core.netty.hook.SocketIdleHook;
import com.iohao.game.external.core.session.UserSession;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

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
    /** 当前时间戳 */
    volatile byte[] timeBytes;

    public DemoIdleHook() {
        updateTime();
        // 每秒更新当前时间
        TaskKit.runInterval(this::updateTime, 1, TimeUnit.SECONDS);
    }

    private void updateTime() {
        LongValue data = LongValue.of(TimeKit.currentTimeMillis());
        /*
         * 提前序列化好时间数据
         * 1. 可以避免重复序列化
         * 2. 减少对象的创建（不需要每次处理心跳时创建对象）
         */
        timeBytes = DataCodecKit.encode(data);
    }

    @Override
    public void pongBefore(BarMessage idleMessage) {
        // 把当前时间戳给到心跳接收端
        idleMessage.setData(timeBytes);
    }

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

        BarMessage message = ExternalCodecKit.createErrorIdleMessage(ActionErrorEnum.idleErrorCode);
        // 错误消息
        message.setValidatorMsg(ActionErrorEnum.idleErrorCode.getMsg() + " : " + state.name());

        // 通知客户端，触发了心跳事件
        userSession.writeAndFlush(message);

        // 返回 true 表示通知框架将当前的用户（玩家）连接关闭
        return true;
    }
}