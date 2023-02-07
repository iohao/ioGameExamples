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
package com.iohao.game.spring.logic.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.protocol.wrapper.IntListPb;
import com.iohao.game.action.skeleton.protocol.wrapper.IntPb;
import com.iohao.game.spring.common.cmd.HallCmdModule;
import com.iohao.game.spring.common.pb.UserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-01-31
 */
@Slf4j
@ActionController(HallCmdModule.cmd)
public class IntPbAction {

    @ActionMethod(HallCmdModule.intPb)
    public int intPb(int value) {
//        return value.intValue + 10;
        return value + 10;
    }

    @ActionMethod(HallCmdModule.intPb1)
    public int intPb1(IntPb value) {
        return value.intValue + 10;
//        return 0;
    }

    @ActionMethod(HallCmdModule.intListPb1)
    public IntListPb intListPb(IntListPb value) {
        return value;
    }

    @ActionMethod(HallCmdModule.defaultValue)
    public UserInfo defaultValue() {
        UserInfo userInfo = new UserInfo();

        return userInfo;
    }
}
