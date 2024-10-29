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
package com.iohao.game.tank.net;

import com.iohao.game.sdk.net.NetCoreSetting;
import com.iohao.game.sdk.net.NetJoinEnum;
import com.iohao.game.sdk.net.internal.DefaultCoreSetting;
import com.iohao.game.sdk.net.internal.DefaultNetServer;
import com.iohao.game.sdk.net.internal.DefaultNetServerHook;
import com.iohao.game.common.kit.ExecutorKit;

/**
 * @author 渔民小镇
 * @date 2023-11-02
 */
public class TankNet {
    public void startup(Runnable task) {
        ExecutorKit.newSingleThreadExecutor("netServer").submit(() -> {

            DefaultCoreSetting setting = new DefaultCoreSetting()
                    .setJoinEnum(NetJoinEnum.WEBSOCKET)
                    .setNetServerHook(new DefaultNetServerHook() {
                        @Override
                        public void success(NetCoreSetting coreSetting) {
                            task.run();
                        }
                    });

            // 启动网络服务器，与游戏服务器连接
            new DefaultNetServer(setting).startup();
        });
    }

    private TankNet() {

    }

    public static TankNet me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankNet ME = new TankNet();
    }
}
