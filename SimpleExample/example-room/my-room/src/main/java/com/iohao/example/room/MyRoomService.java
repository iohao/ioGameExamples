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
package com.iohao.example.room;

import com.iohao.example.room.data.MyRoom;
import com.iohao.example.room.data.MyRule;
import com.iohao.game.common.kit.attr.AttrOption;
import com.iohao.game.widget.light.room.Room;
import com.iohao.game.widget.light.room.RoomService;
import com.iohao.game.widget.light.room.flow.RoomCreateContext;
import com.iohao.game.widget.light.room.flow.RoomCreator;
import com.iohao.game.widget.light.room.operation.OperationFactory;
import com.iohao.game.widget.light.room.operation.OperationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@Slf4j
@Getter
public final class MyRoomService implements RoomService, OperationService, RoomCreator {

    final Map<Long, Room> roomMap = new NonBlockingHashMap<>();
    final Map<Long, Long> userRoomMap = new NonBlockingHashMap<>();
    final OperationFactory operationFactory = OperationFactory.of();

    final AtomicLong roomIdCounter = new AtomicLong(1);


    public AttrOption<MyRule> ruleAttr = AttrOption.valueOf("rule");

    @Override
    public MyRoom createRoom(RoomCreateContext createContext) {
        var room = new MyRoom();
        room.setRoomId(roomIdCounter.getAndIncrement());
        room.setRoomCreateContext(createContext);
        room.setSpaceSize(createContext.getSpaceSize());
        room.setOperationService(this);
        return room;
    }

    private MyRoomService() {
    }

    public static MyRoomService me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final MyRoomService ME = new MyRoomService();
    }
}
