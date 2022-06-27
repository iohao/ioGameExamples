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
package com.iohao.game.example.interaction.same.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.example.common.msg.RoomNumMsg;
import com.iohao.game.example.interaction.same.room.action.DemoCmdForRoom;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-05-22
 */
@Slf4j
@ActionController(DemoCmdForHall.cmd)
public class DemoHallAction {
    @ActionMethod(DemoCmdForHall.count)
    public void count(FlowContext flowContext) {
        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(DemoCmdForRoom.cmd, DemoCmdForRoom.countRoom);
        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        ResponseCollectMessage responseCollectMessage = flowContext.invokeModuleCollectMessage(cmdInfo);
        // 每个逻辑服返回的数据集合
        List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();

        for (ResponseCollectItemMessage responseCollectItemMessage : messageList) {
            ResponseMessage responseMessage = responseCollectItemMessage.getResponseMessage();
            // 得到房间逻辑服返回的业务数据
            RoomNumMsg decode = DataCodecKit.decode(responseMessage.getData(), RoomNumMsg.class);
            log.info("responseCollectItemMessage : {} ", decode);
        }
    }
}
