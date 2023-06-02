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
package com.iohao.game.example.one.tcp;

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.netty.handler.codec.TcpExternalCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * tcp socket 客户端
 *
 * @author 渔民小镇
 * @date 2022-04-13
 */
@Slf4j
public class DemoSocketClient {
    /** 数据包最大1MB */
    static int PACKAGE_MAX_SIZE = 1024 * 1024;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        var bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 编排网关业务
                        ChannelPipeline pipeline = ch.pipeline();

                        // 数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment。
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(PACKAGE_MAX_SIZE,
                                // 长度字段的偏移量， 从 0 开始
                                0,
                                // 字段的长度, 如果使用的是 short ，占用2位；（消息头用的 byteBuf.writeShort 来记录长度的）
                                // 字段的长度, 如果使用的是 int   ，占用4位；（消息头用的 byteBuf.writeInt   来记录长度的）
                                4,
                                // 要添加到长度字段值的补偿值：长度调整值 = 内容字段偏移量 - 长度字段偏移量 - 长度字段的字节数
                                0,
                                // 跳过的初始字节数： 跳过0位; (跳过消息头的 0 位长度)
                                0));

                        // 编解码
                        pipeline.addLast("codec", new TcpExternalCodec());

                        pipeline.addLast(new DemoSocketHandler());
                    }
                });

        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 10100);

        channelFuture.addListener((ChannelFutureListener) future -> {

            ExternalMessage externalMessage = getExternalMessage();

            Channel channel = future.channel();
            channel.writeAndFlush(externalMessage);
        });

    }

    static class DemoSocketHandler extends SimpleChannelInboundHandler<ExternalMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage msg) {
            log.info("connection server {}", msg);

            byte[] dataContent = msg.getData();

            HelloReq helloReq = DataCodecKit.decode(dataContent, HelloReq.class);

            log.info("data: {}", helloReq);
        }
    }

    private static ExternalMessage getExternalMessage() {
        // 路由
        int cmd = 1;
        int subCmd = 0;

        // 业务数据
        HelloReq helloReq = new HelloReq();
        helloReq.name = "abc12";

        return ExternalKit.createExternalMessage(cmd, subCmd, helloReq);
    }
}
