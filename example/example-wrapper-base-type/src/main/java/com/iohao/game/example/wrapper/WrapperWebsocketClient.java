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
package com.iohao.game.example.wrapper;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.wrapper.*;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
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
                    byte[] bytes = DataCodecKit.encode(externalMessage);
                    // 发送数据到游戏服务器
                    this.send(bytes);
                });
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收服务器返回的消息
                byte[] dataContent = byteBuffer.array();
                ExternalMessage message = DataCodecKit.decode(dataContent, ExternalMessage.class);
                log.info("收到消息 ExternalMessage ========== \n{}", message);
                byte[] data = message.getData();
                int cmdMerge = message.getCmdMerge();

                TheCommand theCommand = theCommandMap.get(cmdMerge);
                if (theCommand != null) {
                    Object o = DataCodecKit.decode(data, theCommand.resultClass);
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

//        extractedString();
//        extractedLong();
//        extractedInt();
        extractedBoolean();

    }

    private void extractedBoolean() {
        createTheCommandBoolValue(WrapperCmd.bool2bool);
        createTheCommandBoolValue(WrapperCmd.boolValue2boolValue);

        createTheCommandBoolValueList(WrapperCmd.boolList2boolList);
        createTheCommandBoolValueList(WrapperCmd.boolValueList2boolValueList);
    }

    private TheCommand createTheCommandBoolValueList(int subCmd) {
        List<Boolean> list = new ArrayList<>();
        list.add(false);
        list.add(true);

        BoolValueList boolValueList = new BoolValueList();
        boolValueList.values = list;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, boolValueList);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = BoolValueList.class;


        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandBoolValue(int subCmd) {
        BoolValue boolValue = new BoolValue();
        boolValue.value = false;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, boolValue);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = BoolValue.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private void extractedLong() {
        createTheCommandLongValue(WrapperCmd.long2long);
        createTheCommandLongValue(WrapperCmd.longValue2longValue);

        createTheCommandLongValueList(WrapperCmd.longList2longList);
        createTheCommandLongValueList(WrapperCmd.longValueList2longValueList);
    }

    private void extractedInt() {
        createTheCommandIntValue(WrapperCmd.int2int);
        createTheCommandIntValue(WrapperCmd.intValue2intValue);

        createTheCommandIntValueList(WrapperCmd.intList2intList);
        createTheCommandIntValueList(WrapperCmd.intValueList2intValueList);
    }

    private TheCommand createTheCommandIntValueList(int subCmd) {
        List<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);

        IntValueList intValueList = new IntValueList();
        intValueList.values = list;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, intValueList);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = IntValueList.class;


        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandIntValue(int subCmd) {
        int value = 100;
        IntValue intValue = new IntValue();
        intValue.value = value;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, intValue);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = IntValue.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandLongValue(int subCmd) {
        LongValue longValue = new LongValue();
        longValue.value = 100;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, longValue);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = LongValue.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private TheCommand createTheCommandLongValueList(int subCmd) {
        List<Long> list = new ArrayList<>();
        list.add(1100L);
        list.add(2200L);

        LongValueList longValueList = new LongValueList();
        longValueList.values = list;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, longValueList);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = LongValueList.class;


        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private void extractedString() {
        createTheCommandStringValue(WrapperCmd.string2string);
        createTheCommandStringValue(WrapperCmd.stringValue2stringValue);

        createTheCommandStringValueList(WrapperCmd.stringList2stringList);
        createTheCommandStringValueList(WrapperCmd.stringValueList2stringValueList);
    }

    private TheCommand createTheCommandStringValue(int subCmd) {
        StringValue stringValue = new StringValue();
        stringValue.value = "100";

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, stringValue);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = StringValue.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }


    private TheCommand createTheCommandStringValueList(int subCmd) {
        List<String> list = new ArrayList<>();
        list.add(1100L + "");
        list.add(2200L + "");

        StringValueList stringValueList = new StringValueList();
        stringValueList.values = list;

        ExternalMessage externalMessage = extractedExternalMessage(subCmd, stringValueList);

        TheCommand theCommand = new TheCommand();
        theCommand.externalMessage = externalMessage;
        theCommand.resultClass = StringValueList.class;

        theCommandMap.put(externalMessage.getCmdMerge(), theCommand);

        return theCommand;
    }

    private ExternalMessage extractedExternalMessage(int subCmd, Object object) {
        int cmd = WrapperCmd.cmd;

        // 游戏框架内置的协议， 与游戏前端相互通讯的协议
        return ExternalKit.createExternalMessage(cmd, subCmd, object);
    }

    class TheCommand {

        ExternalMessage externalMessage;

        Class<?> resultClass;
    }
}
