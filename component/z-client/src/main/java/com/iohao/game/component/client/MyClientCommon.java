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
package com.iohao.game.component.client;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.component.chat.client.ChatInputCommandRegion;
import com.iohao.game.component.login.client.LoginInputCommandRegion;
import com.iohao.game.component.login.cmd.LoginCmd;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.command.RequestCommand;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.user.ClientUser;
import com.iohao.game.external.client.user.ClientUserInputCommands;
import com.iohao.game.external.client.user.DefaultClientUser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
public class MyClientCommon {
    static void start(long userId) {
        // 客户端的用户（玩家）
        ClientUser clientUser = new DefaultClientUser();
        clientUser.setJwt(String.valueOf(userId));

        // 一秒后，自动触发登录请求
        TaskKit.runOnceSecond(() -> {
            CmdInfo cmdInfo = LoginCmd.getCmdInfo(LoginCmd.login);

            ClientUserInputCommands clientUserInputCommands = clientUser.getClientUserInputCommands();
            // 创建一个请求命令
            RequestCommand requestCommand = clientUserInputCommands.ofRequestCommand(cmdInfo);
            // 执行请求
            requestCommand.execute();
        });

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                // 登录
                new LoginInputCommandRegion()
                // 聊天 - 私聊
                , new ChatInputCommandRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setClientUser(clientUser)
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }
}
