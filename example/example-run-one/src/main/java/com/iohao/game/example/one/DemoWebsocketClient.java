/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.example.one;

import com.iohao.game.action.skeleton.core.flow.codec.ProtoDataCodec;
import com.iohao.game.example.common.kit.ClientKit;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.one.action.DemoCmd;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessageCmdCode;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

/**
 * 模拟游戏客户端 websocket
 *
 * @author 渔民小镇
 * @date 2022-02-24
 */
@Slf4j
public class DemoWebsocketClient {
    public static void main(String[] args) throws Exception {
        // 这里模拟游戏客户端

        // 连接游戏服务器的地址
        var wsUrl = "ws://127.0.0.1:10100/websocket";

        WebSocketClient webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                // 路由, 对应服务端逻辑服的业务类路由地址
                int cmd = DemoCmd.cmd;
//                subCmd = DemoCmd.jackson;

                Object dataPb = null;

                HelloReq helloReq = new HelloReq();
                helloReq.setName("塔姆");
                int subCmd = DemoCmd.here;
                dataPb = helloReq;

                DemoLoginVerify loginVerify = new DemoLoginVerify();
                loginVerify.jwt = "abc";
//                subCmd = DemoCmd.loginVerify;
//                dataPb = loginVerify;


                // 转为字节
                byte[] bytes = ClientKit.createExternalMessageBytes(cmd, subCmd, dataPb);
                // 发送数据到游戏服务器
                this.send(bytes);
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收服务器返回的消息
                byte[] dataContent = byteBuffer.array();
                ExternalMessage message = ProtoKit.parseProtoByte(dataContent, ExternalMessage.class);
                log.info("收到消息 ExternalMessage ========== \n{}", message);
                byte[] data = message.getData();
                if (data != null) {
//                    HelloReq helloReq = ProtoKit.parseProtoByte(data, HelloReq.class);
//                    log.info("helloReq ========== \n{}", helloReq);
                }
            }

            @Override
            public void onMessage(String message) {

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };

        // 开始连接服务器
        webSocketClient.connect();
    }
}
