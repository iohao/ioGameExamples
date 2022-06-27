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
package com.iohao.game.example.endpoint.room.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.example.common.msg.DemoOperation;
import com.iohao.game.example.common.msg.RoomNumMsg;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

/**
 * 动态绑定逻辑服节点-房间相关
 *
 * @author 渔民小镇
 * @date 2022-05-28
 */
@ActionController(DemoCmdForEndPointRoom.cmd)
public class DemoEndPointRoomAction {

    static LongAdder longAdder = new LongAdder();

    /**
     * 统计房间数
     *
     * @return RoomNumMsg
     */
    @ActionMethod(DemoCmdForEndPointRoom.countRoom)
    public RoomNumMsg countRoom() {

        // 得到 1 ~ 100 的随机数。
        int anInt = ThreadLocalRandom.current().nextInt(1, 100);

        // 当前房间数量
        RoomNumMsg roomNumMsg = new RoomNumMsg();
        // 随机数来表示房间的数量
        roomNumMsg.roomCount = anInt;

        return roomNumMsg;
    }

    /**
     * 房间内的操作
     *
     * @param demoOperation 操作请求
     * @return DemoOperation
     */
    @ActionMethod(DemoCmdForEndPointRoom.operation)
    public DemoOperation operation(DemoOperation demoOperation) {
        longAdder.increment();

        DemoOperation operation = new DemoOperation();
        operation.name = demoOperation.name + ", I'm endPoint here " + longAdder.longValue();
        return operation;
    }
}
