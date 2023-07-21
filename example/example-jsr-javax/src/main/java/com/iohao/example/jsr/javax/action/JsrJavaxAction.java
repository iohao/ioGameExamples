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
package com.iohao.example.jsr.javax.action;

import com.iohao.example.jsr.javax.pb.JsrJavaxPb;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.example.common.cmd.JsrJavaxCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-28
 */
@Slf4j
@ActionController(JsrJavaxCmd.cmd)
public class JsrJavaxAction {

    @ActionMethod(JsrJavaxCmd.jsrJavax)
    public JsrJavaxPb jsrJakarta(JsrJavaxPb jsrJavaxPb) {
        log.info("jsrJavaxPb : {}", jsrJavaxPb);
        return jsrJavaxPb;
    }
}
