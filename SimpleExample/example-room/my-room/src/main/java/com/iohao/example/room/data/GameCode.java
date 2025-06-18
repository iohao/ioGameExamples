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
package com.iohao.example.room.data;

import com.iohao.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.Getter;

import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@Getter
public enum GameCode implements MsgExceptionInfo {
    illegalOperation("非法操作"),
    roomNotExist("房间不存在"),
    roomSpaceSizeNotEnough("房间空间不足，人数已满"),

    ;
    /** 消息码 */
    final int code;
    /** 消息模板 */
    final String msg;

    GameCode(String msg) {
        this.code = this.ordinal() + 1;

        if (Locale.getDefault().getLanguage().equals("zh")) {
            this.msg = msg;
        } else {
            this.msg = this.name();
        }
    }

    public static void main(String[] args) {
        System.out.println(GameCode.illegalOperation);
    }
}
