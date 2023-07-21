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
            broadcastMessage.msg = msg;

            log.info("广播： {}", broadcastMessage);

            CmdInfo cmdInfo = CmdInfo.getCmdInfo(TankCmd.cmd, TankCmd.testBroadcasts);

            BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();

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
