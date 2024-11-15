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
package com.iohao.game.example.interaction;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.interaction.fight.DemoFightLogicServer;
import com.iohao.game.example.interaction.weather.DemoWeatherLogicServer;
import com.iohao.game.example.interaction.weather.action.DemoWeatherAction;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 逻辑服之间相互调用的示例启动类
 *
 * @author 渔民小镇
 * @date 2022-03-24
 */
@Slf4j
public class DemoInteractionApplication {
    public static void main(String[] args) {

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 战斗 - 逻辑服
                new DemoFightLogicServer(),
                // 天气预报 - 逻辑服
                new DemoWeatherLogicServer()
        );

        // 游戏对外服端口
        int port = 10100;
        // 启动 对外服、网关服、逻辑服; 
        NettySimpleHelper.run(port, logicList);

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/anguu6
         */
        TaskKit.runInterval(() -> {
            // print
            log.info("count: {}", DemoWeatherAction.longAdder.longValue());
        }, 5, TimeUnit.SECONDS);
    }
}
