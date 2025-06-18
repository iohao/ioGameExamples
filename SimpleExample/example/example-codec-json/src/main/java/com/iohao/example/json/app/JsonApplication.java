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
package com.iohao.example.json.app;

import com.iohao.game.action.skeleton.core.IoGameGlobalSetting;
import com.iohao.game.action.skeleton.core.codec.JsonDataCodec;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-11-24
 */
@SpringBootApplication
public class JsonApplication {
    public static void main(String[] args) {
        /*
         * run JsonClient.java or http://localhost:8080/index.html
         */
        SpringApplication.run(JsonApplication.class, args);

        // 设置 json 编解码。如果不设置，默认为 jprotobuf
        IoGameGlobalSetting.setDataCodec(new JsonDataCodec());

        // 游戏对外服端口
        int port = ExternalGlobalConfig.externalPort;

        // 逻辑服
        var demoLogicServer = new JsonLogicServer();

        // 启动 对外服、网关服、逻辑服; 
        NettySimpleHelper.run(port, List.of(demoLogicServer));

        /*
         * 该示例文档地址
         * https://iohao.github.io/game/docs/manual/data_protocol
         */
    }

}