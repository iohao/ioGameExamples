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

import com.iohao.example.sdk.logic.data.LoginVerifyMessage;
import com.iohao.example.sdk.logic.data.UserMessage;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;

/**
 * My Action; 我的 Action
 *
 * @author 渔民小镇
 * @date 2024-11-10
 */
@ActionController(2)
public final class MyAction {
    /**
     * this is hello action. 这是我提供的 hello action。
     *
     * @param name your name; 你的名字
     * @return 我的响应内容; My Response
     */
    @ActionMethod(1)
    public String hello(String name) {
        return "hello %s, I'm here.".formatted(name);
    }

    /**
     * 我的登录验证 action。My loginVerify action
     *
     * @param verifyMessage 我的验证信息。verifyMessage
     * @return 我的用户信息。My UserMessage
     */
    @ActionMethod(2)
    public UserMessage loginVerify(LoginVerifyMessage verifyMessage) {
        // ... your verify code
        return new UserMessage();
    }
}
