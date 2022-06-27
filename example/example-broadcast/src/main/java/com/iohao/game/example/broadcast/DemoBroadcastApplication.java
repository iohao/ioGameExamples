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
package com.iohao.game.example.broadcast;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.bolt.broker.client.external.config.ExternalGlobalConfig;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.example.broadcast.server.DemoBroadcastServer;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;
import com.iohao.game.simple.SimpleHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 广播示例
 *
 * @author 渔民小镇
 * @date 2022-05-18
 */
public class DemoBroadcastApplication {
    public static void main(String[] args) {
        // 注意，这个是临时测试用的，设置为 false 表示不用登录就可以访问逻辑服的方法
        ExternalGlobalConfig.verifyIdentity = false;

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        var demoLogicServer = new DemoBroadcastServer();

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        SimpleHelper.run(port, List.of(demoLogicServer));

        // 启动广播 Scheduled，每5秒广播一次消息给客户端
        broadcastScheduled();

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/qv4qfo
         */
    }

    private static void broadcastScheduled() {
        LongAdder counter = new LongAdder();

        ExecutorKit.newSingleScheduled("广播测试").scheduleAtFixedRate(() -> {

            counter.increment();

            DemoBroadcastMessage broadcastMessage = new DemoBroadcastMessage();
            broadcastMessage.msg = "broadcast hello ，" + counter.longValue();

            // 广播消息的路由
            CmdInfo cmdInfo = CmdInfo.getCmdInfo(DemoBroadcastCmd.cmd, DemoBroadcastCmd.broadcastMsg);

            // 广播上下文
            BroadcastContext broadcastContext = BrokerClientHelper.me().getBroadcastContext();

            // 广播
            broadcastContext.broadcast(cmdInfo, broadcastMessage);

        }, 3, 5, TimeUnit.SECONDS);
    }
}
