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
package com.iohao.game.spring.client.tester;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValueList;
import com.iohao.game.external.core.message.ExternalMessage;
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

        Assert.assertEquals(1, externalMessage.getCmdCode());
        Assert.assertEquals(0, externalMessage.getProtocolSwitch());
        Assert.assertEquals(externalMessage.getCmdMerge(), CmdKit.merge(3, 1));


        log.info("================ 原生 proto convert2 jprotobuf ================");
        externalMessage.setResponseStatus(-1001);
        externalMessage.setValidMsg("the msg");
        log.info("catMessage1 : {}", externalMessage);

        byte[] loginData = externalMessage.getData();
        LoginVerify loginVerify1 = DataCodecKit.decode(loginData, LoginVerify.class);
        log.info("loginVerify1 : {}", loginVerify1);
        Assert.assertEquals(273676, loginVerify1.age);
        Assert.assertEquals(loginVerify1.jwt, "abcd");
        Assert.assertEquals(1, loginVerify1.loginBizCode);

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

        Assert.assertEquals(loginVerify2.getAge(), loginVerify1.age);
        Assert.assertEquals(loginVerify2.getLoginBizCode(), loginVerify1.loginBizCode);
        Assert.assertEquals(loginVerify2.getJwt(), loginVerify1.jwt);

        Assert.assertEquals(externalMessageNative2.getCmdCode(), externalMessage.getCmdCode());
        Assert.assertEquals(externalMessageNative2.getProtocolSwitch(), externalMessage.getProtocolSwitch());
        Assert.assertEquals(externalMessageNative2.getCmdMerge(), externalMessage.getCmdMerge());
        Assert.assertEquals(externalMessageNative2.getResponseStatus(), externalMessage.getResponseStatus());
        Assert.assertEquals(externalMessageNative2.getResponseStatus(), externalMessage.getResponseStatus());
    }

    private static void extractedIntLong() throws InvalidProtocolBufferException {
        // ================ 原生 proto IntValue convert2 iohao ================

        Proto.IntValue intValue = Proto.IntValue.newBuilder()
                .setValue(-273676)
                .build();

        Proto.ExternalMessage externalMessage = WebsocketNativeProtoClientKit
                .createExternalMessage(1, 1, intValue.toByteString());

        ExternalMessage convert = convert(externalMessage);

        byte[] data = convert.getData();
        IntValue intValue1 = DataCodecKit.decode(data, IntValue.class);
        log.info("intValue1 : {}", intValue1);

        Assert.assertEquals(intValue.getValue(), intValue1.value);

        System.out.println();

        // ================ 原生 proto IntValueList convert2 iohao ================

        Proto.IntValueList intValueList = Proto.IntValueList.newBuilder()
                .addValues(1)
                .addValues(100)
                .addValues(1000)
                .addValues(-10000)
                .build();

        externalMessage = WebsocketNativeProtoClientKit
                .createExternalMessage(1, 2, intValueList.toByteString());

        convert = convert(externalMessage);
        data = convert.getData();
        IntValueList intValueList1 = DataCodecKit.decode(data, IntValueList.class);
        log.info("intValueList1 : {}", intValueList1);

        System.out.println();

        byte[] bytes = externalMessage.toByteArray();
        Proto.ExternalMessage externalMessage1 = Proto.ExternalMessage.parseFrom(bytes);
        Proto.IntValueList intValueList2 = Proto.IntValueList.parseFrom(externalMessage1.getData());
        log.info("externalMessage1 : {}", externalMessage1);
        log.info("intValueList2 : {}", intValueList2);
    }

    private static ExternalMessage convert(Proto.ExternalMessage externalMessage) {
        byte[] bytes = externalMessage.toByteArray();

        ExternalMessage externalMessage1 = DataCodecKit.decode(bytes, ExternalMessage.class);
        log.info("convert externalMessage : {}", externalMessage1);

        return externalMessage1;
    }
}
