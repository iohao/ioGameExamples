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
package com.iohao.game.example.multiple.eventbus.user;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.eventbus.EventBusRunner;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.processor.listener.BrokerClientListener;
import com.iohao.game.bolt.broker.core.message.BrokerClientModuleMessage;
import com.iohao.game.example.multiple.eventbus.common.bar.BarSkeletonKit;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
public class UserLogicStartup extends AbstractBrokerClientStartup {
    public static void main(String[] args) {
        UserLogicStartup userLogicStartup = new UserLogicStartup();
        // 启动游戏逻辑服
        BrokerClientApplication.start(userLogicStartup);
    }

    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        var config = new BarSkeletonBuilderParamConfig()
                // 扫描 action 类所在包
                .scanActionPackage(UserAction.class);

        // 业务框架构建器
        var builder = config.createBuilder();

        BarSkeletonKit.inOut(builder);

        builder.addRunner((EventBusRunner) (eventBus, skeleton) -> {
            // user 逻辑服的订阅者
            eventBus.register(new UserEventBusSubscriber());
        });

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("eventbus-user-游戏逻辑服");

        builder.addListener(new BrokerClientListener() {
            @Override
            public void registerBefore(BrokerClientModuleMessage moduleMessage, BrokerClient client) {
                moduleMessage.addHeader("name", "i'm user logic");
            }
        });

        return builder;
    }
}
