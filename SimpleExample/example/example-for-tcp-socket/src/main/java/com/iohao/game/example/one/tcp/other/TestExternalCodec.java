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
package com.iohao.game.example.one.tcp.other;

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.example.common.msg.HelloMessage;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.netty.handler.codec.TcpExternalCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoggingHandler(LogLevel.INFO), new TcpExternalCodec());

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
        HelloMessage helloMessage = new HelloMessage();
        helloMessage.name = "abc1";

        ExternalMessage request = ExternalKit.createExternalMessage(cmd, subCmd, helloMessage);

        log.info("{}", request);

        return request;
    }

    private static String toHex(ExternalMessage externalMessage) {
        // 下面是将游戏对外服协议转为十六进制
        byte[] bytes = DataCodecKit.encode(externalMessage);
        log.info("bytes : {}", bytes);

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

        /*
         * [8, 1, 16, 0, 24, -127, -128, 60, 32, 0, 50, 6, 10, 4, 97, 98, 99, 49, 56, 0]
         * 00000014080110001881803c200032060a04616263313800
         */
        return ByteBufUtil.hexDump(msgBytes);
    }

}
