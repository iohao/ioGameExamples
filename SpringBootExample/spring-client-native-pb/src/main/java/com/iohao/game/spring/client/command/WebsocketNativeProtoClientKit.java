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
package com.iohao.game.spring.client.command;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.message.BizProto;
import com.iohao.message.Proto;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.plaf.basic.ComboPopup;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author 渔民小镇
 * @date 2022-08-03
 */
@Slf4j
@UtilityClass
public class WebsocketNativeProtoClientKit {
    public static final int issuesCmd = 6;

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

//                extractedEnum();

//                extractedList();

//                issues147();
//                issues186();
                issues338();

//                extractedLoginVerify(maxValue);
//                defaultValuePb();
//                BoolValue();
//                extractedLongValue();

//                IntValue();
//                IntValueList();
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

                var unaryOperator = consumerCallbackMap.get(cmdMerge);
                if (Objects.nonNull(unaryOperator)) {
                    unaryOperator.accept(data.toByteArray());
                }
            }

            private void extractedList() {
                Proto.ByteValueList.Builder builder = Proto.ByteValueList.newBuilder();

                for (int i = 0; i < 2; i++) {
                    ByteString userInfo = BizProto.UserInfo.newBuilder()
                            .setId(i)
                            .setName("name:" + i)
                            .build()
                            .toByteString();

                    builder.addValues(userInfo);
                }


                ByteString value = builder.build().toByteString();

                sendMsg(3, 9, value, data -> {
                    try {
                        Proto.ByteValueList byteValueList = Proto.ByteValueList.parseFrom(data);
                        byteValueList.getValuesList().stream().map(byteString -> {
                            try {
                                return BizProto.Animal.parseFrom(byteString);
                            } catch (InvalidProtocolBufferException e) {
                                throw new RuntimeException(e);
                            }
                        }).forEach(animal -> log.info("animal : {}-{}", animal.getId(), animal.getAnimalType()));

                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            private void extractedEnum() {
                ByteString value = BizProto.Animal.newBuilder()
                        .setId(1)
                        .setAnimalType(BizProto.AnimalType.BIRD)
                        .build().toByteString();

                sendMsg(3, 8, value, data -> {
                    try {
                        BizProto.Animal animal = BizProto.Animal.parseFrom(data);
                        log.info("animal : {}", animal);
                        BizProto.AnimalType animalType = animal.getAnimalType();
                        log.info("animalType : {}", animalType);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            private void extractedLongValue() {
                ByteString value = Proto.LongValue.newBuilder()
                        .setValue(9120)
                        .build()
                        .toByteString();

                this.sendMsg(5, 2, value);
                this.sendMsg(5, 3, value);
                this.sendMsg(5, 4, value);

            }

            private void BoolValue() {
                Consumer<byte[]> consumer = bytes -> {
                    try {
                        Proto.BoolValue boolValue = Proto.BoolValue.parseFrom(bytes);
                        log.info("bytes : {}", bytes);
                        log.info("BoolValue : {}", boolValue.getValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                log.info("BoolValue");
                ByteString value = Proto.BoolValue.newBuilder()
                        .setValue(true)
                        .build()
                        .toByteString();

                this.sendMsg(3, 8, value, consumer);

                value = Proto.BoolValue.newBuilder()
                        .build()
                        .toByteString();
                this.sendMsg(3, 9, value, consumer);


                var list = Proto.BoolValueList.newBuilder()
                        .addValues(true)
                        .build()
                        .toByteString();

                this.sendMsg(3, 10, list, bytes -> {
                    try {
                        Proto.BoolValueList boolValueList = Proto.BoolValueList.parseFrom(bytes);
                        log.info("bytes : {}", bytes);
                        log.info("BoolValueList : {}", boolValueList.getValuesList());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            private void intValue() {
                Consumer<byte[]> consumer = bytes -> {
                    try {
                        Proto.IntValue IntValue = Proto.IntValue.parseFrom(bytes);
                        log.info("bytes : {}", bytes);
                        log.info("IntValue : {}", IntValue.getValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                log.info("IntValue");
                ByteString value = Proto.IntValue.newBuilder()
                        .setValue(0)
                        .build()
                        .toByteString();

                this.sendMsg(3, 5, value, consumer);
                this.sendMsg(3, 4, value, consumer);
            }

            private void intValueList() {
                log.info("IntValueList");
                ByteString value = Proto.IntValueList.newBuilder()
                        .build()
                        .toByteString();

                this.sendMsg(3, 6, value);
            }

            private void defaultValuePb() {

                Consumer<byte[]> consumer = bytes -> {
                    try {
                        var userInfo = BizProto.UserInfo.parseFrom(bytes);
                        log.info("userInfo : {}", userInfo);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                log.info("defaultValuePb");
                ByteString value = Proto.IntValueList.newBuilder()
                        .build()
                        .toByteString();

                this.sendMsg(3, 7, value, consumer);
            }

            private void issues147() {
                Consumer<byte[]> consumer = bytes -> {
                    try {
                        var sceneEnterReq = BizProto.SceneEnterReq.parseFrom(bytes);
                        log.info("sceneEnterReq : {}", sceneEnterReq);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                ByteString value = BizProto.SceneEnterReq.newBuilder()
                        .setSceneId(1)
                        .setPositionX(0)
                        .setPositionY(1.21f)
                        .build()
                        .toByteString();

                this.sendMsg(6, 3, value, consumer);
            }

            private void issues186() {
                Consumer<byte[]> consumer = bytes -> {
                    try {
                        var vector3 = BizProto.Vector3.parseFrom(bytes);
                        log.info("issues186 : {}", vector3);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                ByteString value = BizProto.Vector3.newBuilder()
                        .setX(0)
                        .setY(0)
                        .setZ(0)
                        .build()
                        .toByteString();

                this.sendMsg(6, 4, value, consumer);
            }

            private void issues338() {
                Consumer<byte[]> consumer = bytes -> {
                    try {
                        var longValue = Proto.LongValue.parseFrom(bytes);
                        log.info("issues338 : {}", longValue);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                this.sendMsg(issuesCmd, 5, consumer);

                Consumer<byte[]> consumer_1 = bytes -> {
                    try {
                        var roomInfo = BizProto.Issue338RoomInfo.parseFrom(bytes);
                        log.info("issues338_roomInfo : {}", roomInfo);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                this.sendMsg(issuesCmd, 6, consumer_1);
            }

            private void extractedLoginVerify(long maxValue) {
                Consumer<byte[]> consumer = bytes -> {
                    try {
                        var userInfo = BizProto.UserInfo.parseFrom(bytes);
                        log.info("userInfo : {}", userInfo);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                };

                ByteString value = BizProto.LoginVerify.newBuilder()
//                        .setAge(273676)
                        .setAge(9120)
                        .setTime(maxValue)
                        .setJwt("abcd")
                        .setLoginBizCode(0)
                        .build()
                        .toByteString();

                this.sendMsg(3, 1, value, consumer);
            }

            private void sendMsg(int cmd, int subCmd, ByteString value) {
                Proto.ExternalMessage externalMessage = createExternalMessage(cmd, subCmd, value);
                byte[] byteArray = externalMessage.toByteArray();
                this.send(byteArray);
            }

            private void sendMsg(int cmd, int subCmd, com.google.protobuf.GeneratedMessageV3 messageV3) {
                this.sendMsg(cmd, subCmd, messageV3.toByteString());
            }

            private void sendMsg(int cmd, int subCmd, ByteString value, Consumer<byte[]> consumerCallback) {
                onMessageCallback(cmd, subCmd, consumerCallback);
                this.sendMsg(cmd, subCmd, value);
            }

            private void sendMsg(int cmd, int subCmd, Consumer<byte[]> consumerCallback) {
                onMessageCallback(cmd, subCmd, consumerCallback);
                this.sendMsg(cmd, subCmd, (ByteString) null);
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

    Map<Integer, Consumer<byte[]>> consumerCallbackMap = new HashMap<>();

    void onMessageCallback(int cmd, int subCmd, Consumer<byte[]> consumerCallback) {
        consumerCallbackMap.put(CmdKit.merge(cmd, subCmd), consumerCallback);
    }
}
