/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.exchange.logic;

import com.iohao.game.exchange.common.ExchangeCmd;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.simple.NettyRunOne;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
public class ExchangeGameApplication {
    public static void main(String[] args) {
        int externalPort = ExternalGlobalConfig.externalPort;
        ExternalServer externalServer = createExternalServer(externalPort);

        new NettyRunOne()
                .setExternalServer(externalServer)
                .setLogicServerList(List.of(new ExchangeGameLogicServer()))
                .startup();
    }

    public static ExternalServer createExternalServer(int externalPort) {
        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;
        accessAuthenticationHook.setVerifyIdentity(true);
        accessAuthenticationHook.addIgnoreAuthCmd(ExchangeCmd.cmd, ExchangeCmd.loginVerify);

        accessAuthenticationHook.addRejectionCmd(ExchangeCmd.cmd, ExchangeCmd.recharge);
        accessAuthenticationHook.addRejectionCmd(ExchangeCmd.cmd, ExchangeCmd.notice);

        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(externalPort);
        return builder.build();
    }
}
