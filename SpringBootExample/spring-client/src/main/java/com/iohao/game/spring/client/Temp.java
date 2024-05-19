/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
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
package com.iohao.game.spring.client;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.common.kit.ProtoKit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author 渔民小镇
 * @date 2024-04-30
 */
@Slf4j
public class Temp {
    static IntValue intValue = IntValue.of(100);

    public static void main(String[] args) throws Exception {
//        extracted11();

        BarSkeletonBuilder builder = BarSkeleton.newBuilder();

    }

    private static void extracted11() throws IOException {
        long allTime = 0;
        for (int i = 0; i < 3; i++) {
             allTime += extracted1();
        }

        log.info("--allTime : {}", allTime);
    }

    private static long extracted1() throws IOException {

        long createCodecTime = System.currentTimeMillis();
        Codec<IntValue> intValueCodec = ProtobufProxy.create(IntValue.class);
        long codecTimeConsumer = System.currentTimeMillis() - createCodecTime;
        log.info("create Codec : {}", codecTimeConsumer);

        long encodeTime = System.currentTimeMillis();
        byte[] encode = intValueCodec.encode(intValue);
        long encodeTimeConsumer = System.currentTimeMillis() - encodeTime;
        log.info("encodeTime : {}", encodeTimeConsumer);

        long allTime = codecTimeConsumer + encodeTimeConsumer;
        log.info("all time : {}", allTime);
        System.out.println();

        return allTime;
    }
}
