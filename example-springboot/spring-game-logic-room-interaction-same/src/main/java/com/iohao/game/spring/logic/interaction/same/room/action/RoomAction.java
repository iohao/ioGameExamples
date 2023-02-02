/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
