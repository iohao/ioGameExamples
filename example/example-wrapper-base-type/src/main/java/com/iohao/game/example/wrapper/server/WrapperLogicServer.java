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
package com.iohao.game.example.wrapper.server;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.processor.hook.ClientProcessorHooks;
import com.iohao.game.example.wrapper.action.IntAction;
import com.iohao.game.example.wrapper.action.WrapperCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
@Slf4j
public class WrapperLogicServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        var config = new BarSkeletonBuilderParamConfig()
                // 扫描 action 类所在包
                .scanActionPackage(IntAction.class);

        // 业务框架构建器
        var builder = config.createBuilder();

        // 添加控制台输出插件
        builder.addInOut(new DebugInOut());

        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        // bolt 业务处理器的钩子管理器
        ClientProcessorHooks hooks = getClientProcessorHooks();

        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder
                .appName("业务参数自动装箱和拆箱-逻辑服")
                // 设置 bolt 业务处理器的钩子管理器
                .clientProcessorHooks(hooks)
        ;

        return builder;
    }

    private ClientProcessorHooks getClientProcessorHooks() {
        // bolt 业务处理器的钩子管理器
        ClientProcessorHooks hooks = new ClientProcessorHooks();

        // 设置逻辑服业务处理钩子接口
        hooks.setRequestMessageClientProcessorHook((barSkeleton, flowContext) -> {

            // 这里做线程编排相关的逻辑

            CmdInfo cmdInfo = flowContext.getCmdInfo();
            int cmdMerge = cmdInfo.getCmdMerge();
            // 如果是这个路由，就给到其他并发框架
            if (cmdMerge == CmdKit.merge(WrapperCmd.cmd, WrapperCmd.int2int)) {
                // 注意，这里是伪代码；放到 disruptor 或其他并发框架中执行，这样可以做到无锁化编程。
                barSkeleton.handle(flowContext);
                return;
            }

            // 默认正常执行
            barSkeleton.handle(flowContext);
        });

        return hooks;
    }
}
