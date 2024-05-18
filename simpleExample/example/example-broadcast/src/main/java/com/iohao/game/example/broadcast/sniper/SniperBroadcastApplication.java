/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
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
package com.iohao.game.example.broadcast.sniper;

import com.iohao.game.action.skeleton.core.BarMessageKit;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValueList;
import com.iohao.game.action.skeleton.protocol.wrapper.WrapperKit;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.common.kit.HashKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.broadcast.DemoBroadcastCmd;
import com.iohao.game.example.broadcast.DemoBroadcastServer;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.broker.client.ExternalBrokerClientStartup;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2023-06-08
 */
@Slf4j
public class SniperBroadcastApplication {
    public static void main(String[] args) {

        int externalPort = 10100;

        // 给游戏对外服指定一个 id
        String externalId = "0-1";
        ExternalBrokerClientStartup externalBrokerClientStartup = new ExternalBrokerClientStartup();
        externalBrokerClientStartup.setId(externalId);

        ExternalServer externalServer = DefaultExternalServer
                .newBuilder(externalPort)
                .externalBrokerClientStartup(externalBrokerClientStartup)
                .build();

        new NettyRunOne()
                .setExternalServer(externalServer)
                .setLogicServerList(List.of(new DemoBroadcastServer()))
                .startup()
        ;

        // 通过这个配置来测试
        IoGameGlobalConfig.brokerSniperToggleAK47 = false;
        extracted(externalId);
    }


    private static void extracted(String externalId) {
        int sourceClientId = HashKit.hash32(externalId);
        LongAdder counter = new LongAdder();

        Runnable runnable = () -> {
            counter.increment();

            DemoBroadcastMessage broadcastMessage = new DemoBroadcastMessage();
            broadcastMessage.msg = "broadcast hello，" + counter.longValue();

            // 广播消息的路由
            CmdInfo cmdInfo = CmdInfo.of(DemoBroadcastCmd.cmd, DemoBroadcastCmd.broadcastMsg);
            ResponseMessage responseMessage = BarMessageKit.createResponseMessage(cmdInfo, broadcastMessage);
            // 指定游戏对外服广播
            HeadMetadata headMetadata = responseMessage.getHeadMetadata();
            headMetadata.setSourceClientId(sourceClientId);

            if (counter.sum() % 2 == 0) {
                headMetadata.setSourceClientId(sourceClientId + 1);
            }

            // 广播上下文
            BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();
            broadcastContext.broadcast(responseMessage);
        };

        // 广播测试
        TaskKit.runInterval(runnable::run, 5, TimeUnit.SECONDS);
    }

    private static void test() {
        // 给客户端广播一个 int 值 : 1
        var bizData = WrapperKit.of(1);

        // 广播上下文
        CmdInfo cmdInfo = CmdInfo.of(DemoBroadcastCmd.cmd, DemoBroadcastCmd.broadcastMsg);
        BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();
        broadcastContext.broadcast(cmdInfo, bizData);

        // 给客户端广播一个 bool 值 : true
        var bizDataBoolean = WrapperKit.of(true);
        broadcastContext.broadcast(cmdInfo, bizDataBoolean);

        // 对象列表演示
        DemoBroadcastMessage broadcastMessage = new DemoBroadcastMessage();
        broadcastMessage.msg = "broadcast hello，Wrapper!";
        List<DemoBroadcastMessage> list = new ArrayList<>();
        list.add(broadcastMessage);
        var bizDataList = WrapperKit.ofListByteValue(list);
        broadcastContext.broadcast(cmdInfo, bizDataList);

        // int 列表
        var bizDataIntList = IntValueList.of(List.of(1, 3, 5, 7));
        broadcastContext.broadcast(cmdInfo, bizDataIntList);

//        ... ... 省略部分代码
        // 其他类同，不全部介绍了。
    }

}
