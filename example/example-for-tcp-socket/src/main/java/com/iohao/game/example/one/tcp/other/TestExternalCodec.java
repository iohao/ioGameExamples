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
package com.iohao.game.example.one.tcp.other;

import cn.hutool.core.util.HexUtil;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.handler.codec.ExternalCodecSocket;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.example.common.msg.HelloReq;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 编解码示例
 *
 * @author 渔民小镇
 * @date 2022-04-13
 */
@Slf4j
public class TestExternalCodec {
    public static void main(String[] args) {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoggingHandler(LogLevel.INFO), new ExternalCodecSocket());

        ExternalMessage externalMessage = getExternalMessage();
        // 编码
        embeddedChannel.writeOutbound(externalMessage);

        // 解码
        embeddedChannel.writeInbound(Unpooled.wrappedBuffer(new byte[]{
                0x00, 0x00, 0x00, 0x12, 0x08, 0x02, 0x10, 0x00, 0x18, (byte) 0x82, (byte) 0x80, 0x08, 0x20, 0x00, 0x32, 0x06,
                0x0a, 0x04, 0x61, 0x62, 0x63, 0x31
        }));

        String s = toHex(externalMessage);
        /*
         * jmeter
         * org.apache.jmeter.protocol.tcp.sampler.BinaryTCPClientImpl
         * 127.0.0.1:10100
         * 00000012080110001881803c200032060a0461626331
         */
        log.info("十六进制报文 : {}", s);
    }

    private static ExternalMessage getExternalMessage() {
        // 路由
        int cmd = 15;
        int subCmd = 1;
        // 业务数据
        HelloReq helloReq = new HelloReq();
        helloReq.name = "abc1";

        ExternalMessage request = ExternalKit.createExternalMessage(cmd, subCmd, helloReq);

        log.info("{}", request);

        return request;
    }

    private static String toHex(ExternalMessage externalMessage) {
        // 下面是将游戏对外服协议转为十六进制
        byte[] bytes = DataCodecKit.encode(externalMessage);

        ByteBuf buffer = new PooledByteBufAllocator(true).buffer();
        // 消息长度
        buffer.writeInt(bytes.length)
                // 消息
                .writeBytes(bytes);

        System.out.println();
        int length = bytes.length + 4;
        // 消息
        byte[] msgBytes = new byte[length];
        buffer.readBytes(msgBytes);

        String s = HexUtil.encodeHexStr(msgBytes);
        return s;
    }

}
