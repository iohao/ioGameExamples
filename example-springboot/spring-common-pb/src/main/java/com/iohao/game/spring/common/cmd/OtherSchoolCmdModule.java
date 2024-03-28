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
package com.iohao.game.spring.common.cmd;

import com.iohao.game.action.skeleton.core.CmdInfo;

/**
 * @author 渔民小镇
 * @date 2022-08-26
 */
public interface OtherSchoolCmdModule {
    int cmd = SpringCmdModule.otherSchoolCmd;

    int jsr380 = 1;

    /** 业务参数自动装箱、拆箱基础类型 long */
    int longValueWrapper = 2;
    int longValueWrapperLonger = 3;
    int longValueWrapperLongValue = 4;

    int longValueWithBroadcast = 6;

    int longValueWithBroadcastData = 7;

    static CmdInfo getCmdInfo(int subCmd) {
        return CmdInfo.of(cmd, subCmd);
    }
}
