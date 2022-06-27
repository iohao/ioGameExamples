/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.collect.tank.service;

import com.iohao.game.collect.common.FrameKit;
import com.iohao.game.widget.light.room.RoomService;
import com.iohao.game.collect.proto.tank.TankRoomBroadcastRes;
import com.iohao.game.collect.tank.action.TankAction;
import com.iohao.game.collect.tank.mapstruct.TankMapstruct;
import com.iohao.game.collect.tank.room.TankPlayerEntity;
import com.iohao.game.collect.tank.room.TankRoomEntity;
import com.iohao.game.common.kit.ExecutorKit;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2022-01-15
 */
public class TankRoomTask {

    RoomService roomService = RoomService.me();

    final ScheduledExecutorService scheduled = ExecutorKit.newSingleScheduled("房间内广播");

    public void init() {

        int framePerMillis = FrameKit.framePerMillis(20);

        scheduled.scheduleAtFixedRate(new TaskRoomStart()
                , 5000
                , framePerMillis
                , TimeUnit.MILLISECONDS);

    }

    static class TaskRoomStart implements Runnable {
        RoomService roomService = RoomService.me();

        @Override
        public void run() {
            // 所有房间
//            Collection<TankRoom> rooms = roomService.listRoom();

            long roomId = TankAction.tempRoomId;
            TankRoomEntity room = roomService.getRoom(roomId);
            if (Objects.isNull(room)) {
                return;
            }

            Collection<TankPlayerEntity> listPlayer = room.listPlayer();
            TankRoomBroadcastRes res = new TankRoomBroadcastRes();
            TankMapstruct.ME.convertList(listPlayer);

            // 转发当前



        }
    }

    public static TankRoomTask me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankRoomTask ME = new TankRoomTask();
    }
}
