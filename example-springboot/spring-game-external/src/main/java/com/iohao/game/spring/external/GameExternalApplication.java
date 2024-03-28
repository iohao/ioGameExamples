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
package com.iohao.game.spring.external;

import com.iohao.game.external.core.ExternalServer;

/**
 * 单独启动类：游戏对外服
 *
 * @author 渔民小镇
 * @date 2022-07-09
 */
public class GameExternalApplication {
    public static void main(String[] args) {

        // 对外开放的端口
        int externalPort = 10100;

        // 构建游戏对外服
        ExternalServer externalServer = new GameExternal().createExternalServer(externalPort);

        // 启动游戏对外服
        externalServer.startup();
    }
}
