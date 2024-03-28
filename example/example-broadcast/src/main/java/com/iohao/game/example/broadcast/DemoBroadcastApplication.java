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

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;

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

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        var demoLogicServer = new DemoBroadcastServer();

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        NettySimpleHelper.run(port, List.of(demoLogicServer));

        // 启动广播 Scheduled，每5秒广播一次消息给客户端
        broadcastScheduled();

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/qv4qfo
         */
    }

    private static void broadcastScheduled() {
        LongAdder counter = new LongAdder();

        Runnable runnable = () -> {
            counter.increment();

            DemoBroadcastMessage broadcastMessage = new DemoBroadcastMessage();
            broadcastMessage.msg = "broadcast hello ，" + counter.longValue();

            // 广播消息的路由
            CmdInfo cmdInfo = CmdInfo.of(DemoBroadcastCmd.cmd, DemoBroadcastCmd.broadcastMsg);
            // 广播上下文
            BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();
            // 广播
            broadcastContext.broadcast(cmdInfo, broadcastMessage);
        };

        TaskKit.runInterval(runnable::run, 5, TimeUnit.SECONDS);
    }
}
