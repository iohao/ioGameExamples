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
package com.iohao.game.example.broadcast.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.CommunicationAggregationContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.action.skeleton.kit.RangeBroadcast;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;

import java.util.List;
import java.util.Set;

/**
 * @author 渔民小镇
 * @date 2024-05-22
 */
@ActionController(DemoBroadcastCmd.cmd)
public class RangeBroadcastAction {

    @ActionMethod(DemoBroadcastCmd.testRangeBroadcast)
    public void testRangeBroadcast(FlowContext flowContext) {

        CmdInfo cmdInfo = flowContext.getCmdInfo();

        // ------------ object ------------
        // 全服广播 - 广播单个对象
        DemoBroadcastMessage message = new DemoBroadcastMessage();
        message.msg = "helloBroadcast --- 1";
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessage(cmdInfo, message)
                .execute();

        List<DemoBroadcastMessage> messageList = List.of(message);
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessageList(cmdInfo, messageList)
                .execute()
        ;

        // ------------ int ------------

        // 全服广播 - 广播 int
        int intValue = 1;
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessage(cmdInfo, intValue)
                .execute();

        // 全服广播 - 广播 int list
        List<Integer> intValueList = List.of(1, 2);
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessageIntList(cmdInfo, intValueList)
                .execute();

        // ------------ long ------------

        // 全服广播 - 广播 long
        long longValue = 1L;
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessage(cmdInfo, longValue)
                .execute();

        // 全服广播 - 广播 long list
        List<Long> longValueList = List.of(1L, 2L);
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessageLongList(cmdInfo, longValueList)
                .execute();

        // ------------ String ------------

        // 全服广播 - 广播 String
        String stringValue = "1";
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessage(cmdInfo, stringValue)
                .execute();

        // 全服广播 - 广播 String list
        List<String> stringValueList = List.of("1L", "2L");
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessageStringList(cmdInfo, stringValueList)
                .execute();

        // ------------ boolean ------------

        // 全服广播 - 广播 boolean
        boolean boolValue = true;
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessage(cmdInfo, boolValue)
                .execute();

        // 全服广播 - 广播 boolean list
        List<Boolean> boolValueList = List.of(true, false);
        new MyGlobalRangeBroadcast(flowContext)
                .setResponseMessageBoolList(cmdInfo, boolValueList)
                .execute();
    }

    static class MyGlobalRangeBroadcast extends RangeBroadcast {

        public MyGlobalRangeBroadcast(FlowContext flowContext) {
            this(flowContext.option(FlowAttr.aggregationContext));
        }

        public MyGlobalRangeBroadcast(CommunicationAggregationContext aggregationContext) {
            super(aggregationContext);
        }

        @Override
        protected void broadcast() {
            ResponseMessage responseMessage = this.getResponseMessage();

            Set<Long> userIds = this.getUserIds();

            if (userIds.isEmpty()) {
                // 全服广播
                this.getAggregationContext().broadcast(responseMessage);
            } else {
                // 指定玩家广播
                this.getAggregationContext().broadcast(responseMessage, this.getUserIds());
            }
        }
    }
}
