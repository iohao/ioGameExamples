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
package com.iohao.game.example.wrapper;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.flow.codec.ProtoDataCodec;
import com.iohao.game.action.skeleton.protocol.wrapper.IntListPb;
import com.iohao.game.action.skeleton.protocol.wrapper.IntPb;
import com.iohao.game.action.skeleton.protocol.wrapper.LongListPb;
import com.iohao.game.action.skeleton.protocol.wrapper.LongPb;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessageCmdCode;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.example.wrapper.action.WrapperCmd;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
@Slf4j
public class WrapperWebsocketClient {

    public static void main(String[] args) throws URISyntaxException {
        WrapperWebsocketClient websocketClient = new WrapperWebsocketClient();
        websocketClient.init();
    }

    private void init() throws URISyntaxException {
        initTheCommands();

        // 这里模拟游戏客户端
        // 连接游戏服务器的地址
        var wsUrl = "ws://127.0.0.1:10100/websocket";

        WebSocketClient webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Collection<TheCommand> values = theCommandMap.values();
                values.forEach(theCommand -> {
                    ExternalMessage externalMessage = theCommand.externalMessage;
                    // 转为字节
                    byte[] bytes = ProtoKit.toBytes(externalMessage);
                    // 发送数据到游戏服务器
                    this.send(bytes);
                });
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收服务器返回的消息
                byte[] dataContent = byteBuffer.array();
                ExternalMessage message = ProtoKit.parseProtoByte(dataContent, ExternalMessage.class);
                log.info("收到消息 ExternalMessage ========== \n{}", message);
                byte[] data = message.getData();
                int cmdMerge = message.getCmdMerge();

                TheCommand theCommand = theCommandMap.get(cmdMerge);
                if (theCommand != null) {
                    Object o = ProtoKit.parseProtoByte(data, theCommand.resultClass);
                    int cmd = CmdKit.getCmd(cmdMerge);
                    int subCmd = CmdKit.getSubCmd(cmdMerge);

                    log.info("{} - {} : {}", cmd, subCmd, o);
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

    Map<Integer, TheCommand> theCommandMap = new TreeMap<>();


    private void initTheCommands() {

        extractedLong();
//        extractedInt();
    }

    private void extractedLong() {
        createTheCommandLongPb(WrapperCmd.long2long);
        createTheCommandLongPb(WrapperCmd.longPb2longPb);

        TheCommand theCommandLongPb = createTheCommandLongPb(WrapperCmd.long2longList);
        theCommandLongPb.resultClass = LongListPb.class;

        theCommandLongPb = createTheCommandLongPb(WrapperCmd.long2longListPb);
        theCommandLongPb.resultClass = LongListPb.class;

        createTheCommandLongListPb(WrapperCmd.longListPb2longList);
        createTheCommandLongListPb(WrapperCmd.longList2longListPb);
    }

    private void extractedInt() {
        createTheCommandIntPb(WrapperCmd.int2int);
        createTheCommandIntPb(WrapperCmd.intPb2intPb);

        TheCommand theCommandIntPb = createTheCommandIntPb(WrapperCmd.int2intList);
        theCommandIntPb.resultClass = IntListPb.class;

        theCommandIntPb = createTheCommandIntPb(WrapperCmd.int2intListPb);
        theCommandIntPb.resultClass = IntListPb.class;

        createTheCommandIntListPb(WrapperCmd.intListPb2intList);
        createTheCommandIntListPb(WrapperCmd.intList2intListPb);
    }

    private TheCommand createTheCommandIntListPb(int subCmd) {
        List<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);

        IntListPb intListPb = new IntListPb();
        intListPb.intValues = list;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, intListPb);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = IntListPb.class;


        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandIntPb(int subCmd) {
        int intValue = 100;
        IntPb intPb = new IntPb();
        intPb.intValue = intValue;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, intPb);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = IntPb.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandLongPb(int subCmd) {
        LongPb intPb = new LongPb();
        intPb.longValue = 100;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, intPb);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = LongPb.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandLongListPb(int subCmd) {
        List<Long> list = new ArrayList<>();
        list.add(1100L);
        list.add(2200L);

        LongListPb intListPb = new LongListPb();
        intListPb.longValues = list;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, intListPb);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = LongListPb.class;


        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private ExternalMessage extractedExternalMessage(int subCmd, Object object) {
        int cmd = WrapperCmd.cmd;

        // 游戏框架内置的协议， 与游戏前端相互通讯的协议
        ExternalMessage externalMessage = new ExternalMessage();
        // 请求命令类型: 0 心跳，1 业务
        externalMessage.setCmdCode(ExternalMessageCmdCode.biz);
        // 路由
        externalMessage.setCmdMerge(cmd, subCmd);

        // 业务数据
        byte[] data = ProtoDataCodec.me().encode(object);
        // 业务数据
        externalMessage.setData(data);

        return externalMessage;
    }

    class TheCommand {

        ExternalMessage externalMessage;

        Class<?> resultClass;
    }
}
