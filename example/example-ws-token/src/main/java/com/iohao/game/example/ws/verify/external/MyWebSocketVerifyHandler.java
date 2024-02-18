/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.ws.verify.external;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.bolt.broker.core.aware.BrokerClientAware;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.example.common.msg.DemoAttachment;
import com.iohao.game.example.ws.verify.action.WsVerifyCmd;
import com.iohao.game.external.core.message.ExternalCodecKit;
import com.iohao.game.external.core.netty.handler.ws.WebSocketVerifyHandler;
import com.iohao.game.external.core.netty.session.SocketUserSession;
import com.iohao.game.external.core.session.UserSessionOption;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author 渔民小镇
 * @date 2023-08-04
 */
@Slf4j
@Setter
public class MyWebSocketVerifyHandler extends WebSocketVerifyHandler
        implements BrokerClientAware {

    BrokerClient brokerClient;

    @Override
    public boolean verify(SocketUserSession userSession, Map<String, String> params) {
        /*
         * 测试方法，ws://127.0.0.1:10100/websocket?token=abc&name=aaaa
         * 之后可以在 params 中得到参数 key:value 格式
         */
        String token = params.get("token");
        boolean verifyResult = "abc".equals(token);

        if (verifyResult) {
            // 只保存一些关键的元信息
            DemoAttachment attachment = new DemoAttachment();
            attachment.name = params.get("name");
            attachment.userId = Math.abs(attachment.name.hashCode());

            byte[] encode = DataCodecKit.encode(attachment);
            userSession.option(UserSessionOption.attachment, encode);

            // 创建一个登录请求
            RequestMessage requestMessage = this.createRequestMessage();
            userSession.employ(requestMessage);

            // 请求游戏网关，在由网关转到具体的业务逻辑服
            // 触发登录 action
            try {
                brokerClient.oneway(requestMessage);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        // 返回 true 表示验证通过，返回 false 框架会关闭连接。
        return verifyResult;
    }

    private RequestMessage createRequestMessage() {
        RequestMessage request = ExternalCodecKit.createRequest();
        HeadMetadata headMetadata = request.getHeadMetadata();
        CmdInfo cmdInfo = CmdInfo.of(WsVerifyCmd.cmd, WsVerifyCmd.login);
        headMetadata.setCmdInfo(cmdInfo);

        ExternalCodecKit.employ(request, brokerClient);

        return request;
    }
}