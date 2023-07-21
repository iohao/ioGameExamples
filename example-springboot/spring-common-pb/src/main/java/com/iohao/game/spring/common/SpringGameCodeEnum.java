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
