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
package com.iohao.game.example.broadcast;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;

/**
 * @author 渔民小镇
 * @date 2023-03-13
 */
@ActionController(DemoBroadcastCmd.cmd)
public class BroadcastMessageAction {
    @ActionMethod(DemoBroadcastCmd.helloBroadcast1)
    public void helloBroadcast1(FlowContext flowContext) {
        DemoBroadcastMessage message = new DemoBroadcastMessage();
        message.msg = "helloBroadcast --- 1";

        // 广播消息的路由
        CmdInfo cmdInfo = CmdInfo.of(DemoBroadcastCmd.cmd, DemoBroadcastCmd.broadcastMsg);

        /*
         * 将方法声明为 void，使用推送（广播）的方式将消息给到请求端。
         * 类似传统的用法
         */
        flowContext.broadcast(cmdInfo, message);
    }
}
