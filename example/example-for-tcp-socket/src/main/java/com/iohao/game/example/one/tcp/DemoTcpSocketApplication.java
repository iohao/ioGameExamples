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
package com.iohao.game.example.one.tcp;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.example.one.server.DemoLogicServer;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;

import java.util.List;

/**
 * tcp socket 服务器启动类
 *
 * @author 渔民小镇
 * @date 2022-04-13
 */
public class DemoTcpSocketApplication {
    public static void main(String[] args) {
        // 游戏对外服端口
        int port = 10100;

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                new DemoLogicServer()
        );

        // 对外服 tcp socket 方式连接 （对应的测试类是 DemoSocketClient 客户端）
        NettySimpleHelper.runTcp(port, logicList);

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/ywe7uc
         */
    }
}
