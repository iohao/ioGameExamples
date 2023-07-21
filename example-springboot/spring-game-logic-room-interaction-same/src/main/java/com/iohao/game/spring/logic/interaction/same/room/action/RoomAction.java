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
package com.iohao.game.spring.logic.interaction.same.room.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.commumication.BrokerClientContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.spring.common.cmd.RoomCmdModule;
import com.iohao.game.spring.common.pb.OtherVerify;
import com.iohao.game.spring.common.pb.RoomNumPb;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 房间相关
 *
 * @author 渔民小镇
 * @date 2022-07-30
 */
@Slf4j
@ActionController(RoomCmdModule.cmd)
public class RoomAction {
    /**
     * 统计房间的数量
     *
     * @return 房间的数量
     */
    @ActionMethod(RoomCmdModule.countRoom)
    public RoomNumPb countRoom() {

        // 得到 1 ~ 100 的随机数。
        int anInt = ThreadLocalRandom.current().nextInt(1, 100);

        // 当前房间的数量
        RoomNumPb roomNumMsg = new RoomNumPb();
        // 随机数来表示房间的数量
        roomNumMsg.roomCount = anInt;

        return roomNumMsg;
    }

    @ActionMethod(RoomCmdModule.helloRoom)
    public OtherVerify helloRoom(FlowContext flowContext) {
        BrokerClient brokerClient = (BrokerClient) flowContext.option(FlowAttr.brokerClientContext);
        String id = brokerClient.getId();
        log.info("id : {}", id);

        // 随意测试的方法
        OtherVerify otherVerify = new OtherVerify();
        otherVerify.jwt = id;

        return otherVerify;
    }
}
