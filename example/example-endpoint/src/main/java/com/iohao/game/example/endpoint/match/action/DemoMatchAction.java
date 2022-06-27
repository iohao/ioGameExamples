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
package com.iohao.game.example.endpoint.match.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.ProcessorContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.action.skeleton.protocol.processor.EndPointLogicServerMessage;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
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
        int newUserId = loginVerify.jwt.hashCode();

        // 登录的关键代码
        // 具体可参考 https://www.yuque.com/iohao/game/tywkqv
        boolean success = UserIdSettingKit.settingUserId(flowContext, newUserId);

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
        // 匹配请求
        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(DemoCmdForEndPointRoom.cmd, DemoCmdForEndPointRoom.countRoom);
        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        ResponseCollectMessage responseCollectMessage = flowContext.invokeModuleCollectMessage(cmdInfo);

        // 选出房间数最少的房间逻辑服
        ResponseCollectItemMessage minItemMessage = getMinItemMessage(responseCollectMessage);
        String logicServerId = minItemMessage.getLogicServerId();

        //
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
                // 需要绑定的逻辑服id
                .setLogicServerId(logicServerId)
                // true 为绑定，false 为取消绑定
                .setBinding(true);

        // 发送消息到网关
        ProcessorContext processorContext = BrokerClientHelper.me().getProcessorContext();
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
