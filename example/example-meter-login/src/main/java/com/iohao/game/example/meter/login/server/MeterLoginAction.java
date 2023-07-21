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
package com.iohao.game.example.meter.login.server;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.example.common.cmd.MeterLoginCmd;
import com.iohao.game.example.common.msg.HelloReq;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2022-10-08
 */
@ActionController(MeterLoginCmd.cmd)
public class MeterLoginAction {
    public static final LongAdder longAdder = new LongAdder();
    public static final LongAdder loginLongAdder = new LongAdder();

    @ActionMethod(MeterLoginCmd.login)
    public HelloReq login(HelloReq helloReq, FlowContext flowContext) {

        longAdder.increment();

        // 一直测试登录，即使登录过了也再次登录
        String name = helloReq.name;
        int userId = Math.abs(name.hashCode());

        boolean b = UserIdSettingKit.settingUserId(flowContext, userId);
        if (b) {
            loginLongAdder.increment();
        }


        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = "meter login，I'm here";
        return newHelloReq;
    }
}
