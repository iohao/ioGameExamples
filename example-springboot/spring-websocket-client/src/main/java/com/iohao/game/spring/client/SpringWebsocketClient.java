/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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

import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.command.ClientCommandKit;
import com.iohao.game.command.WebsocketClientKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.spring.common.cmd.HallCmdModule;
import com.iohao.game.spring.common.cmd.OtherSchoolCmdModule;
import com.iohao.game.spring.common.cmd.RoomCmdModule;
import com.iohao.game.spring.common.cmd.SchoolCmdModule;
import com.iohao.game.spring.common.data.MyAttachment;
import com.iohao.game.spring.common.pb.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

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

        // 元信息相关
        attachmentCommands();

        TimeUnit.MILLISECONDS.sleep(1);

        // 请求构建
//        initClientCommands();
//
//        // 逻辑服间的相互通信
//        communicationClientCommands();
//
//        // 其他
//        otherCommand();

        // 启动客户端
        WebsocketClientKit.runClient();
    }

    private static void attachmentCommands() {
        // 请求 设置元信息
        var externalMessage = ClientCommandKit.createExternalMessage(
                HallCmdModule.cmd,
                HallCmdModule.attachment
        );

        ClientCommandKit.createClientCommand(externalMessage, 1000);

        // 请求 打印元信息
        externalMessage = ClientCommandKit.createExternalMessage(
                HallCmdModule.cmd,
                HallCmdModule.attachmentPrint
        );

        ClientCommandKit.createClientCommand(externalMessage, MyAttachment.class);

    }

    private static void otherCommand() {

        // 请求、响应
        ExternalMessage externalMessageHelloRoom = ClientCommandKit.createExternalMessage(
                RoomCmdModule.cmd,
                RoomCmdModule.helloRoom
        );

        ClientCommandKit.createClientCommand(externalMessageHelloRoom, OtherVerify.class);


        ExternalMessage externalMessageLongValueWithBroadcast = ClientCommandKit.createExternalMessage(
                OtherSchoolCmdModule.cmd,
                OtherSchoolCmdModule.longValueWithBroadcast
        );

        ClientCommandKit.createClientCommand(externalMessageLongValueWithBroadcast, UserInfo.class);
        ClientCommandKit.addParseResult(OtherSchoolCmdModule.cmd, OtherSchoolCmdModule.longValueWithBroadcastData, SchoolPb.class);
    }

    private static void initLoginCommand() {
        /*
         *       注意，这个业务码放这里只是为了方便测试多种情况
         *       交由测试请求端来控制
         *
         *       ------------业务码说明------------------
         *       当业务码为 【0】时 (相当于号已经在线上了，不能重复登录)
         *       如果游戏对外服已经有该玩家的连接了，就抛异常，告诉请求端玩家已经在线。
         *       否则就正常登录成功
         *
         *       当业务码为 【1】时 （相当于顶号）
         *       强制断开之前的客户端连接，并让本次登录成功。
         */
        int loginBizCode = 1;

        // 登录请求
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.age = 273676;
        loginVerify.jwt = "luoyi";
        loginVerify.loginBizCode = loginBizCode;

        ExternalMessage externalMessageLogin = ClientCommandKit.createExternalMessage(
                HallCmdModule.cmd,
                HallCmdModule.loginVerify,
                loginVerify
        );

        ClientCommandKit.createClientCommand(externalMessageLogin, UserInfo.class, 1500);
    }

    private static void initClientCommands() {
        LogicRequestPb logicRequestPb = new LogicRequestPb();
        logicRequestPb.name = "塔姆";

        // 请求、响应
        ExternalMessage externalMessageHere = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.here,
                logicRequestPb
        );

        ClientCommandKit.createClientCommand(externalMessageHere, LogicRequestPb.class);


        // 请求、无响应
        ExternalMessage externalMessageHereVoid = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.hereVoid,
                logicRequestPb
        );

        ClientCommandKit.createClientCommand(externalMessageHereVoid, LogicRequestPb.class);


        // 更新学校信息，jsr380
        SchoolPb schoolPb = new SchoolPb();
        schoolPb.email = "ioGame@game.com";
        schoolPb.classCapacity = 99;
        schoolPb.teacherNum = 40;
        schoolPb.teacherNum = 70;

        ExternalMessage externalMessageJSR380 = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.jsr380,
                schoolPb
        );

        ClientCommandKit.createClientCommand(externalMessageJSR380);

        //SchoolPb name=null 在加入分组校验时 该用例会返回校验失败: name不能为null
        ExternalMessage externalMessageGroup = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.group,
                schoolPb
        );

        ClientCommandKit.createClientCommand(externalMessageGroup);


        //断言 + 异常机制 = 清晰简洁的代码
        SchoolLevelPb schoolLevelPb = new SchoolLevelPb();
        schoolLevelPb.level = 11;
        schoolLevelPb.vipLevel = 10;

        ExternalMessage externalMessageAssert = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.assertWithException,
                schoolLevelPb
        );

        ClientCommandKit.createClientCommand(externalMessageAssert);


        // 广播相关
        ExternalMessage externalMessageBroadcast = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.broadcast
        );

        ClientCommandKit.createClientCommand(externalMessageBroadcast);

        // 添加接收广播的解析
        ClientCommandKit.addParseResult(
                SchoolCmdModule.cmd,
                SchoolCmdModule.broadcastData,
                SpringBroadcastMessagePb.class
        );

        // 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
        IntValue intValue = new IntValue();
        intValue.value = 10;

        ExternalMessage externalMessageIntValueWrapper = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.intValueWrapper,
                intValue
        );

        ClientCommandKit.createClientCommand(externalMessageIntValueWrapper, IntValue.class);


        OtherVerify otherVerify = new OtherVerify();
        otherVerify.jwt = "j";

        // 请求、响应
        ExternalMessage externalMessageJsr380 = ClientCommandKit.createExternalMessage(
                OtherSchoolCmdModule.cmd,
                OtherSchoolCmdModule.jsr380,
                otherVerify
        );

        ClientCommandKit.createClientCommand(externalMessageJsr380);
    }

    private static void communicationClientCommands() {
        // 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
        ExternalMessage externalMessageCommunication31 = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.communication31
        );

        ClientCommandKit.createClientCommand(externalMessageCommunication31);


        // 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
        ExternalMessage externalMessageCommunication32 = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.communication32
        );

        ClientCommandKit.createClientCommand(externalMessageCommunication32);

        // 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）

        ExternalMessage externalMessageCommunication33 = ClientCommandKit.createExternalMessage(
                SchoolCmdModule.cmd,
                SchoolCmdModule.communication33
        );

        ClientCommandKit.createClientCommand(externalMessageCommunication33);

    }
}
