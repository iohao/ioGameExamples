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
package com.iohao.game.component.client;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.common.kit.concurrent.IntervalTaskListener;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.component.login.client.LoginInputCommandRegion;
import com.iohao.game.component.login.cmd.LoginCmd;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.command.RequestCommand;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import com.iohao.game.external.client.user.ClientUser;
import com.iohao.game.external.client.user.ClientUserInputCommands;
import com.iohao.game.external.client.user.ClientUsers;
import com.iohao.game.external.client.user.DefaultClientUser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 压测演示
 *
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
public class PressureClient {
    static int pressureUserSize;
    static int pressureSecondsRequest;
    static int pressureRound;
    static AtomicBoolean printFlag = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        // 压测，关闭控制台输入
        ClientUserConfigs.closeScanner = true;
        // 压测，关闭相关打印日志
        ClientUserConfigs.closeLog();

        // 需要模拟压测的玩家数量
        pressureUserSize = 80;
        // 每个玩家每秒的请求次数
        pressureSecondsRequest = 10;
        // 共测 N 轮
        pressureRound = 10;

        ExecutorService executor = ExecutorKit.newFixedThreadPool(200, "loginUser");
        // 设置期望测试的玩家数量
        for (int i = 1; i <= pressureUserSize; i++) {
            // 模拟玩家的 userId
            long userId = i;
            // 启动模拟客户端
            executor.execute(() -> start(userId));
        }

        TimeUnit.SECONDS.sleep(1);
    }

    private static void pressureLog() {
        String pressureFormat = """
                请求总次数【%,d】
                模拟压测【%s】个玩家 - 每个玩家每秒请求【%,d】次
                每秒请求总次数【%,d】
                共测【%,d】轮
                """.formatted(
                (pressureUserSize * pressureSecondsRequest * pressureRound),
                pressureUserSize,
                pressureSecondsRequest,
                (pressureUserSize * pressureSecondsRequest),
                pressureRound
        );

        log.info("\n{}", pressureFormat);
    }

    private static void extractedPrintPressureLog() {
        if (printFlag.get()) {
            printFlag.set(false);
            ClientUsers.execute(PressureClient::pressureLog);
        }
    }

    static void start(long userId) {
        // 客户端的用户（玩家）
        ClientUser clientUser = new DefaultClientUser();
        String jwt = String.valueOf(userId);
        clientUser.setJwt(jwt);

        // 一秒后，自动触发登录请求
        TaskKit.runOnceSecond(() -> {
            CmdInfo cmdInfo = LoginCmd.of(LoginCmd.login);

            // 通过 cmdInfo 查找请求命令对象，并执行
            ClientUserInputCommands clientUserInputCommands = clientUser.getClientUserInputCommands();
            RequestCommand requestCommand = clientUserInputCommands.ofRequestCommand(cmdInfo);
            requestCommand.execute();
        });

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                // 登录
                new LoginInputCommandRegion()
                // 压测相关
                , new PressureInputCommandRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setClientUser(clientUser)
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }

    @Slf4j
    static class PressureInputCommandRegion extends AbstractInputCommandRegion {
        AtomicInteger count = new AtomicInteger();

        @Override
        public void initInputCommand() {
            // 设置模块主路由
            this.inputCommandCreate.cmd = LoginCmd.cmd;
            ofCommand(LoginCmd.inc).setTitle("inc");

            ofCommand(LoginCmd.inc2).setRequestData(() -> LongValue.of(1)).setTitle("inc2").callback(result -> {
            });

            // 需要压测的业务代码。将任务添加到队列中，当玩家全部登录完成后会执行任务
            ClientUsers.execute(this::ppp);

            extractedPrintPressureLog();
        }

        private void ppp() {
            TaskKit.runInterval(new IntervalTaskListener() {
                @Override
                public void onUpdate() {

                    // 请求 N 次 inc action
                    for (int i = 0; i < pressureSecondsRequest; i++) {
                        ofRequestCommand(LoginCmd.inc).execute();
                    }
                }

                @Override
                public boolean isActive() {
                    // 执行次数上限
                    return count.incrementAndGet() <= pressureRound;
                }
            }, 1, TimeUnit.SECONDS);
        }
    }
}
