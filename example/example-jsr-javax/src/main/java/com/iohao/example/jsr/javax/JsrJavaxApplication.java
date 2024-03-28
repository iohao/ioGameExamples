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
package com.iohao.example.jsr.javax;

import com.iohao.example.jsr.javax.server.JsrJavaxLogicServer;
import com.iohao.game.common.validation.annotation.EnableValidation;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-10-28
 */
@Slf4j
@EnableValidation("com.iohao.game.common.validation.support.JavaxValidator")
public class JsrJavaxApplication {
    public static void main(String[] args) {
        // 游戏对外服端口
        int port = 10100;

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        NettySimpleHelper.run(port, List.of(new JsrJavaxLogicServer()));
    }
}
