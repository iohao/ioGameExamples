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
package com.iohao.game.example.multiple.weather;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.example.multiple.common.cmd.internal.WeatherCmd;
import com.iohao.game.example.multiple.common.data.TheLogin;
import com.iohao.game.example.multiple.common.data.TheUserInfo;

/**
 * @author 渔民小镇
 * @date 2023-04-10
 */
@ActionController(WeatherCmd.cmd)
public class LoginAction {
    @ActionMethod(WeatherCmd.login)
    public TheUserInfo login(TheLogin login, FlowContext flowContext) {

        String jwt = login.jwt;

        long userId = jwt.hashCode();

        boolean b = UserIdSettingKit.settingUserId(flowContext, userId);
        if (!b) {
            System.err.println("login error");
        }

        TheUserInfo userInfo = new TheUserInfo();
        userInfo.id = userId;
        userInfo.name = jwt;

        return userInfo;
    }
}
