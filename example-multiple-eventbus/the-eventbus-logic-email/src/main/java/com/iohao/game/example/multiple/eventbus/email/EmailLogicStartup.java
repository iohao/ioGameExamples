/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.multiple.eventbus.email;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.eventbus.AbstractEventBusRunner;
import com.iohao.game.action.skeleton.eventbus.EventBus;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.example.multiple.eventbus.common.bar.BarSkeletonKit;
import com.iohao.game.example.multiple.eventbus.email.subscriber.EmailAnyEventBusSubscriber;
import com.iohao.game.example.multiple.eventbus.email.subscriber.EmailEventBusSubscriber;
import com.iohao.game.example.multiple.eventbus.email.subscriber.EmailOrderEventBusSubscriber;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@Slf4j
public class EmailLogicStartup extends AbstractBrokerClientStartup {
    final String appName;
    final String appId;
    final String tag = "email 逻辑服";

    public EmailLogicStartup(String id) {
        appName = tag + "-" + id;
        appId = tag + "-" + id;
    }

    public EmailLogicStartup() {
        this("1");
    }

//    public static void main(String[] args) {
//        EmailLogicStartup emailLogicStartup = new EmailLogicStartup();
//        // 启动游戏逻辑服
//        BrokerClientApplication.start(emailLogicStartup);
//    }

    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        var config = new BarSkeletonBuilderParamConfig()
                // 扫描 action 类所在包
                .scanActionPackage(EmailAction.class);

        // 业务框架构建器
        var builder = config.createBuilder();

        BarSkeletonKit.inOut(builder);

        // email 逻辑服添加 EventBusRunner，用于处理 EventBus 相关业务
        builder.addRunner(new AbstractEventBusRunner() {
            @Override
            public void registerEventBus(EventBus eventBus, BarSkeleton skeleton) {
                // email 逻辑服的订阅者
                eventBus.register(new EmailEventBusSubscriber());
                eventBus.register(new EmailOrderEventBusSubscriber());
                eventBus.register(new EmailAnyEventBusSubscriber());

                // 收集 action 统计相关
                BarSkeletonKit.eventStatActionInOut(skeleton, eventBus, appId, appName);
            }
        });

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        return BrokerClient.newBuilder()
                .appName(appName)
                .tag(tag)
                .id(appId);
    }
}
