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
package com.iohao.game.example.old.external;

import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.example.old.external.server.OldBroker;
import com.iohao.game.example.old.external.server.OldExternal;
import com.iohao.game.example.old.external.server.OldLogicServer;
import com.iohao.game.simple.SimpleRunOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-06-05
 */
public class OldApplication {
    public static void main(String[] args) {

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        var demoLogicServer = new OldLogicServer();

        ExternalServer externalServer = new OldExternal().create(port);

        BrokerServer brokerServer = new OldBroker().create();

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        new SimpleRunOne()
                .setExternalServer(externalServer)
                .setBrokerServer(brokerServer)
                .setLogicServerList(List.of(demoLogicServer))
                .startup();


        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/qv4qfo
         */
    }

}
