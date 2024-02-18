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
package com.iohao.game.example.interaction.same.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.example.common.msg.RoomNumMsg;
import com.iohao.game.example.interaction.same.InteractionSameKit;
import com.iohao.game.example.interaction.same.room.action.DemoCmdForRoom;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2022-05-22
 */
@Slf4j
@ActionController(DemoCmdForHall.cmd)
public class DemoHallAction {
    @ActionMethod(DemoCmdForHall.count)
    public void count(FlowContext flowContext) {
        // 模块通讯上下文
        InvokeModuleContext invokeModuleContext = BrokerClientHelper.getInvokeModuleContext();
        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.of(DemoCmdForRoom.cmd, DemoCmdForRoom.countRoom);

        //        extractedSync(invokeModuleContext, cmdInfo);

        int loop = 24;
        loop = 1;
        for (int i = 0; i < loop; i++) {
//            extractedVirtualCallback(flowContext, cmdInfo);

            extractedOneSync(flowContext, cmdInfo);
        }
    }

    private static void extractedOneSync(FlowContext flowContext, CmdInfo cmdInfo) {

        log.info("开始请求 : {}", cmdInfo);
        flowContext.invokeModuleMessageAsync(cmdInfo, responseMessage -> {
            // inc counter
            InteractionSameKit.count.increment();
            RoomNumMsg roomNumMsg = responseMessage.getData(RoomNumMsg.class);
            log.info("异步回调 : {}", roomNumMsg.roomCount);
        });
    }

    private void extractedVirtualCallback(FlowContext flowContext, CmdInfo cmdInfo) {
        // 回调写法 -- 完全无阻塞

        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        flowContext.invokeModuleCollectMessageAsync(cmdInfo, responseCollectMessage -> {
            // 回调写法
//            log.info("responseCollectMessage : {}", responseCollectMessage);
            // 每个逻辑服返回的数据集合
//            print(responseCollectMessage);

            InteractionSameKit.count.increment();
        });
    }

    private static void print(ResponseCollectMessage responseCollectMessage) {
        System.out.println();

        List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();
        for (ResponseCollectItemMessage responseCollectItemMessage : messageList) {
            ResponseMessage responseMessage = responseCollectItemMessage.getResponseMessage();
            // 得到房间逻辑服返回的业务数据
            RoomNumMsg decode = DataCodecKit.decode(responseMessage.getData(), RoomNumMsg.class);
            log.info("responseCollectItemMessage : {} ", decode);
        }
    }

    private void extractedSync(InvokeModuleContext invokeModuleContext, CmdInfo cmdInfo) {
        // 同步写法

        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        ResponseCollectMessage responseCollectMessage = invokeModuleContext.invokeModuleCollectMessage(cmdInfo);
        // 每个逻辑服返回的数据集合
        print(responseCollectMessage);
    }

    @ActionMethod(DemoCmdForHall.testCount)
    public int testCount() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}
