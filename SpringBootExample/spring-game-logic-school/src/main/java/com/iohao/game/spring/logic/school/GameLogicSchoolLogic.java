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
package com.iohao.game.spring.logic.school;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.doc.BroadcastDocument;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.spring.common.cmd.OtherSchoolCmdModule;
import com.iohao.game.spring.common.cmd.SchoolCmdModule;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.common.pb.SpringBroadcastMessagePb;
import com.iohao.game.spring.logic.core.MyBarSkeletonConfig;
import com.iohao.game.spring.logic.school.action.SchoolAction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * 学校游戏逻辑服
 *
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameLogicSchoolLogic extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        BarSkeletonBuilderParamConfig config = MyBarSkeletonConfig.createBarSkeletonBuilderParamConfig()
                // 扫描 action 类所在包
                .scanActionPackage(SchoolAction.class);

        // 业务框架构建器
        BarSkeletonBuilder builder = MyBarSkeletonConfig.createBarSkeletonBuilder(config);

        // 开启 jsr380 验证
        builder.getSetting().setValidator(true);

        extractedDoc(builder);

        return builder.build();
    }

    private static void extractedDoc(BarSkeletonBuilder builder) {
        builder.addBroadcastDocument(BroadcastDocument.newBuilder(OtherSchoolCmdModule.of(OtherSchoolCmdModule.longValueWithBroadcastData))
                .setDataClass(SchoolPb.class)
                .setMethodDescription("longValueWithBroadcastData")
        ).addBroadcastDocument(BroadcastDocument.newBuilder(SchoolCmdModule.of(SchoolCmdModule.broadcastData))
                .setDataClass(SpringBroadcastMessagePb.class)
                .setMethodDescription("广播消息PB")
        );


    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("spring school 学校游戏逻辑服");
        return builder;
    }
}
