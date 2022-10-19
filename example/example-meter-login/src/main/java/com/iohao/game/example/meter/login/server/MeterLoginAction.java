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
        int userId = name.hashCode();

        boolean b = UserIdSettingKit.settingUserId(flowContext, userId);
        if (b) {
            loginLongAdder.increment();
        }


        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = "meter login，I'm here";
        return newHelloReq;
    }
}
