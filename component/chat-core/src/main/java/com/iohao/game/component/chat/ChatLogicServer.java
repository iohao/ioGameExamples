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
package com.iohao.game.component.chat;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.doc.ActionSendDoc;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.component.chat.action.ChatAction;
import com.iohao.game.component.chat.cmd.ChatCmd;
import com.iohao.game.component.chat.proto.ChatNotifyMessage;
import com.iohao.game.action.skeleton.kit.LogicServerCreateKit;

/**
 * 聊天逻辑服
 *
 * @author 渔民小镇
 * @date 2023-07-16
 */
public class ChatLogicServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器
        BarSkeletonBuilder builder = LogicServerCreateKit.createBuilder(ChatAction.class);

        sendDoc(builder);

        return builder.build();
    }

    private static void sendDoc(BarSkeletonBuilder builder) {
        CmdInfo cmdInfo;

        // 推送、广播文档
        cmdInfo = ChatCmd.getCmdInfo(ChatCmd.notifyPrivate);
        builder.addActionSendDoc(new ActionSendDoc(cmdInfo, ChatNotifyMessage.class, "私聊消息通知"));
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.id("component-chat-1");
        builder.appName("聊天逻辑服");
        return builder;
    }
}
