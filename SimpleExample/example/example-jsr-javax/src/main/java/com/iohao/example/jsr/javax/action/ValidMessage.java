/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.example.jsr.javax.action;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author 渔民小镇
 * @date 2022-10-28
 */
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ValidMessage {
    /** id */
    int id;

    /**
     * email
     * <pre>
     *     注意，使用 javax 验证时，不能使用 javax 的 email;
     * </pre>
     */
    @NotNull
    @org.hibernate.validator.constraints.Email
    String email;
}
