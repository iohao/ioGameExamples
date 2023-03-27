/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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