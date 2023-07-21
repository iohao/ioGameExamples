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
package com.iohao.game.spring.logic.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValueList;
import com.iohao.game.spring.common.cmd.HallCmdModule;
import com.iohao.game.spring.common.pb.UserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-01-31
 */
@Slf4j
@ActionController(HallCmdModule.cmd)
public class SpringIntAction {

    @ActionMethod(HallCmdModule.intValue)
    public int intValue(int value) {
        return value + 10;
    }

    @ActionMethod(HallCmdModule.intValue1)
    public int intValue1(IntValue value) {
        return value.value + 10;
    }

    @ActionMethod(HallCmdModule.intValueList)
    public IntValueList intValueList(IntValueList value) {
        return value;
    }

    @ActionMethod(HallCmdModule.defaultValue)
    public UserInfo defaultValue() {
        UserInfo userInfo = new UserInfo();

        return userInfo;
    }
}
