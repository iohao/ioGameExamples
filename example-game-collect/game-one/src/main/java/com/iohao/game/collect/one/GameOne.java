/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.one;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.collect.external.GameExternalBoot;
import com.iohao.game.collect.hall.HallClientStartup;
import com.iohao.game.collect.tank.TankClientStartup;
import com.iohao.game.simple.SimpleRunOne;

import java.util.List;

/**
 * 游戏启动
 *
 * @author 渔民小镇
 * @date 2022-01-13
 */
public class GameOne {
    public static void main(String[] args) {

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicServer = List.of(
                // 大厅
                new HallClientStartup(),
                // 坦克游戏
                new TankClientStartup()
        );

        // 简单启动器 RunOne (谐音:拳皇97中的 round one ready go!)
        new SimpleRunOne()
                // 对外服
                .setExternalServer(new GameExternalBoot().createExternalServer())
                // 逻辑服列表
                .setLogicServerList(logicServer)
                // 启动 对外服、网关、逻辑服
                .startup();

        // see : TankApp.java  （坦克游戏客户端启动类）
    }

}
