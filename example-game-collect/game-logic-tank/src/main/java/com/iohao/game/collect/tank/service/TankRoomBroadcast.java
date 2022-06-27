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
package com.iohao.game.collect.tank.service;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.collect.proto.tank.TankBroadcastMessage;
import com.iohao.game.collect.tank.TankCmd;
import com.iohao.game.common.kit.ExecutorKit;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 广播演示
 *
 * @author 渔民小镇
 * @date 2022-05-18
 */
@Slf4j
public class TankRoomBroadcast {


    private TankRoomBroadcast() {

    }

    LongAdder counter = new LongAdder();

    public void start() {
        final ScheduledExecutorService scheduled = ExecutorKit.newSingleScheduled("房间内广播测试");

        scheduled.scheduleAtFixedRate(() -> {

            String msg = "broadcast hello ，" + counter.longValue();
            counter.increment();

            TankBroadcastMessage broadcastMessage = new TankBroadcastMessage();
            broadcastMessage.setMsg(msg);

            log.info("广播： {}", broadcastMessage);

            CmdInfo cmdInfo = CmdInfo.getCmdInfo(TankCmd.cmd, TankCmd.testBroadcasts);

            BroadcastContext broadcastContext = BrokerClientHelper.me().getBroadcastContext();

            broadcastContext.broadcast(cmdInfo, broadcastMessage);

        }, 3, 5, TimeUnit.SECONDS);

    }

    public static TankRoomBroadcast me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankRoomBroadcast ME = new TankRoomBroadcast();
    }

}
