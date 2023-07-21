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
package com.iohao.game.component.chat.proto;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.common.kit.TimeKit;
import com.iohao.game.component.chat.cmd.ChatFileMerge;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 发送聊天，一般是客户端向服务器发送的聊天内容
 *
 * @author 渔民小镇
 * @date 2023-07-16
 */
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = ChatFileMerge.fileName, filePackage = ChatFileMerge.filePackage)
public class ChatSendMessage {
    /** 发送者昵称 */
    String senderNickname;
    /** 接收者、接收频道 */
    long receiverId;
    /** 消息内容 */
    List<ChatMessageItem> items;

    public ChatMessage toChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.items = this.items;
        chatMessage.senderNickname = this.senderNickname;
        chatMessage.sendTime = TimeKit.currentTimeMillis();
        chatMessage.receiverId = this.receiverId;
        return chatMessage;
    }
}
