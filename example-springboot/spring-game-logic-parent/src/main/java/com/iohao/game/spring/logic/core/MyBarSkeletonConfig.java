/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.spring.logic.core;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.flow.MyFlowContext;
import com.iohao.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.game.spring.common.SendDoc;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import lombok.experimental.UtilityClass;

/**
 * @author 渔民小镇
 * @date 2022-08-20
 */
@UtilityClass
public class MyBarSkeletonConfig {
    /**
     * 在实践项目中，可以提供这样一个通用的业务框架配置
     *
     * @return BarSkeletonBuilderParamConfig
     */
    public BarSkeletonBuilderParamConfig createBarSkeletonBuilderParamConfig() {
        // 业务框架构建器 配置
        return new BarSkeletonBuilderParamConfig()
                // 开启广播日志
                .setBroadcastLog(true)
                // 异常码文档生成
                .addErrorCode(SpringGameCodeEnum.values())
                // 推送(广播)文档生成
                .scanActionSendPackage(SendDoc.class);
    }

    /**
     * 在实践项目中，可以提供这样一个通用的业务框架配置
     *
     * @param config BarSkeletonBuilderParamConfig
     * @return BarSkeletonBuilder
     */
    public BarSkeletonBuilder createBarSkeletonBuilder(BarSkeletonBuilderParamConfig config) {
        // 业务框架构建器
        return config.createBuilder()
                // 添加控制台输出插件
                .addInOut(new DebugInOut())
                // 设置一个自定义的 flow 上下文生产工厂
                .setFlowContextFactory(MyFlowContext::new)
                ;
    }

    private BarSkeleton createBarSkeleton() {
        BarSkeletonBuilderParamConfig config = new BarSkeletonBuilderParamConfig();

        BarSkeletonBuilder builder = config.createBuilder()
                // 设置一个自定义的 flow 上下文生产工厂
                .setFlowContextFactory(MyFlowContext::new);

        return builder.build();
    }
}
