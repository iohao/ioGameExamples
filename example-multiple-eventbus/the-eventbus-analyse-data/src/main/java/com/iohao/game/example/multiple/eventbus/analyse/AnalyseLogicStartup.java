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
package com.iohao.game.example.multiple.eventbus.analyse;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.flow.internal.TraceIdInOut;
import com.iohao.game.action.skeleton.eventbus.AbstractEventBusRunner;
import com.iohao.game.action.skeleton.eventbus.EventBus;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;

/**
 * @author 渔民小镇
 * @date 2024-01-07
 */
public class AnalyseLogicStartup extends AbstractBrokerClientStartup {
    public static void main(String[] args) {
        var analyseLogicStartup = new AnalyseLogicStartup();
        // 启动游戏逻辑服
        BrokerClientApplication.start(analyseLogicStartup);

        // 文档 - https://www.yuque.com/iohao/game/gmxz33#KLzvW
        // 阅读小节 - 其他使用案例
    }

    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        var config = new BarSkeletonBuilderParamConfig();

        // 业务框架构建器
        var builder = config.createBuilder();

        // traceId
        TraceIdInOut traceIdInOut = new TraceIdInOut();
        builder.addInOut(traceIdInOut);

        // Analyse 逻辑服添加 EventBusRunner，用于处理 EventBus 相关业务
        builder.addRunner(new AbstractEventBusRunner() {
            @Override
            public void registerEventBus(EventBus eventBus, BarSkeleton skeleton) {
                // 数据分析逻辑服的订阅者
                eventBus.register(new AnalyseEventBusSubscriber());
            }
        });

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("数据分析-逻辑服");
        return builder;
    }
}
