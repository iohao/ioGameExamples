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
package com.iohao.example.jsr.jakarta.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.example.common.cmd.JsrJakartaCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-26
 */
@Slf4j
@ActionController(JsrJakartaCmd.cmd)
public class JsrJakartaAction {
    int count;

    @ActionMethod(JsrJakartaCmd.verify)
    public ValidMessage verify(ValidMessage message) {
        log.info("jsr380 success:{}", message);
        return message;
    }

    @ActionMethod(JsrJakartaCmd.hello)
    public String hello(String name) {
        count++;

        if (count % 2 == 0) {
            throw new MsgException(100, "name error " + count);
        }

        return "count:%s %s".formatted(count, name);
    }
}
