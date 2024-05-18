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
package com.iohao.game.component.login;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.flow.internal.StatActionInOut;
import com.iohao.game.action.skeleton.core.flow.internal.ThreadMonitorInOut;
import com.iohao.game.action.skeleton.core.runner.Runner;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.action.skeleton.kit.LogicServerCreateKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.component.login.action.PressureAction;
import com.iohao.game.component.login.action.TheLoginAction;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
public class LoginLogicServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器
        BarSkeletonBuilder builder = LogicServerCreateKit.createBuilder(TheLoginAction.class);
        // 业务线程监控插件，将插件添加到业务框架中
        var threadMonitorInOut = new ThreadMonitorInOut();
//        builder.addInOut(threadMonitorInOut);

        // action 调用统计插件，将插件添加到业务框架中
        var statActionInOut = new StatActionInOut();
        builder.addInOut(statActionInOut);

        builder.addRunner(skeleton -> TaskKit.runInterval(() -> {
            long value = PressureAction.inc.longValue();
            log.info("inc {}", value);

            var region = statActionInOut.getRegion();
            log.info("\n{}", region);
            System.out.println();

        }, 1, TimeUnit.SECONDS));


        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.id("component-login-1");
        builder.appName("登录逻辑服组件");
        return builder;
    }
}
