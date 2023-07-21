/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.tank.net.onmessage;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.external.core.message.ExternalMessage;
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
//        return DataCodecKit.decode(data, BarHelloPb.class);
        return null;
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
