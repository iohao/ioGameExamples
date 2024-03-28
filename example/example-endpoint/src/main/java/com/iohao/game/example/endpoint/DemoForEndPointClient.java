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
package com.iohao.game.example.endpoint;

import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.common.msg.DemoOperation;
import com.iohao.game.example.common.msg.MatchResponse;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.example.endpoint.match.action.DemoCmdForEndPointMatch;
import com.iohao.game.example.endpoint.room.action.DemoCmdForEndPointRoom;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class DemoForEndPointClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                // 匹配
                new InternalMatchRegion()
                // 房间
                , new InternalRoomRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }


    static class InternalRoomRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = DemoCmdForEndPointRoom.cmd;

            // 配置一些模拟请求
            ofCommand(DemoCmdForEndPointRoom.operation).setTitle("房间内的操作").setRequestData(() -> {
                DemoOperation demoOperation = new DemoOperation();
                demoOperation.name = "卡莉斯塔";
                return demoOperation;
            }).callback(result -> {
                DemoOperation value = result.getValue(DemoOperation.class);
                log.info("房间内的操作 : {}", value);
            });

            // Scheduled 5 seconds 发送消息 -- 房间请求相关的
            ExecutorKit.newSingleScheduled("websocketClient").scheduleAtFixedRate(() -> {
                // 请求房间逻辑服的 operation 方法
                ofRequestCommand(DemoCmdForEndPointRoom.operation).execute();
            }, 5, 5, TimeUnit.SECONDS);
        }
    }

    static class InternalMatchRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            // 模拟请求的主路由
            inputCommandCreate.cmd = DemoCmdForEndPointMatch.cmd;

            // 配置一些模拟请求
            ofCommand(DemoCmdForEndPointMatch.loginVerify).setTitle("登录验证").setRequestData(() -> {
                // jwt 写个固定的
                DemoLoginVerify loginVerify = new DemoLoginVerify();
                loginVerify.jwt = "abc";
                return loginVerify;
            }).callback(result -> {
                DemoUserInfo value = result.getValue(DemoUserInfo.class);
                log.info("登录成功 : {}", value);

                // 登录成功后，执行匹配请求
                ofRequestCommand(DemoCmdForEndPointMatch.matching).execute();
            });

            ofCommand(DemoCmdForEndPointMatch.matching).setTitle("匹配").callback(result -> {
                MatchResponse value = result.getValue(MatchResponse.class);
                log.info("匹配成功 : {}", value);
            });

            // 一秒后，执行模拟请求;
            TaskKit.runOnceSecond(() -> {
                // 登录
                ofRequestCommand(DemoCmdForEndPointMatch.loginVerify).execute();
            });
        }
    }
}
