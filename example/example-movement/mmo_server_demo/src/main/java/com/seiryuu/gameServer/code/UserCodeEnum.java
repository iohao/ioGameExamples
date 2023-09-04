/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.seiryuu.gameServer.code;

import com.iohao.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-03-22
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum UserCodeEnum implements MsgExceptionInfo {
    /** jsr330 */
    // jackson_error(100, "异常机制测试，name 必需是 jackson !"),

    /**
     * 账号不存在 或密码错误 都报这个错误
     */
    user_not_empty(100, "账号或密码错误，请重试！"),

    /**
     * 号已经在线上，不允许重复登录
     */
    user_accountOnline(101, "不可重复登录！"),

    /**
     * 账号注册 已存在
     */
    user_register_repeat(102, "账号不可重复！"),

    ;
    /**
     * 消息码
     */
    final int code;

    /**
     * 消息模板
     */
    final String msg;

    UserCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
