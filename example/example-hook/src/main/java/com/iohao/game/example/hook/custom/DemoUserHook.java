/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
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

import com.alipay.remoting.exception.RemotingException;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalKit;
import com.iohao.game.bolt.broker.client.external.session.UserSession;
import com.iohao.game.bolt.broker.client.external.session.UserSessions;
import com.iohao.game.bolt.broker.client.external.session.hook.UserHook;
import com.iohao.game.example.hook.action.DemoCmdForHookRoom;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
public class DemoUserHook implements UserHook {
    @Override
    public void into(UserSession userSession) {
        long userId = userSession.getUserId();
        log.info("demo 玩家上线 userId: {} -- {}", userId, userSession.getUserChannelId());
        log.info("demo 当前在线玩家数量： {}", UserSessions.me().countOnline());
    }

    @Override
    public void quit(UserSession userSession) {
        long userId = userSession.getUserId();
        log.info("demo 玩家退出 userId: {} -- {}", userId, userSession.getUserChannelId());
        log.info("demo 当前在线玩家数量： {}", UserSessions.me().countOnline());

        // 房间主路由和房间子路由（退出房间）
        int mergeCmd = CmdKit.merge(DemoCmdForHookRoom.cmd, DemoCmdForHookRoom.quitRoom);
        // 创建请求消息，createRequestMessage 有多个重载，可以传入业务参数
        RequestMessage requestMessage = ExternalKit.createRequestMessage(mergeCmd);

        try {
            // 请求游戏网关
            // 由内部逻辑服转发用户请求到游戏网关，在由网关转到具体的业务逻辑服
            ExternalKit.requestGateway(userSession, requestMessage);
        } catch (RemotingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
