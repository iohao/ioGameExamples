/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.spring.client;

import com.iohao.game.action.skeleton.protocol.wrapper.IntPb;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.spring.common.SpringCmdModule;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.spring.common.pb.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 游戏客户端模拟启动类
 *
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Slf4j
public class SpringWebsocketClient {

    public static void main(String[] args) throws Exception {

        // 请求构建 - 登录相关
        initLoginCommand();

        // 请求构建
        initClientCommands();

        // 启动客户端
        WebsocketClientKit.runClient();
    }

    private static void initLoginCommand() {
        ClientCommandKit.login = true;
        // （0 or 1）， 具体的作用可以查看 LoginVerify.loginBizCode 的注释
        int loginBizCode = 1;

        // 登录请求
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.jwt = "luoyi";
        loginVerify.loginBizCode = loginBizCode;

        ExternalMessage externalMessageLogin = ClientCommandKit.createExternalMessage(
                SpringCmdModule.HallCmd.cmd,
                SpringCmdModule.HallCmd.loginVerify,
                loginVerify
        );

        ClientCommandKit.createClientCommand(externalMessageLogin, UserInfo.class);
    }

    private static void initClientCommands() {
        LogicRequestPb logicRequestPb = new LogicRequestPb();
        logicRequestPb.name = "塔姆";

        // 请求、响应
        ExternalMessage externalMessageHere = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.here,
                logicRequestPb
        );

        ClientCommandKit.createClientCommand(externalMessageHere, LogicRequestPb.class);


        // 请求、无响应
        ExternalMessage externalMessageHereVoid = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.hereVoid,
                logicRequestPb
        );

        ClientCommandKit.createClientCommand(externalMessageHereVoid, LogicRequestPb.class);


        // 更新学校信息，jsr380
        SchoolPb schoolPb = new SchoolPb();
        schoolPb.email = "ioGame@game.com";
        schoolPb.classCapacity = 99;
        schoolPb.teacherNum = 40;

        ExternalMessage externalMessageJSR380 = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.jsr380,
                schoolPb
        );

        ClientCommandKit.createClientCommand(externalMessageJSR380);


        //断言 + 异常机制 = 清晰简洁的代码
        SchoolLevelPb schoolLevelPb = new SchoolLevelPb();
        schoolLevelPb.level = 11;
        schoolLevelPb.vipLevel = 10;

        ExternalMessage externalMessageAssert = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.assertWithException,
                schoolLevelPb
        );

        ClientCommandKit.createClientCommand(externalMessageAssert);


        // 广播相关
        ExternalMessage externalMessageBroadcast = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.broadcast
        );

        ClientCommandKit.createClientCommand(externalMessageBroadcast);

        // 添加接收广播的解析
        ClientCommandKit.addParseResult(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.broadcastData,
                SpringBroadcastMessagePb.class
        );

        // 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
        IntPb intPb = new IntPb();
        intPb.intValue = 10;

        ExternalMessage externalMessageIntPbWrapper = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.intPbWrapper,
                intPb
        );

        ClientCommandKit.createClientCommand(externalMessageIntPbWrapper, IntPb.class);

        // 逻辑服间的相互通信
        communicationClientCommands();
    }

    private static void communicationClientCommands() {
        // 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
        ExternalMessage externalMessageCommunication31 = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.communication31
        );

        ClientCommandKit.createClientCommand(externalMessageCommunication31);


        // 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
        ExternalMessage externalMessageCommunication32 = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.communication32
        );

        ClientCommandKit.createClientCommand(externalMessageCommunication32);


        // 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
        ExternalMessage externalMessageCommunication33 = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.communication33
        );

        ClientCommandKit.createClientCommand(externalMessageCommunication33);

    }
}
