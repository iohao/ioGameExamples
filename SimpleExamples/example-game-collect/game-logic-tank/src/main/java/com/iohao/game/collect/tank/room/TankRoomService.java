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
package com.iohao.game.collect.tank.room;

import com.iohao.game.widget.light.room.Player;
import com.iohao.game.widget.light.room.Room;
import com.iohao.game.widget.light.room.RoomService;
import com.iohao.game.widget.light.room.flow.GameFlowContext;
import com.iohao.game.widget.light.room.flow.GameFlowService;
import com.iohao.game.widget.light.room.flow.RoomCreateContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 渔民小镇
 * @date 2024-05-16
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TankRoomService implements RoomService, GameFlowService {
    final Map<Long, Room> roomMap = new ConcurrentHashMap<>();
    final Map<Long, Long> userRoomMap = new ConcurrentHashMap<>();
    AtomicLong roomIdAtomic = new AtomicLong(0);

    @Override
    public TankRoomEntity createRoom(RoomCreateContext createContext) {
        long roomId = roomIdAtomic.incrementAndGet();

        TankRoomEntity room = new TankRoomEntity();
        room.setRoomId(roomId);

        return room;
    }

    @Override
    public Player createPlayer(GameFlowContext gameFlowContext) {
        TankPlayerEntity tankPlayerEntity = new TankPlayerEntity();
        tankPlayerEntity.setUserId(gameFlowContext.getUserId());
        tankPlayerEntity.setHp(10000);
        return tankPlayerEntity;
    }

    @Override
    public void enterRoom(GameFlowContext gameFlowContext) {

        // 如果不在房间内先加入房间
        Room room = gameFlowContext.getRoom();
        long userId = gameFlowContext.getUserId();
        room.ifPlayerNotExist(userId, () -> {
            Player newPlayer = this.createPlayer(gameFlowContext);
            this.addPlayer(room, newPlayer);
        });
    }

    @Override
    public void startGameVerify(GameFlowContext gameFlowContext) {

    }

    @Override
    public void startGameVerifyAfter(GameFlowContext gameFlowContext) {

    }
}
