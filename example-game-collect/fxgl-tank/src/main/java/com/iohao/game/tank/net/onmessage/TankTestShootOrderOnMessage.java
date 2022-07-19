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
package com.iohao.game.tank.net.onmessage;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.protocol.temp.BarHelloPb;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.tank.net.TankOnMessage;

/**
 * @author 渔民小镇
 * @date 2022-07-14
 */
public class TankTestShootOrderOnMessage implements TankOnMessage {

    @Override
    public int getCmdMerge() {
        return CmdKit.merge(TankCmd.cmd, TankCmd.testShootingOrder);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        return ProtoKit.parseProtoByte(data, BarHelloPb.class);
    }

    private TankTestShootOrderOnMessage() {

    }

    public static TankTestShootOrderOnMessage me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankTestShootOrderOnMessage ME = new TankTestShootOrderOnMessage();
    }
}
