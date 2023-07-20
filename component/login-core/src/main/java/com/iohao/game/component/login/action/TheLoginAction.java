/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.component.login.action;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.component.login.cmd.LoginCmd;
import com.iohao.game.component.login.proto.LoginVerify;
import com.iohao.game.component.login.proto.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
@ActionController(LoginCmd.cmd)
public class TheLoginAction {
    // cn name
    static final Name name = new Faker(Locale.CHINA).name();

    /**
     * 登录验证
     *
     * @param loginVerify 登录
     * @param flowContext flowContext
     * @return 用户信息
     */
    @ActionMethod(LoginCmd.login)
    public UserInfo loginVerify(LoginVerify loginVerify, FlowContext flowContext) {
        // 约定好， jwt 使用的 int
        long userId = Integer.parseInt(loginVerify.jwt);

        UserInfo userInfo = new UserInfo();
        userInfo.id = userId;
        userInfo.name = name.lastName() + name.firstName();

        // channel 中设置用户的真实 userId；
        boolean success = UserIdSettingKit.settingUserId(flowContext, userId);

        if (!success) {
            log.error("登录失败");
        }

        return userInfo;
    }
}
