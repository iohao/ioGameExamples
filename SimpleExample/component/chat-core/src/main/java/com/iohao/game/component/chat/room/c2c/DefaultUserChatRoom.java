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
package com.iohao.game.component.chat.room.c2c;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.common.kit.CollKit;
import com.iohao.game.component.chat.cmd.ChatCmd;
import com.iohao.game.component.chat.proto.ChatMessage;
import com.iohao.game.component.chat.proto.ChatNotifyMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jctools.maps.NonBlockingHashMap;
import org.jctools.maps.NonBlockingHashSet;

import java.util.*;

/**
 * 聊天消息域，每个玩家对应一个
 *
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefaultUserChatRoom implements UserChatRoom {

    long userId;
    /** 未读消息的发送者列表 */
    Set<Long> unreadSenderIdSet = new NonBlockingHashSet<>();
    /**
     * 未读的发送者消息
     * <pre>
     *     key : 消息发送者
     *     value : SenderMessageRegion
     * </pre>
     */
    Map<Long, SenderMessageRegion> senderMessageRegionMap = new NonBlockingHashMap<>();

    /**
     * 接收私聊消息
     *
     * @param chatMessage 消息
     */
    public void receivePrivate(ChatMessage chatMessage) {
        long senderId = chatMessage.senderId;
        SenderMessageRegion senderMessageRegion = getSenderMessageRegion(senderId);
        senderMessageRegion.addMessage(chatMessage);

        this.notifyPrivate(senderId);
    }

    private void notifyPrivate(long senderId) {

        if (this.unreadSenderIdSet.contains(senderId)) {
            // 不通知了，表示玩家还没读之前的私聊消息
            return;
        }

        this.unreadSenderIdSet.add(senderId);

        // 消息通知，但不给具体的内容；让客户端主动读取
        CmdInfo cmdInfo = ChatCmd.of(ChatCmd.notifyPrivate);

        ChatNotifyMessage chatNotifyMessage = new ChatNotifyMessage();
        chatNotifyMessage.senderId = senderId;

        BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();
        broadcastContext.broadcast(cmdInfo, chatNotifyMessage, this.userId);
    }

    /**
     * 读取发送者的消息
     *
     * @param senderId 消息发送者
     */
    public List<ChatMessage> readPrivate(long senderId) {
        SenderMessageRegion senderMessageRegion = this.getSenderMessageRegion(senderId);

        this.unreadSenderIdSet.remove(senderId);

        return senderMessageRegion.listChatMessage();
    }

    public List<Long> listUnreadUserId() {
        if (CollKit.isEmpty(this.unreadSenderIdSet)) {
            return Collections.emptyList();
        }

        return new ArrayList<>(this.unreadSenderIdSet);
    }

    @Override
    public void setId(long id) {
        this.userId = id;
    }

    @Override
    public long getId() {
        return this.userId;
    }

    /**
     * 发送者的消息域
     */
    static class SenderMessageRegion {
        List<ChatMessage> chatMessageList = new LinkedList<>();

        void addMessage(ChatMessage chatMessage) {
            /*
             * synchronized 在没有竞争时，JVM 会使用轻量级锁；
             * 当有竞争时，锁会自动升级，将轻量级锁变为重量级锁；
             */
            synchronized (this) {
                chatMessageList.add(chatMessage);
            }
        }

        public List<ChatMessage> listChatMessage() {
            if (CollKit.isEmpty(this.chatMessageList)) {
                return Collections.emptyList();
            }

            /*
             * synchronized 在没有竞争时，JVM 会使用轻量级锁；
             * 当有竞争时，锁会自动升级，将轻量级锁变为重量级锁；
             *
             * 临界区内的操作是原子的
             */
            synchronized (this) {
                var chatMessages = new ArrayList<>(this.chatMessageList);
                this.chatMessageList.clear();
                return chatMessages;
            }
        }
    }

    SenderMessageRegion getSenderMessageRegion(long senderId) {
        SenderMessageRegion senderMessageRegion = this.senderMessageRegionMap.get(senderId);

        if (Objects.isNull(senderMessageRegion)) {
            senderMessageRegion = this.senderMessageRegionMap.putIfAbsent(senderId, new SenderMessageRegion());

            if (Objects.isNull(senderMessageRegion)) {
                senderMessageRegion = this.senderMessageRegionMap.get(senderId);
            }
        }

        return senderMessageRegion;
    }
}
