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

import com.iohao.game.common.kit.TimeKit;
import com.iohao.game.component.chat.proto.ChatMessage;
import com.iohao.game.component.chat.proto.ChatMessageItem;
import com.iohao.game.component.chat.proto.ChatSendMessage;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 渔民小镇
 * @date 2023-07-16
 */
@UtilityClass
class ClientChatKit {
    String toString(ChatMessage chatMessage) {
        String collect = chatMessage.items
                .stream()
                .map(item -> item.bodyString)
                .collect(Collectors.joining(""));

        String time = TimeKit.formatter(chatMessage.sendTime);
        String format = """
                发送者[%s - %s] {%s} 聊天内容
                [%s]
                """;

        return String.format(format, chatMessage.senderNickname, chatMessage.senderId, time, collect);
    }

    ChatSendMessage ofChatMessage(long receiverId, String bodyString) {

        ChatMessageItem item = new ChatMessageItem();
        item.bodyString = bodyString;

        ChatSendMessage chatSendMessage = new ChatSendMessage();
        chatSendMessage.receiverId = receiverId;
        chatSendMessage.items = List.of(item);

        return chatSendMessage;
    }
}
