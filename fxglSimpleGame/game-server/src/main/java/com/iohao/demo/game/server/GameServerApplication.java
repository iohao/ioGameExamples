/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.demo.game.server;

import com.iohao.game.external.core.netty.simple.NettySimpleHelper;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-11-23
 */
public class GameServerApplication {
    public static void main(String[] args) {

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        var demoLogicServer = new MyLogicServer();

        // 启动游戏对外服、Broker（游戏网关）、游戏逻辑服
        // 这三部分在一个进程中相互使用内存通信
        NettySimpleHelper.run(port, List.of(demoLogicServer));
    }
}
