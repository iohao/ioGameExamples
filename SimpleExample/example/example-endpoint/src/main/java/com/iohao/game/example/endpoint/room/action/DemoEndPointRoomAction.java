/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.endpoint.room.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.common.kit.RandomKit;
import com.iohao.game.example.common.msg.DemoOperation;
import com.iohao.game.example.common.msg.RoomNumMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * 动态绑定逻辑服节点-房间相关
 *
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
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
        int anInt = RandomKit.random(1, 100);

        // 当前房间数量
        RoomNumMsg roomNumMsg = new RoomNumMsg();
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
    public DemoOperation operation(DemoOperation demoOperation, FlowContext flowContext) {

        longAdder.increment();

        DemoOperation operation = new DemoOperation();
        operation.name = String.format("%s-%s",
                demoOperation.name,
                longAdder.longValue());

        return operation;
    }
}
