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

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.bolt.broker.core.aware.BrokerClientAware;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.example.hook.action.DemoCmdForHookRoom;
import com.iohao.game.external.core.aware.UserSessionsAware;
import com.iohao.game.external.core.hook.UserHook;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.session.UserSession;
import com.iohao.game.external.core.session.UserSessions;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
public class DemoUserHook implements UserHook, UserSessionsAware, BrokerClientAware {

    BrokerClient brokerClient;
    UserSessions<?, ?> userSessions;

    @Override
    public void into(UserSession userSession) {
        long userId = userSession.getUserId();
        log.info("demo 玩家上线 userId: {} -- {}", userId, userSession.getUserChannelId());
        log.info("demo 当前在线玩家数量： {}", this.userSessions.countOnline());
    }

    @Override
    public void quit(UserSession userSession) {
        long userId = userSession.getUserId();
        log.info("demo 玩家退出 userId: {} -- {}", userId, userSession.getUserChannelId());
        log.info("demo 当前在线玩家数量： {}", this.userSessions.countOnline());

        // 房间主路由和房间子路由（退出房间）
        int mergeCmd = CmdKit.merge(DemoCmdForHookRoom.cmd, DemoCmdForHookRoom.quitRoom);
        // 创建请求消息，createRequestMessage 有多个重载，可以传入业务参数

        int idHash = brokerClient.getBrokerClientModuleMessage().getIdHash();
        RequestMessage requestMessage = ExternalKit.createRequestMessage(mergeCmd, idHash);
        userSession.employ(requestMessage);

        try {
            // 请求游戏网关
            this.brokerClient.oneway(requestMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void setUserSessions(UserSessions<?, ?> userSessions) {
        this.userSessions = userSessions;
    }

    @Override
    public void setBrokerClient(BrokerClient brokerClient) {
        this.brokerClient = brokerClient;
    }
}
