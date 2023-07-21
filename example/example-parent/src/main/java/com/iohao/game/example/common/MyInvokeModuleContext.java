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
package com.iohao.game.example.common;

import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-08-20
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyInvokeModuleContext implements InvokeModuleContext {

    FlowContext flowContext;

    public static InvokeModuleContext of(FlowContext flowContext) {
        return new MyInvokeModuleContext(flowContext);
    }

    @Override
    public void invokeModuleVoidMessage(RequestMessage requestMessage) {
        employ(requestMessage);

        BrokerClientHelper
                .getInvokeModuleContext()
                .invokeModuleVoidMessage(requestMessage);
    }

    @Override
    public ResponseMessage invokeModuleMessage(RequestMessage requestMessage) {
        employ(requestMessage);

        return BrokerClientHelper
                .getInvokeModuleContext()
                .invokeModuleMessage(requestMessage);
    }

    @Override
    public ResponseCollectMessage invokeModuleCollectMessage(RequestMessage requestMessage) {
        employ(requestMessage);

        return BrokerClientHelper
                .getInvokeModuleContext()
                .invokeModuleCollectMessage(requestMessage);
    }

    private void employ(RequestMessage requestMessage) {
        // RequestMessage 把userId、headMetadata 添加上
        HeadMetadata headMetadata = flowContext.getRequest().getHeadMetadata();
        requestMessage.getHeadMetadata()
                .setUserId(flowContext.getUserId())
                .setAttachmentData(headMetadata.getAttachmentData())
                .setChannelId(headMetadata.getChannelId())
        ;
    }

    private MyInvokeModuleContext(FlowContext flowContext) {
        this.flowContext = flowContext;
    }
}