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

import com.iohao.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.bolt.broker.core.aware.BrokerClientAware;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.message.BrokerClientModuleMessage;
import com.iohao.game.external.core.aware.UserSessionsAware;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.netty.session.NettyUserSessions;
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

    NettyUserSessions userSessions;
    BrokerClient brokerClient;

    @Override
    public void setUserSessions(UserSessions<?, ?> userSessions) {
        this.userSessions = (NettyUserSessions) userSessions;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {

        // 得到 session
        UserSession userSession = this.userSessions.getUserSession(ctx);

        // 如果没登录的，模拟登录
        if (!userSession.isVerifyIdentity()) {
            userIdAdder.increment();

            UserChannelId userChannelId = userSession.getUserChannelId();
            userSessions.settingUserId(userChannelId, userIdAdder.longValue());
        }

        // 是否可以访问业务方法（action），true 表示可以访问该路由对应的业务方法
        boolean pass = ExternalGlobalConfig.accessAuthenticationHook.pass(userSession, message.getCmdMerge());

        // 当访问验证没通过，通知玩家
        if (!pass) {
            message.setResponseStatus(ActionErrorEnum.verifyIdentity.getCode());
            message.setValidMsg("请先登录，在请求业务方法");
            // 响应结果给用户
            userSession.writeAndFlush(message);
            return;
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
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("channelInactive");
        // 从 session 管理中移除
        var userSession = this.userSessions.getUserSession(ctx);
        this.userSessions.removeUserSession(userSession);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 加入到 session 管理
        this.userSessions.add(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("exceptionCaught");
        // 从 session 管理中移除
        var userSession = this.userSessions.getUserSession(ctx);
        this.userSessions.removeUserSession(userSession);
    }

    @Override
    public void setBrokerClient(BrokerClient brokerClient) {
        this.brokerClient = brokerClient;
    }
}
