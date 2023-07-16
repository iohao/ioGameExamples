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
package com.iohao.game.component.chat.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.component.chat.cmd.ChatCmd;
import com.iohao.game.component.chat.proto.ChatMessage;
import com.iohao.game.component.chat.proto.ChatMessageTypeEnum;
import com.iohao.game.component.chat.proto.ChatSendMessage;
import com.iohao.game.component.chat.room.c2c.UserChatRoom;
import com.iohao.game.component.chat.room.c2c.UserChatRooms;

import java.util.List;

/**
 * 私聊相关
 *
 * @author 渔民小镇
 * @date 2023-06-28
 */
@ActionController(ChatCmd.cmd)
public class ChatAction {
    /**
     * 玩家与玩家的私聊
     *
     * @param sendMessage 聊天内容
     * @param flowContext flowContext
     */
    @ActionMethod(ChatCmd.c_2_c)
    public void c2c(ChatSendMessage sendMessage, FlowContext flowContext) {
        ChatMessage chatMessage = sendMessage.toChatMessage();
        chatMessage.senderId = flowContext.getUserId();
        chatMessage.msgType = ChatMessageTypeEnum.C_2_C;

        // 通知消息接收者
        long receiverId = chatMessage.receiverId;
        UserChatRoom receiverUserChatRoom = UserChatRooms.ofUserChatRoom(receiverId);
        receiverUserChatRoom.receivePrivate(chatMessage);
    }

    /**
     * 读取指定发送者的消息列表
     *
     * @param senderId    发送者
     * @param flowContext flowContext
     * @return 消息列表
     */
    @ActionMethod(ChatCmd.readPrivateMessage)
    public List<ChatMessage> readPrivateMessage(long senderId, FlowContext flowContext) {
        long userId = flowContext.getUserId();

        UserChatRoom userChatRoom = UserChatRooms.ofUserChatRoom(userId);
        return userChatRoom.readPrivate(senderId);
    }

    /**
     * 未读消息的发送者列表
     *
     * @param flowContext flowContext
     * @return 未读消息的发送者列表
     */
    @ActionMethod(ChatCmd.listUnreadUserId)
    public List<Long> listUnreadUserId(FlowContext flowContext) {
        // 未读消息的发送者列表
        long userId = flowContext.getUserId();
        UserChatRoom userChatRoom = UserChatRooms.ofUserChatRoom(userId);
        return userChatRoom.listUnreadUserId();
    }
}
