/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
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
                // 扫描 DemoAction.class 所在包
                .addActionController(IntAction.class);

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
