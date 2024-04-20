/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.tank.room.flow;

import com.iohao.game.widget.light.room.AbstractRoom;
import com.iohao.game.widget.light.room.CreateRoomInfo;
import com.iohao.game.widget.light.room.flow.RoomCreateCustom;
import com.iohao.game.collect.tank.room.TankRoomEntity;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 坦克 房间创建
 *
 * @author 渔民小镇
 * @date 2022-01-14
 */
public class TankRoomCreateCustom implements RoomCreateCustom {
    AtomicLong roomIdAtomic = new AtomicLong(0);

    @Override
    @SuppressWarnings("unchecked")
    public AbstractRoom createRoom(CreateRoomInfo createRoomInfo) {
        long roomId = roomIdAtomic.incrementAndGet();

        TankRoomEntity room = new TankRoomEntity();
        room.setRoomId(roomId);

        return room;
    }
}
