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
package com.iohao.game.example.one.tcp.other;

import com.iohao.game.action.skeleton.core.flow.codec.ProtoDataCodec;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.bolt.broker.client.external.bootstrap.handler.codec.ExternalCodecSocket;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessageCmdCode;
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
    }

    private static ExternalMessage getExternalMessage() {
        ExternalMessage request = new ExternalMessage();
        request.setCmdCode(ExternalMessageCmdCode.biz);

        // 路由
        int cmd = 1;
        int subCmd = 1;
        request.setCmdMerge(cmd, subCmd);

        // 业务数据
        HelloReq helloReq = new HelloReq();
        helloReq.name = "abc1";

        byte[] data = ProtoDataCodec.me().encode(helloReq);
        // 业务数据
        request.setData(data);

        log.info("{}", request);
        return request;
    }

}
