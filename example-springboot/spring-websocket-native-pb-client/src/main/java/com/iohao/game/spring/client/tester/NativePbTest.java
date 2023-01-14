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

import com.google.protobuf.InvalidProtocolBufferException;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.message.BizProto;
import com.iohao.message.MyTempUserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-01-04
 */
@Slf4j
public class NativePbTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        MyTempUserInfo myTempUserInfo = new MyTempUserInfo();
        myTempUserInfo.id = 1;
        myTempUserInfo.name = "12345";

        byte[] encode = DataCodecKit.encode(myTempUserInfo);
        log.info("encode: {}", encode);

        BizProto.TempUserInfo.Builder builder = BizProto.TempUserInfo.newBuilder();
        builder.setId(myTempUserInfo.id);
        builder.setName(myTempUserInfo.name);
        byte[] bytes = builder.build().toByteArray();
        log.info("bytes : {}", bytes);

        BizProto.TempUserInfo tempUserInfo = BizProto.TempUserInfo.parseFrom(encode);
        log.info("tempUserInfo : {}", tempUserInfo);
    }

    private static void extracted1() {
        BizProto.TempUserInfo.Builder builder = BizProto.TempUserInfo.newBuilder();
        builder.setId(1);
        builder.setName("12345");

        byte[] bytes = builder.build().toByteArray();

        MyTempUserInfo userInfo = DataCodecKit.decode(bytes, MyTempUserInfo.class);

        log.info("userInfo : {}", userInfo);
    }

    private static void extracted() {
        BizProto.TempUserInfo.Builder builder = BizProto.TempUserInfo.newBuilder();
        builder.setId(1);
        builder.setName("12345");

        byte[] bytes = builder.build().toByteArray();
        log.info("bytes : {}", bytes);

        MyTempUserInfo myTempUserInfo = new MyTempUserInfo();
        myTempUserInfo.id = builder.getId();
        myTempUserInfo.name = builder.getName();

        byte[] encode = DataCodecKit.encode(myTempUserInfo);
        log.info("encode: {}", encode);
    }
}
