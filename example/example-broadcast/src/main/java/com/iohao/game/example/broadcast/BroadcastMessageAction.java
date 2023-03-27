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
package com.iohao.game.example.broadcast;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;

/**
 * @author 渔民小镇
 * @date 2023-03-13
 */
@ActionController(DemoBroadcastCmd.cmd)
public class BroadcastMessageAction {
    @ActionMethod(DemoBroadcastCmd.helloBroadcast1)
    public void helloBroadcast1() {
        DemoBroadcastMessage message = new DemoBroadcastMessage();
        message.msg = "helloBroadcast --- 1";

        // 广播消息的路由
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(DemoBroadcastCmd.cmd, DemoBroadcastCmd.broadcastMsg);

        // 广播上下文
        BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();

        /*
         * 将方法声明为 void，使用推送（广播）的方式将消息给到请求端。
         * 类似传统的用法
         */
        broadcastContext.broadcast(cmdInfo, message);
    }
}
