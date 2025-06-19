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
package com.iohao.example.sdk.logic;

import com.iohao.example.sdk.logic.action.SdkAction;
import com.iohao.example.sdk.logic.action.SdkCmd;
import com.iohao.example.sdk.logic.data.BulletMessage;
import com.iohao.example.sdk.logic.data.UserMessage;
import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.doc.BroadcastDocument;
import com.iohao.game.action.skeleton.core.flow.internal.DebugInOut;
import com.iohao.game.action.skeleton.protocol.wrapper.BoolValue;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;

/**
 * @author 渔民小镇
 * @date 2024-11-01
 */
public final class SdkLogicServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        BarSkeletonBuilder builder = new BarSkeletonBuilderParamConfig()
                .scanActionPackage(SdkAction.class)
                .setBroadcastLog(true)
                .createBuilder();

        extractedDoc(builder);

        builder.addInOut(new DebugInOut());

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("Sdk GameLogic");
        return builder;
    }

    private void extractedDoc(BarSkeletonBuilder builder) {
        builder.addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastBulletMessage)
                // broadcast type
                .setDataClass(BulletMessage.class)
                .setMethodDescription("trigger bullet broadcast; cn:触发子弹广播")
                .setMethodName("bulletBroadcast")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastEmpty)
                .setMethodDescription("test Empty Value")
                .setMethodName("EmptyValue")
        );

        // single value
        builder.addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastInt)
                .setDataClass(int.class, "biz data description")
                .setMethodDescription("IntValue method description")
                .setMethodName("IntValue")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastLong)
                .setDataClass(long.class)
                .setMethodDescription("LongValue")
                .setMethodName("LongValue")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastBool)
                .setDataClass(boolean.class)
                .setMethodDescription("BoolValue")
                .setMethodName("BoolValue")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastString)
                .setDataClass(String.class)
                .setMethodDescription("StringValue")
                .setMethodName("StringValue")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastValue)
                .setDataClass(UserMessage.class)
                .setMethodDescription("UserMessage")
                .setMethodName("UserMessage")
        );

        // list value
        builder.addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastListIntValue)
                .setDataClassList(IntValue.class, "biz id list")
                .setMethodDescription("IntValueList method description")
                .setMethodName("IntValueList")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastListLong)
                .setDataClassList(LongValue.class)
                .setMethodDescription("LongValueList")
                .setMethodName("LongValueList")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastListBool)
                .setDataClassList(BoolValue.class)
                .setMethodDescription("BoolValueList")
                .setMethodName("BoolValueList")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastListString)
                .setDataClassList(StringValue.class)
                .setMethodDescription("StringValueList")
                .setMethodName("StringValueList")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SdkCmd.broadcastListValue)
                .setDataClassList(UserMessage.class)
                .setMethodDescription("UserMessageList")
                .setMethodName("UserMessageList")
        );
    }
}