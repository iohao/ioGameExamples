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

        byte[] tempData = tempUserInfo.toByteArray();
        MyTempUserInfo userInfo = DataCodecKit.decode(tempData, MyTempUserInfo.class);
        log.info("userInfo : {}", userInfo);
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
