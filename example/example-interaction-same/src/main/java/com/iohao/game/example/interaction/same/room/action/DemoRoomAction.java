/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.interaction.same.room.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.example.common.msg.RoomNumMsg;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 房间相关
 *
 * @author 渔民小镇
 * @date 2022-05-22
 */
@ActionController(DemoCmdForRoom.cmd)
public class DemoRoomAction {
    @ActionMethod(DemoCmdForRoom.countRoom)
    public RoomNumMsg countRoom() {

        // 得到 1 ~ 100 的随机数。
        int anInt = ThreadLocalRandom.current().nextInt(1, 100);

        // 当前房间数量
        RoomNumMsg roomNumMsg = new RoomNumMsg();
        // 随机数来表示房间的数量
        roomNumMsg.roomCount = anInt;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return roomNumMsg;
    }
}
