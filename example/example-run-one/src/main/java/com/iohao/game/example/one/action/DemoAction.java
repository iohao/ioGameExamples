/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.example.one.action;

import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.example.one.code.DemoCodeEnum;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例action
 *
 * @author 渔民小镇
 * @date 2022-02-24
 */
@Slf4j
@ActionController(DemoCmd.cmd)
public class DemoAction {
    /**
     * 示例 here 方法
     *
     * @param helloReq helloReq
     * @return HelloReq
     */
    @ActionMethod(DemoCmd.here)
    public HelloReq here(HelloReq helloReq) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = helloReq.name + ", I'm here ";
        return newHelloReq;
    }

    /**
     * 示例 异常机制演示
     *
     * @param helloReq helloReq
     * @return HelloReq
     * @throws MsgException e
     */
    @ActionMethod(DemoCmd.jackson)
    public HelloReq jackson(HelloReq helloReq) throws MsgException {
        String jacksonName = "jackson";

        DemoCodeEnum.jackson_error.assertTrue(jacksonName.equals(helloReq.name));

        helloReq.name = helloReq.name + ", hello, jackson !";

        return helloReq;
    }


    /**
     * 登录
     *
     * @param loginVerify 登录请求
     * @param flowContext flowContext
     * @return 玩家数据
     */
    @ActionMethod(DemoCmd.loginVerify)
    public DemoUserInfo loginVerify(DemoLoginVerify loginVerify, FlowContext flowContext) {
        // 登录验证请求
        // 为了方便，这里登录用户的id 写个自身传入 jwt 的 hash
        int newUserId = loginVerify.jwt.hashCode();

        // 登录的关键代码
        // 具体可参考 https://www.yuque.com/iohao/game/tywkqv
        boolean success = UserIdSettingKit.settingUserId(flowContext, newUserId);

        if (!success) {
            log.error("登录错误");
        }

        DemoUserInfo userInfo = new DemoUserInfo();
        userInfo.id = newUserId;
        userInfo.name = loginVerify.jwt;

        return userInfo;
    }
}
