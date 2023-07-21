/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.interaction.part;

import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-11-05
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DemoInteractionExternalClient {
    public static void main(String[] args) {
        // 游戏对外服 - 构建器
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(10100)
                // Broker （游戏网关）的连接地址
                .brokerAddress(new BrokerAddress("127.0.0.1", IoGameGlobalConfig.brokerPort));

        builder.build().startup();
    }
}
