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
package com.iohao.game.example.endpoint.match.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.ProcessorContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.action.skeleton.protocol.processor.EndPointLogicServerMessage;
import com.iohao.game.action.skeleton.protocol.processor.EndPointOperationEnum;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.example.common.msg.MatchResponse;
import com.iohao.game.example.common.msg.RoomNumMsg;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.example.endpoint.room.action.DemoCmdForEndPointRoom;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态绑定逻辑服节点-匹配相关
 *
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
@ActionController(DemoCmdForEndPointMatch.cmd)
public class DemoMatchAction {
    /**
     * 登录
     *
     * @param loginVerify 登录请求
     * @param flowContext flowContext
     * @return 玩家数据
     */
    @ActionMethod(DemoCmdForEndPointMatch.loginVerify)
    public DemoUserInfo loginVerify(DemoLoginVerify loginVerify, FlowContext flowContext) {
        // 登录验证请求
        // 为了方便，这里登录用户的id 写个自身传入 jwt 的 hash
        int newUserId = Math.abs(loginVerify.jwt.hashCode());

        // 登录的关键代码
        // 具体可参考 https://www.yuque.com/iohao/game/tywkqv
        boolean success = flowContext.setUserId(newUserId);

        if (!success) {
            log.error("登录错误");
        }

        DemoUserInfo userInfo = new DemoUserInfo();
        userInfo.id = newUserId;
        userInfo.name = loginVerify.jwt;

        return userInfo;
    }

    /**
     * 开始匹配
     *
     * @param flowContext flowContext
     * @return MatchResponse 匹配响应
     */
    @ActionMethod(DemoCmdForEndPointMatch.matching)
    public MatchResponse matching(FlowContext flowContext) {
        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.of(DemoCmdForEndPointRoom.cmd, DemoCmdForEndPointRoom.countRoom);
        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        ResponseCollectMessage responseCollectMessage = flowContext.invokeModuleCollectMessage(cmdInfo);

        // 选出房间数最少的房间逻辑服
        ResponseCollectItemMessage minItemMessage = getMinItemMessage(responseCollectMessage);
        String logicServerId = minItemMessage.getLogicServerId();

        log.info("当前房间数最少的逻辑服是 userId:{} logicServerId:{}", flowContext.getUserId(), logicServerId);

        // 得到当前请求用户
        long userId = flowContext.getUserId();
        // 添加需要绑定的用户（玩家）
        List<Long> userIdList = new ArrayList<>();
        userIdList.add(userId);

        // 绑定消息
        EndPointLogicServerMessage endPointLogicServerMessage = new EndPointLogicServerMessage()
                // 需要绑定的玩家，示例中只取了当前请求匹配的玩家
                .setUserList(userIdList)
                /*
                 * 添加需要绑定的逻辑服id，下面绑定了两个；
                 * 1.给绑定一个房间游戏逻辑服的 id
                 * 2.绑定 animal 游戏逻辑服就简单点，固定写 id 为 2-1 的；
                 * （也就是我们启动两台 animal 游戏逻辑服中的一台）
                 */
                .addLogicServerId(logicServerId)
                .addLogicServerId("2-1")
                // 覆盖绑定游戏逻辑服
                .setOperation(EndPointOperationEnum.COVER_BINDING);

        // 发送消息到网关
        ProcessorContext processorContext = BrokerClientHelper.getProcessorContext();
        processorContext.invokeOneway(endPointLogicServerMessage);

        // 将匹配结果发送给用户
        MatchResponse matchResponse = new MatchResponse();
        matchResponse.matchSuccess = true;
        return matchResponse;
    }

    ResponseCollectItemMessage getMinItemMessage(ResponseCollectMessage responseCollectMessage) {
        // 每个逻辑服返回的数据集合
        List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();

        ResponseCollectItemMessage minMessage = null;
        for (ResponseCollectItemMessage itemMessage : messageList) {

            if (minMessage == null) {
                minMessage = itemMessage;
                continue;
            }

            RoomNumMsg roomNumMsg1 = itemMessage.getData(RoomNumMsg.class);

            RoomNumMsg roomNumMsgMin = minMessage.getData(RoomNumMsg.class);

            // 得到最少房间的逻辑服信息
            if (roomNumMsgMin.roomCount > roomNumMsg1.roomCount) {
                minMessage = itemMessage;
            }
        }

        return minMessage;
    }
}
