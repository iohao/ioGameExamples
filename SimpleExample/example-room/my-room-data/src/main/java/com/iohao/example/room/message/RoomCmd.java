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
package com.iohao.example.room.message;

import com.iohao.game.action.skeleton.core.CmdInfo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
public interface RoomCmd {
    int cmd = 1;
    int login = 0;
    int operation = 1;

    int createRoom = 2;
    int enterRoom = 3;
    int quitRoom = 4;
    int ready = 5;
    int listRoomId = 6;

    /** broadcastIndex. cn:广播起始 */
    AtomicInteger inc = new AtomicInteger(50);

    CmdInfo readyBroadcast = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo enterRoomBroadcast = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo quitRoomBroadcast = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo operationBroadcast = CmdInfo.of(cmd, inc.getAndIncrement());
}
