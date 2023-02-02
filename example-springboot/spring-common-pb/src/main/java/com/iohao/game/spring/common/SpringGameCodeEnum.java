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
package com.iohao.game.spring.common;

import com.iohao.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 断言 + 异常机制
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SpringGameCodeEnum implements MsgExceptionInfo {
    /** 学校最大等级 */
    levelMax(101, "学校等级超出"),
    /** 等级不够 */
    vipLevelEnough(102, "VIP 等级不够"),
    /** 号已经在线上，不允许重复登录 */
    accountOnline(103, "号已经在线上，不允许重复登录"),

    /** 登录异常 */
    loginError(104, "登录异常"),

    ;

    /** 消息码 */
    final int code;
    /** 消息模板 */
    final String msg;

    SpringGameCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
