/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.component.chat.client;

import com.iohao.game.action.skeleton.protocol.wrapper.ByteValueList;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValueList;
import com.iohao.game.common.kit.CollKit;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import com.iohao.game.component.chat.cmd.ChatCmd;
import com.iohao.game.component.chat.proto.ChatMessage;
import com.iohao.game.component.chat.proto.ChatNotifyMessage;
import com.iohao.game.component.chat.proto.ChatSendMessage;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.command.InputRequestData;
import com.iohao.game.external.client.kit.ScannerKit;
import org.slf4j.Logger;

import java.util.List;

/**
 * 模拟客户端 - 私聊相关
 *
 * @author 渔民小镇
 * @date 2023-07-16
 */
public class ChatInputCommandRegion extends AbstractInputCommandRegion {
    static final Logger log = IoGameLoggerFactory.getLogger("chat");

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

        // 动态请求内容 - 私聊，聊天内容
        InputRequestData c2cInputRequestData = () -> {
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
        };

        // 创建一个模拟命令 - 【125-1】玩家与玩家的私聊
        ofCommand(ChatCmd.c_2_c)
                // 设置动态请求参数对象
                .setInputRequestData(c2cInputRequestData)
                // 命令描述
                .setDescription("玩家与玩家的私聊");

        // 创建一个模拟命令 - 【125-2】未读消息的发送者列表
        ofCommand(ChatCmd.listUnreadUserId).callback(LongValueList.class, result -> {
            LongValueList longValueList = result.getValue();
            log.info("未读消息的发送者列表 : {}", longValueList.values);
        }).setDescription("未读消息的发送者列表");

        // 创建一个模拟命令 - 【125-3】读取某个玩家的私有消息
        ofCommandUserId(ChatCmd.readPrivateMessage).callback(ByteValueList.class, result -> {
            List<ChatMessage> list = result.toList(ChatMessage.class);
            if (CollKit.isEmpty(list)) {
                return;
            }

            log.info("玩家【{}】读取私聊消息数量 : {}", userId, list.size());
            System.out.println("------------------------------");
            list.stream().map(ClientChatKit::toString).forEach(System.out::println);
            System.out.println("------------------------------");

        }).setDescription("读取某个玩家的私有消息");
    }

    private void initListen() {
        // 广播监听回调 - 监听【125-11】玩家私聊消息通知
        listenBroadcast(ChatNotifyMessage.class, result -> {
            ChatNotifyMessage chatNotifyMessage = result.getValue();
            // 聊天消息发送方的 userId
            long senderId = chatNotifyMessage.senderId;
            log.info("玩家[{}]给我的私聊通知", senderId);

            // 创建一个请求命令，【125-3】读取某个玩家的私有消息
            this.ofRequestCommand(ChatCmd.readPrivateMessage)
                    // 在执行请求时传入一个参数；
                    .request(senderId);

        }, ChatCmd.notifyPrivate, "玩家私聊消息通知");
    }
}
