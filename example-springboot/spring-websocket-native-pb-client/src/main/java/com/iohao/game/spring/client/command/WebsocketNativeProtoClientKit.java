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
package com.iohao.game.spring.client.command;

import com.google.protobuf.ByteString;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.message.BizProto;
import com.iohao.message.Proto;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2022-08-03
 */
@Slf4j
@UtilityClass
public class WebsocketNativeProtoClientKit {

    public static void main(String[] args) throws Exception {

        WebsocketNativeProtoClientKit.runClient();
    }

    public void runClient() throws Exception {
        // 连接游戏服务器的地址
        String wsUrl = "ws://127.0.0.1:10100/websocket";

        WebSocketClient webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                long maxValue = Long.MAX_VALUE - 1;
                log.info("Long.MAX_VALUE-1 : {}", maxValue);


//                extractedLoginVerify(maxValue);

//                extractedLongPb();

                intPb();
//                intListPb();
            }

            private void extractedLongPb() {
                ByteString value = Proto.LongPb.newBuilder()
                        .setLongValue(9120)
                        .build()
                        .toByteString();

                this.sendMsg(5, 2, value);
                this.sendMsg(5, 3, value);
                this.sendMsg(5, 4, value);

            }

            private void intPb() {
                log.info("intPb");
                ByteString value = Proto.IntPb.newBuilder()
                        .setIntValue(0)
                        .build()
                        .toByteString();

                this.sendMsg(3, 5, value);
                this.sendMsg(3, 4, value);
            }

            private void intListPb() {
                log.info("intListPb");
                ByteString value = Proto.IntListPb.newBuilder()
                        .build()
                        .toByteString();

                this.sendMsg(3, 6, value);
            }

            private void extractedLoginVerify(long maxValue) {
                ByteString value = BizProto.LoginVerify.newBuilder()
//                        .setAge(273676)
                        .setAge(9120)
                        .setTime(maxValue)
                        .setJwt("abcd")
                        .setLoginBizCode(0)
                        .build()
                        .toByteString();

                this.sendMsg(3, 1, value);
            }

            private void sendMsg(int cmd, int subCmd, ByteString value) {
                Proto.ExternalMessage externalMessage = createExternalMessage(cmd, subCmd, value);
                this.send(externalMessage.toByteArray());
            }

            private void sendMsg(int cmd, int subCmd, com.google.protobuf.GeneratedMessageV3 messageV3) {
                this.sendMsg(cmd, subCmd, messageV3.toByteString());
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

            @SneakyThrows
            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收服务器返回的消息
                var externalMessage = Proto.ExternalMessage.parseFrom(byteBuffer);
                int cmdMerge = externalMessage.getCmdMerge();

                log.info("externalMessage : {}", externalMessage);
                log.info("cmd : {}-{}", CmdKit.getCmd(cmdMerge), CmdKit.getSubCmd(cmdMerge));

                ByteString data = externalMessage.getData();

                if (cmdMerge == CmdKit.merge(3, 5) || cmdMerge == CmdKit.merge(3, 4)) {
                    byte[] bytes = data.toByteArray();
                    log.info("bytes : {}", bytes);
                    Proto.IntPb intPb = Proto.IntPb.parseFrom(data);
                    log.info("intPb : {}", intPb.getIntValue());
                }

            }
        };

        // 开始连接服务器
        webSocketClient.connect();
    }

    public Proto.ExternalMessage createExternalMessage(int cmd, int subCmd, ByteString data) {

        Proto.ExternalMessage.Builder builder = Proto.ExternalMessage.newBuilder()
                // 请求命令类型: 0 心跳，1 业务
                .setCmdCode(1)
                .setProtocolSwitch(0)
                // 路由
                .setCmdMerge(CmdKit.merge(cmd, subCmd))
                .setResponseStatus(0);

        if (Objects.nonNull(data)) {
            builder.setData(data);
        }

        return builder.build();
    }
}
