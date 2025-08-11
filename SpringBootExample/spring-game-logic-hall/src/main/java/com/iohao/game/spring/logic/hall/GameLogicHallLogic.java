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
package com.iohao.game.spring.logic.hall;

import com.iohao.game.MyKit;
import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.flow.MyFlowContext;
import com.iohao.game.action.skeleton.eventbus.EventBus;
import com.iohao.game.action.skeleton.eventbus.EventBusRunner;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.spring.logic.core.MyBarSkeletonConfig;
import com.iohao.game.spring.logic.hall.action.LoginAction;
import com.iohao.game.spring.logic.hall.event.HelloEventHandler;
import com.iohao.game.widget.light.domain.event.DomainEventContext;
import com.iohao.game.widget.light.domain.event.DomainEventContextParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * 大厅游戏逻辑服
 *
 * @author 渔民小镇
 * @date 2022-07-27
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameLogicHallLogic extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        BarSkeletonBuilderParamConfig config = MyBarSkeletonConfig.createBarSkeletonBuilderParamConfig()
                // 扫描 action 类所在包
                .scanActionPackage(LoginAction.class);

        // 业务框架构建器
        BarSkeletonBuilder builder = MyBarSkeletonConfig.createBarSkeletonBuilder(config);
        // 开启 jsr380 验证
        builder.getSetting().setValidator(true);

        builder.setFlowContextFactory(MyFlowContext::new);

        extractedDomainEvent(builder);

        return builder.build();
    }

    private static void extractedDomainEvent(BarSkeletonBuilder builder) {
        builder.addRunner((EventBusRunner) (eventBus, skeleton) -> {
            var helloEventHandler = MyKit.applicationContext.getBean(HelloEventHandler.class);

            // 领域事件上下文参数
            var contextParam = new DomainEventContextParam();
            // 将类添加到领域事件上下文中
            contextParam.addEventHandler(helloEventHandler);

            // 启动事件驱动
            var domainEventContext = new DomainEventContext(contextParam);
            domainEventContext.startup();
        });
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("spring hall 大厅逻辑服");
        return builder;
    }
}
