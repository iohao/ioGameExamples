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
package com.iohao.game.spring.logic.hall;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import com.iohao.game.spring.logic.hall.action.LoginAction;
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
public class GameLogicHallClient extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {

        // 业务框架构建器 配置
        BarSkeletonBuilderParamConfig config = new BarSkeletonBuilderParamConfig()
                // 扫描 ClassesAction.class 所在包
                .addActionController(LoginAction.class)
                // 开启广播日志
                .setBroadcastLog(true)
                // 对接文档相关 - 错误码
                .addErrorCode(SpringGameCodeEnum.values());

        // 业务框架构建器
        BarSkeletonBuilder builder = config.createBuilder();
        // 开启 jsr380 验证
        builder.getSetting().setValidator(true);

        // 添加控制台输出插件
        builder.addInOut(new DebugInOut());

        return builder.build();
    }



    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("spring hall 大厅逻辑服");
        return builder;
    }
}
