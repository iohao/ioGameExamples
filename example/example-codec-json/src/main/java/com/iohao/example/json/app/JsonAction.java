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
package com.iohao.example.json.app;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.example.common.cmd.JsonCmd;
import com.iohao.game.example.common.msg.HelloReq;

/**
 * @author 渔民小镇
 * @date 2022-11-24
 */
@ActionController(JsonCmd.cmd)
public class JsonAction {
    @ActionMethod(JsonCmd.hello)
    public HelloReq hello(HelloReq helloReq) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = helloReq.name + "，hello json";
        return newHelloReq;
    }

    @ActionMethod(JsonCmd.jsonMsg)
    public JsonMsg json(JsonMsg jsonMsg) {
        JsonMsg newJsonMsg = new JsonMsg();
        newJsonMsg.name = jsonMsg.name + "，hello json msg";
        return newJsonMsg;
    }

    @ActionMethod(JsonCmd.helloString)
    public String helloString(HelloReq helloReq) {
        return helloReq.name + "，hello String";
    }
}
