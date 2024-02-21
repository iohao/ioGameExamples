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
package com.iohao.game.example.endpoint.room.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.example.common.msg.DemoOperation;
import com.iohao.game.example.common.msg.RoomNumMsg;
import com.iohao.game.example.endpoint.animal.action.DemoCmdForEndPointAnimal;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
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
    public DemoOperation operation(DemoOperation demoOperation, FlowContext flowContext) {
        CmdInfo cmdInfo = CmdInfo.of(
                DemoCmdForEndPointAnimal.cmd, DemoCmdForEndPointAnimal.randomAnimal
        );

        // 跨服访问，逻辑服与逻辑服之间相互通信
        ResponseMessage responseMessage = flowContext.invokeModuleMessage(cmdInfo);
        StringValue stringValue = responseMessage.getData(StringValue.class);

        longAdder.increment();
        DemoOperation operation = new DemoOperation();
        operation.name = String.format("%s , %s-%s",
                demoOperation.name,
                stringValue,
                longAdder.longValue());

        return operation;
    }
}
