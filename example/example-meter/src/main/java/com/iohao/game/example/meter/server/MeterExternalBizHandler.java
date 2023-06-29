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
package com.iohao.game.example.meter.server;

import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.bolt.broker.core.aware.BrokerClientAware;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.message.BrokerClientModuleMessage;
import com.iohao.game.external.core.aware.UserSessionsAware;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.netty.session.SocketUserSessions;
import com.iohao.game.external.core.session.UserChannelId;
import com.iohao.game.external.core.session.UserSession;
import com.iohao.game.external.core.session.UserSessions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2022-10-02
 */
@Slf4j
@ChannelHandler.Sharable
public class MeterExternalBizHandler extends SimpleChannelInboundHandler<ExternalMessage>
        implements UserSessionsAware, BrokerClientAware {
    public static final LongAdder userIdAdder = new LongAdder();

    SocketUserSessions userSessions;
    BrokerClient brokerClient;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {
        UserSession userSession = this.userSessions.getUserSession(ctx);

        // 如果没登录的，模拟登录
        if (!userSession.isVerifyIdentity()) {
            userIdAdder.increment();

            UserChannelId userChannelId = userSession.getUserChannelId();
            userSessions.settingUserId(userChannelId, userIdAdder.longValue());
        }

        // 将 message 转换成 RequestMessage
        RequestMessage requestMessage = convertRequestMessage(message);

        try {
            // 请求游戏网关，在由网关转到具体的业务逻辑服
            brokerClient.oneway(requestMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private RequestMessage convertRequestMessage(ExternalMessage externalMessage) {

        BrokerClientModuleMessage brokerClientModuleMessage = brokerClient.getBrokerClientModuleMessage();
        int idHash = brokerClientModuleMessage.getIdHash();

        return ExternalKit.convertRequestMessage(externalMessage, idHash);
    }

    @Override
    public void setBrokerClient(BrokerClient brokerClient) {
        this.brokerClient = brokerClient;
    }

    @Override
    public void setUserSessions(UserSessions<?, ?> userSessions) {
        this.userSessions = (SocketUserSessions) userSessions;
    }


    public MeterExternalBizHandler() {

    }

    public static MeterExternalBizHandler me() {
    	return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final MeterExternalBizHandler ME = new MeterExternalBizHandler();
    }
}
