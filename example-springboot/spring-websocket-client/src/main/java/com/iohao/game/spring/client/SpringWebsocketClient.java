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

import com.alibaba.fastjson2.JSON;
import com.iohao.game.action.skeleton.core.flow.codec.ProtoDataCodec;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.spring.common.ClientCommandKit;
import com.iohao.game.spring.common.cmd.SpringCmdModule;
import com.iohao.game.spring.common.pb.LogicRequestPb;
import com.iohao.game.spring.common.pb.SchoolLevelPb;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.common.pb.SpringBroadcastMessage;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Slf4j
public class SpringWebsocketClient {

    public static void main(String[] args) throws Exception {

        initClientCommands();
        // 这里模拟游戏客户端

        // 连接游戏服务器的地址
        String wsUrl = "ws://127.0.0.1:10088/websocket";

        WebSocketClient webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                // 建立连接后 发送一条消息给游戏服务器
                LogicRequestPb helloReq = new LogicRequestPb();
                helloReq.name = "222";

                // 路由, 对应服务端逻辑服的业务类路由地址
                int cmd = SpringCmdModule.SchoolCmd.cmd;
                int subCmd = SpringCmdModule.SchoolCmd.here;

                // 游戏框架内置的协议， 与游戏前端相互通信的协议
                ExternalMessage externalMessage = new ExternalMessage();
                // 请求命令类型: 0 心跳，1 业务
                externalMessage.setCmdCode(1);
                // 路由
                externalMessage.setCmdMerge(cmd, subCmd);
                // 业务数据
                byte[] data = ProtoDataCodec.me().encode(helloReq);
                externalMessage.setData(data);
                log.info("消息体：{}", JSON.toJSONString(externalMessage));

                // 转为 pb 字节
                byte[] bytes = ProtoKit.toBytes(externalMessage);
                // 发送数据到游戏服务器
                this.send(bytes);
            }

            @Override
            public void onMessage(String s) {

            }

            @Override
            public void onClose(int i, String s, boolean b) {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收服务器返回的消息
                byte[] dataContent = byteBuffer.array();
                ExternalMessage message = ProtoKit.parseProtoByte(dataContent, ExternalMessage.class);
                log.info("收到消息 ExternalMessage ========== \n{}", message);
                byte[] data = message.getData();
                if (data != null) {
                    LogicRequestPb helloReq = ProtoKit.parseProtoByte(data, LogicRequestPb.class);
                    log.info("helloReq ========== \n{}", helloReq);
                }
            }
        };

        // 开始连接服务器
        webSocketClient.connect();
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


        // 更新学校信息，jsr303
        SchoolPb schoolPb = new SchoolPb();
        schoolPb.email = "ioGame@game.com";
        schoolPb.classCapacity = 99;
        schoolPb.teacherNum = 40;

        ExternalMessage externalMessageJSR303 = ClientCommandKit.createExternalMessage(
                SpringCmdModule.SchoolCmd.cmd,
                SpringCmdModule.SchoolCmd.jsr303,
                schoolPb
        );

        ClientCommandKit.createClientCommand(externalMessageJSR303);


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
                SpringBroadcastMessage.class
        );

    }
}
