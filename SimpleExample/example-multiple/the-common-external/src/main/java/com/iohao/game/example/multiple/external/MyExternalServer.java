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
package com.iohao.game.example.multiple.external;


import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.example.multiple.common.cmd.internal.WeatherCmd;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2023-03-30
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyExternalServer {

    public ExternalServer createExternalServer(int externalPort) {
        extractedAccess();

        // 游戏对外服 - 构建器
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(externalPort)
                // websocket 方式连接；如果不设置，默认也是这个配置
                .externalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // Broker （游戏网关）的连接地址；如果不设置，默认也是这个配置
                .brokerAddress(new BrokerAddress("127.0.0.1", IoGameGlobalConfig.brokerPort));

        // 构建游戏对外服
        return builder.build();
    }

    private static void extractedAccess() {
        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);
        // 添加不需要登录（身份验证）也能访问的业务方法 (action)
        accessAuthenticationHook.addIgnoreAuthCmd(WeatherCmd.cmd, WeatherCmd.login);
    }

    public static void main(String[] args) {
        // 游戏对外服端口
        ExternalServer externalServer = new MyExternalServer()
                .createExternalServer(ExternalGlobalConfig.externalPort);

        externalServer.startup();
    }

}
