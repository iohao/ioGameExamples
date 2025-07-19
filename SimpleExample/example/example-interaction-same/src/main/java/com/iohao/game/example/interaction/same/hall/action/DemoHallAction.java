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
package com.iohao.game.example.interaction.same.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.example.common.msg.RoomNumMsg;
import com.iohao.game.example.interaction.same.room.action.DemoCmdForRoom;
import com.iohao.game.example.interaction.same.room.action.DemoRoomAction;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2022-05-22
 */
@Slf4j
@ActionController(DemoHallCmd.cmd)
public class DemoHallAction {
    @ActionMethod(DemoHallCmd.count)
    public void count(FlowContext flowContext) {
        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.of(DemoCmdForRoom.cmd, DemoCmdForRoom.countRoom);

        // 异步回调 - 无阻塞
        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        flowContext.invokeModuleCollectMessageAsync(cmdInfo, responseCollectMessage -> {
            // 每个逻辑服返回的数据集合
            List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();

            for (ResponseCollectItemMessage itemMessage : messageList) {
                // 得到房间逻辑服返回的业务数据
                RoomNumMsg roomNumMsg = itemMessage.getData(RoomNumMsg.class);
                log.info("{}", roomNumMsg);
            }

            log.info("end: {}", messageList.size());
            DemoRoomAction.flag.set(false);
        });
    }

    @ActionMethod(DemoHallCmd.testCount)
    public int testCount() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}
