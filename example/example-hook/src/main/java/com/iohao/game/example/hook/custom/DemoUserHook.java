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
        // 给请求消息加上一些 user 自身的数据
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
