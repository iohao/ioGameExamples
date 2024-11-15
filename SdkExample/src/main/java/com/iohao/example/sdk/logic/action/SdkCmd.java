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

    int testError = 20;

    int broadcastInt = 32;
    int broadcastLong = 33;
    int broadcastBool = 34;
    int broadcastString = 35;
    int broadcastValue = 36;

    int broadcastListInt = 42;
    int broadcastListLong = 43;
    int broadcastListBool = 44;
    int broadcastListString = 45;
    int broadcastListValue = 46;

    int internalAddMoney = 50;

    /* ---------- other action test ---------- */
    int noParam = 60;
    int noReturn = 61;


    static CmdInfo of(int subCmd) {
        return CmdInfo.of(cmd, subCmd);
    }
}
