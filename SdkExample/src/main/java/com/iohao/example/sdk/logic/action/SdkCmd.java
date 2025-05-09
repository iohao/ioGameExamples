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
package com.iohao.example.sdk.logic.action;

import com.iohao.game.action.skeleton.core.CmdInfo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 渔民小镇
 * @date 2024-11-01
 */
public interface SdkCmd {
    int cmd = 1;

    int loginVerify = 0;
    int triggerBroadcast = 1;

    int intValue = 2;
    int longValue = 3;
    int boolValue = 4;
    int stringValue = 5;
    int value = 6;

    int listInt = 12;
    int listLong = 13;
    int listBool = 14;
    int listString = 15;
    int listValue = 16;

    /* ---------- other action test ---------- */
    int testError = 20;
    int noParam = 21;
    int noReturn = 22;
    int bulletMessage = 23;

    int internalAddMoney = 30;

    /* ---------- 广播起始 ---------- */
    AtomicInteger inc = new AtomicInteger(32);

    CmdInfo broadcastInt = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastLong = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastBool = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastString = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastValue = CmdInfo.of(cmd, inc.getAndIncrement());

    CmdInfo broadcastListIntValue = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastListLong = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastListBool = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastListString = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastListValue = CmdInfo.of(cmd, inc.getAndIncrement());

    CmdInfo broadcastBulletMessage = CmdInfo.of(cmd, inc.getAndIncrement());
    CmdInfo broadcastEmpty = CmdInfo.of(cmd, inc.getAndIncrement());

    static CmdInfo of(int subCmd) {
        return CmdInfo.of(cmd, subCmd);
    }
}
