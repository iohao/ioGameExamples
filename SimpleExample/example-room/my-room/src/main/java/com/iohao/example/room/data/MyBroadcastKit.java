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
package com.iohao.example.room.data;

import com.iohao.example.room.message.*;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.widget.light.room.Room;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@UtilityClass
public final class MyBroadcastKit {
    public void broadcast(ReadyMessage message, Room room) {
        CmdInfo cmdInfo = RoomCmd.readyBroadcast;
        room.broadcastRange(cmdInfo, message);
    }

    public void broadcastUserQuitRoom(long userId, Room room) {
        var cmdInfo = RoomCmd.quitRoomBroadcast;
        room.broadcastRange(cmdInfo, LongValue.of(userId));
    }

    public void broadcastEnterRoom(MyRoom room) {
        CmdInfo cmdInfo = RoomCmd.enterRoomBroadcast;
        EnterRoomMessage enterRoomMessage = mapEnterRoomMessage(room);
        room.broadcastRange(cmdInfo, enterRoomMessage);
    }

    public void broadcastOperation(MyOperationCommandMessage message, Room room) {
        CmdInfo cmdInfo = RoomCmd.operationBroadcast;
        room.broadcastRange(cmdInfo, message);
    }

    private EnterRoomMessage mapEnterRoomMessage(MyRoom room) {
        EnterRoomMessage enterRoomMessage = new EnterRoomMessage();
        enterRoomMessage.roomId = room.getRoomId();

        enterRoomMessage.playerMap = room.streamPlayer()
                .map((player) -> MyBroadcastKit.mapPlayerMessage((MyPlayer) player))
                .collect(Collectors.toMap(PlayerMessage::getUserId, p -> p));

        return enterRoomMessage;
    }

    private PlayerMessage mapPlayerMessage(MyPlayer player) {
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.userId = player.getUserId();
        playerMessage.nickname = player.getNickname();
        return playerMessage;
    }
}
