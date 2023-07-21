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
package com.iohao.game.example.one.code;

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
public enum DemoCodeEnum implements MsgExceptionInfo {
    /** jsr330 */
    jackson_error(100, "异常机制测试，name 必须是 jackson !");
    /** 消息码 */
    final int code;
    /** 消息模板 */
    final String msg;

    DemoCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
