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
package com.iohao.example.room;

import com.iohao.example.room.action.MyRoomAction;
import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.core.exception.MsgExceptionInfo;
import com.iohao.game.action.skeleton.core.flow.ActionAfter;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.action.skeleton.core.flow.internal.DebugInOut;
import com.iohao.game.action.skeleton.core.flow.internal.DefaultActionAfter;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@Slf4j
public final class MyRoomLogicStartup extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        var config = new BarSkeletonBuilderParamConfig()
                .scanActionPackage(MyRoomAction.class);

        var builder = config.createBuilder();
        builder.addInOut(new DebugInOut());
        extractedActionAfter(builder);

        builder.addRunner(new MyOperationConfigRunner());

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("MyRoomGameLogic");
        return builder;
    }

    private static void extractedActionAfter(BarSkeletonBuilder builder) {
        builder.setActionAfter(new ActionAfter() {
            final ActionAfter actionAfter = new DefaultActionAfter();

            @Override
            public void execute(FlowContext flowContext) {
                final ResponseMessage response = flowContext.getResponse();
                if (response.hasError()) {
                    String msg = flowContext.option(FlowAttr.msgException);
                    response.setValidatorMsg(msg);

                    if (flowContext.getMethodResult() instanceof MsgException e) {
                        log.error(e.getMessage(), e);
                    }
                }

                actionAfter.execute(flowContext);
            }
        });
    }
}
