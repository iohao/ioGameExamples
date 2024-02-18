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
package com.iohao.game.spring.logic.core;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.IoGameGlobalSetting;
import com.iohao.game.action.skeleton.core.flow.MyFlowContext;
import com.iohao.game.action.skeleton.core.flow.internal.DebugInOut;
import com.iohao.game.spring.common.SendDoc;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import lombok.experimental.UtilityClass;

/**
 * @author 渔民小镇
 * @date 2022-08-20
 */
@UtilityClass
public class MyBarSkeletonConfig {

    static MyDefaultMethodParser defaultMethodParser = new MyDefaultMethodParser();

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
        // issues186，为了方便观察，全部写这里了
//        MethodParsers.me().setMethodParser(defaultMethodParser);
        IoGameGlobalSetting.setDataCodec(new MyProtoDataCodec());

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
