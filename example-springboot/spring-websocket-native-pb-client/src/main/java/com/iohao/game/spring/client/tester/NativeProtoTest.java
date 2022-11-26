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
package com.iohao.game.spring.client.tester;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.wrapper.IntListPb;
import com.iohao.game.action.skeleton.protocol.wrapper.IntPb;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.spring.client.command.WebsocketNativeProtoClientKit;
import com.iohao.message.BizProto;
import com.iohao.message.Proto;
import com.iohao.pb.LoginVerify;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

/**
 * @author 渔民小镇
 * @date 2022-08-03
 */
@Slf4j
public class NativeProtoTest {
    public static void main(String[] args) throws Exception {

        extractedExternalMessage();

//        extractedIntLong();
    }

    private static void extractedExternalMessage() throws InvalidProtocolBufferException {
        // ================ 原生 proto ================
        BizProto.LoginVerify loginVerify = BizProto.LoginVerify.newBuilder()
                .setAge(273676)
                .setJwt("abcd")
                .setLoginBizCode(1)
                .build();

        Proto.ExternalMessage externalMessageNative = Proto.ExternalMessage.newBuilder()
                .setCmdCode(1)
                .setProtocolSwitch(0)
                .setCmdMerge(CmdKit.merge(3, 1))
                .setData(loginVerify.toByteString())
                .build();

        byte[] bytes = externalMessageNative.toByteArray();
        log.info("bytes : {} {}", bytes.length, bytes);
        System.out.println();

        // ================ 原生 proto convert2 jprotobuf ================

        ExternalMessage externalMessage = DataCodecKit.decode(bytes, ExternalMessage.class);

        Assert.assertTrue(externalMessage.getCmdCode() == 1);
        Assert.assertTrue(externalMessage.getProtocolSwitch() == 0);
        Assert.assertTrue(externalMessage.getCmdMerge() == CmdKit.merge(3, 1));


        log.info("================ 原生 proto convert2 jprotobuf ================");
        externalMessage.setResponseStatus(-1001);
        externalMessage.setValidMsg("the msg");
        log.info("catMessage1 : {}", externalMessage);

        byte[] loginData = externalMessage.getData();
        LoginVerify loginVerify1 = DataCodecKit.decode(loginData, LoginVerify.class);
        log.info("loginVerify1 : {}", loginVerify1);
        Assert.assertTrue(loginVerify1.age == 273676);
        Assert.assertEquals(loginVerify1.jwt, "abcd");
        Assert.assertTrue(loginVerify1.loginBizCode == 1);

        System.out.println();

        byte[] bytes1 = DataCodecKit.encode(externalMessage);

        Proto.ExternalMessage externalMessageNative2 = Proto.ExternalMessage.parseFrom(bytes1);
        System.out.println();
        log.info("================ jprotobuf convert2 原生 proto ================");
        log.info("catMessage2 : {} {}", externalMessageNative2.getResponseStatus(), externalMessageNative2);

        // ================ 原生 proto login ================

        ByteString theLoginData = externalMessageNative2.getData();
        log.info("================ 原生 proto login ================");

        BizProto.LoginVerify loginVerify2 = BizProto.LoginVerify.parseFrom(theLoginData);
        log.info("loginVerify2 : {}", loginVerify2);

        Assert.assertTrue(loginVerify2.getAge() == loginVerify1.age);
        Assert.assertTrue(loginVerify2.getLoginBizCode() == loginVerify1.loginBizCode);
        Assert.assertEquals(loginVerify2.getJwt(), loginVerify1.jwt);

        Assert.assertTrue(externalMessageNative2.getCmdCode() == externalMessage.getCmdCode());
        Assert.assertTrue(externalMessageNative2.getProtocolSwitch() == externalMessage.getProtocolSwitch());
        Assert.assertTrue(externalMessageNative2.getCmdMerge() == externalMessage.getCmdMerge());
        Assert.assertTrue(externalMessageNative2.getResponseStatus() == externalMessage.getResponseStatus());
        Assert.assertEquals(externalMessageNative2.getResponseStatus(), externalMessage.getResponseStatus());
    }

    private static void extractedIntLong() throws InvalidProtocolBufferException {
        // ================ 原生 proto IntPb convert2 iohao ================

        Proto.IntPb intPb = Proto.IntPb.newBuilder()
                .setIntValue(-273676)
                .build();

        Proto.ExternalMessage externalMessage = WebsocketNativeProtoClientKit
                .createExternalMessage(1, 1, intPb.toByteString());

        ExternalMessage convert = convert(externalMessage);

        byte[] data = convert.getData();
        IntPb intPb1 = DataCodecKit.decode(data, IntPb.class);
        log.info("intPb1 : {}", intPb1);

        Assert.assertTrue(intPb.getIntValue() == intPb1.intValue);

        System.out.println();

        // ================ 原生 proto IntListPb convert2 iohao ================

        Proto.IntListPb intListPb = Proto.IntListPb.newBuilder()
                .addIntValues(1)
                .addIntValues(100)
                .addIntValues(1000)
                .addIntValues(-10000)
                .build();

        externalMessage = WebsocketNativeProtoClientKit
                .createExternalMessage(1, 2, intListPb.toByteString());

        convert = convert(externalMessage);
        data = convert.getData();
        IntListPb intListPb1 = DataCodecKit.decode(data, IntListPb.class);
        log.info("intListPb1 : {}", intListPb1);

        System.out.println();

        byte[] bytes = externalMessage.toByteArray();
        Proto.ExternalMessage externalMessage1 = Proto.ExternalMessage.parseFrom(bytes);
        Proto.IntListPb intListPb2 = Proto.IntListPb.parseFrom(externalMessage1.getData());
        log.info("externalMessage1 : {}", externalMessage1);
        log.info("intListPb2 : {}", intListPb2);
    }

    private static ExternalMessage convert(Proto.ExternalMessage externalMessage) {
        byte[] bytes = externalMessage.toByteArray();

        ExternalMessage externalMessage1 = DataCodecKit.decode(bytes, ExternalMessage.class);
        log.info("convert externalMessage : {}", externalMessage1);

        return externalMessage1;
    }
}
