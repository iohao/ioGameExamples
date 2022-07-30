/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.spring.logic.interaction.same.room.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.spring.common.SpringCmdModule;
import com.iohao.game.spring.common.pb.RoomNumPb;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 渔民小镇
 * @date 2022-07-30
 */
@ActionController(SpringCmdModule.RoomCmd.cmd)
public class RoomAction {
    @ActionMethod(SpringCmdModule.RoomCmd.countRoom)
    public RoomNumPb countRoom() {

        // 得到 1 ~ 100 的随机数。
        int anInt = ThreadLocalRandom.current().nextInt(1, 100);

        // 当前房间的数量
        RoomNumPb roomNumMsg = new RoomNumPb();
        // 随机数来表示房间的数量
        roomNumMsg.roomCount = anInt;

        return roomNumMsg;
    }
}
