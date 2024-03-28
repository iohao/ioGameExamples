/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.component.chat.client;

import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.common.kit.CollKit;
import com.iohao.game.component.chat.cmd.ChatCmd;
import com.iohao.game.component.chat.proto.ChatMessage;
import com.iohao.game.component.chat.proto.ChatNotifyMessage;
import com.iohao.game.component.chat.proto.ChatSendMessage;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.kit.ScannerKit;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 模拟客户端 - 私聊相关
 *
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
public class ChatInputCommandRegion extends AbstractInputCommandRegion {

    @Override
    public void initInputCommand() {
        // 设置模拟的主路由 【125】
        inputCommandCreate.cmd = ChatCmd.cmd;

        // 构建模拟请求
        initRequest();

        // 监听服务器广播
        initListen();
    }

    private void initRequest() {
        // 创建一个模拟命令 - 【125-1】玩家与玩家的私聊
        ofCommand(ChatCmd.c_2_c).setTitle("玩家与玩家的私聊").setRequestData(() -> {
            // 当开启关闭控制台输入配置时，会使用 【2】 作为默认值；（这个稍后解释）
            ScannerKit.log(() -> log.info("请输入接收者的 userId"));
            long receiverId = ScannerKit.nextLong(2);

            // 当开启关闭控制台输入配置时，会使用 【你好，老哥】 作为默认值；（这个稍后解释）
            ScannerKit.log(() -> log.info("请输入聊天内容 String"));
            String bodyString = ScannerKit.nextLine("你好，老哥");

            // 创建一个发送聊天对象
            ChatSendMessage chatSendMessage = ClientChatKit.ofChatMessage(receiverId, bodyString);
            chatSendMessage.senderNickname = clientUser.getNickname();

            return chatSendMessage;
        });

        // 创建一个模拟命令 - 【125-2】未读消息的发送者列表
        ofCommand(ChatCmd.listUnreadUserId).setTitle("未读消息的发送者列表").callback(result -> {
            List<Long> values = result.listLong();
            log.info("未读消息的发送者列表 : {}", values);
        });

        // 创建一个模拟命令 - 【125-3】读取某个玩家的私有消息
        ofCommandUserId(ChatCmd.readPrivateMessage).setTitle("读取某个玩家的私有消息~~~").callback(result -> {
            List<ChatMessage> list = result.listValue(ChatMessage.class);
            if (CollKit.isEmpty(list)) {
                return;
            }

            log.info("玩家【{}】读取私聊消息数量 : {}", userId, list.size());
            System.out.println("------------------------------");
            list.stream().map(ClientChatKit::toString).forEach(System.out::println);
            System.out.println("------------------------------");
        });
    }

    private void initListen() {
        // 广播监听回调 - 监听【125-11】玩家私聊消息通知
        ofListen(result -> {
            ChatNotifyMessage chatNotifyMessage = result.getValue(ChatNotifyMessage.class);
            // 聊天消息发送方的 userId
            long senderId = chatNotifyMessage.senderId;
            log.info("玩家[{}]给我的私聊通知", senderId);

            // 创建一个请求命令，【125-3】读取某个玩家的私有消息
            this.ofRequestCommand(ChatCmd.readPrivateMessage)
                    // 请求参数；
                    .setRequestData(() -> LongValue.of(senderId))
                    .execute();

        }, ChatCmd.notifyPrivate, "玩家私聊消息通知~~~");
    }
}
