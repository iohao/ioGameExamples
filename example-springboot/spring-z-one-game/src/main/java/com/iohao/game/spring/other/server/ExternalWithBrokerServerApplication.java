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
package com.iohao.game.spring.other.server;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import com.iohao.game.spring.broker.GameBrokerBoot;
import com.iohao.game.spring.common.pb.Vector3;
import com.iohao.game.spring.external.GameExternal;

/**
 * 游戏对外服和游戏网关的启动
 *
 * @author 渔民小镇
 * @date 2022-09-05
 */
public class ExternalWithBrokerServerApplication {
    public static void main(String[] args) {

        Vector3 vector3 = ProtoKit.parseProtoByte(null, Vector3.class);

        Codec<Vector3> codec = ProtobufProxy.create(Vector3.class);

        byte[] data = new byte[]{};

        try {
            Vector3 v = codec.decode(data);
            System.out.println("--- " + v);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println(vector3);
        if (true) {

            return;
        }

        // 对外开放的端口
        int externalPort = 10100;
        // 游戏对外服
        ExternalServer externalServer = new GameExternal().createExternalServer(externalPort);

        // broker （游戏网关）
        BrokerServer brokerServer = new GameBrokerBoot().createBrokerServer();

        // 多服单进程的方式部署（类似单体应用）
        new NettyRunOne()
                // broker （游戏网关）
                .setBrokerServer(brokerServer)
                // 游戏对外服
                .setExternalServer(externalServer)
                // 启动 游戏对外服、游戏网关、游戏逻辑服
                .startup();

        // 先关闭访问验证
        ExternalGlobalConfig.accessAuthenticationHook.setVerifyIdentity(false);
    }
}
