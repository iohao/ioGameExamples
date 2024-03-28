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
package com.iohao.game.example.cluster.one;

import com.iohao.game.example.cluster.one.server.DemoClusterLogicServer;
import com.iohao.game.external.core.netty.simple.NettyClusterSimpleHelper;

import java.util.List;

/**
 * 单进程启动集群
 *
 * @author 渔民小镇
 * @date 2022-05-15
 */
public class DemoClusterApplication {
    public static void main(String[] args) {

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        var demoLogicServer = new DemoClusterLogicServer();

        // 启动 对外服、游戏网关集群、逻辑服; 并生成游戏业务文档
        NettyClusterSimpleHelper.run(port, List.of(demoLogicServer));

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/qmo56c
         */
    }
}
